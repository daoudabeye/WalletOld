package org.wallet.operation.retrait;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.wallet.account.Account;
import org.wallet.account.AccountRepository;
import org.wallet.operation.Operation;
import org.wallet.operation.OperationForm;
import org.wallet.operation.OperationService;
import org.wallet.phone.Telephone;
import org.wallet.phone.TelephoneRepository;
import org.wallet.support.web.MessageTextService;
import org.wallet.utilisateur.Utilisateur;

@Controller
public class RetraitController {

	private static final String RETRAIT_VIEW_NAME = "operations/retrait";
	private static final String CONF_VIEW_NAME = "operations/confirmation";

	@Autowired
	TelephoneRepository telephoneRepository;

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	OperationService operationService;

	@RequestMapping(value = "retrait")
	public String retrait(Model model) {
		model.addAttribute(new OperationForm());
		model.addAttribute("user",new Utilisateur());
		return RETRAIT_VIEW_NAME;
	}
	
	@RequestMapping(value = "confirmation")
	public String confirmation(Model model) {
		model.addAttribute(new OperationForm());
		return CONF_VIEW_NAME;
	}

	@RequestMapping(value = "retrait", method = RequestMethod.POST)
	public String retrait(@Valid @ModelAttribute OperationForm retraitForm, Errors errors, 
			RedirectAttributes ra, Model model) throws Exception{
		if (errors.hasErrors()) {
			addError(errors, model, errors.toString());
			return RETRAIT_VIEW_NAME;
		}

		String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
		Account agentAccount = accountRepository.findOneByUsername(loggedUser);
		Telephone phoneAgent = telephoneRepository.findFirst1ByStatus(agentAccount.getUserId());

		if(phoneAgent == null){
			addError(errors, model, "Aucun numero Actif, "
					+ "pour faire des operations vous devez avoir au moins un numero actif");
			return RETRAIT_VIEW_NAME;
		}

		try {
			String info = "Effectué par :"+retraitForm.getNom()+" "+retraitForm.getPrenom();
			
			Operation op = operationService.webRetrait(phoneAgent.getPhone(), retraitForm.getNumero(), 
					Double.valueOf(retraitForm.getMontant()), info);
			
			model.addAttribute("init", "true");
			model.addAttribute("start", "true");
			model.addAttribute("retraitTmpId", op.getIdOperation());
			model.addAttribute("form", "false");
			
		} catch (Exception e) {
			e.printStackTrace();
			if(e.getMessage().startsWith("Erreur")){
				errors.reject("error.app.binding.webform", "Exception au Depot:");
				model.addAttribute("errorMessage", e.getMessage());
				addError(errors, model, e.getMessage());
				return RETRAIT_VIEW_NAME;
			}
		}

		return RETRAIT_VIEW_NAME;
	}

	private void addError(Errors errors, Model model, String msg){
		errors.reject("error.app.binding.webform", msg);
		model.addAttribute("errorMessage", msg);
		model.addAttribute("user",new Utilisateur());
	}

	@RequestMapping(value = "/retrait/{retraitTmpId}", method = RequestMethod.GET)
	public String checkStatus(Model model, @PathVariable("retraitTmpId") String retraitTmpId){
			System.out.println("id : "+retraitTmpId);
			model.addAttribute(new OperationForm());
		try {
			Operation op = operationService.retraitCheck(retraitTmpId);

			if(op == null){
				model.addAttribute("form", "false");
				model.addAttribute("init", "true");
				return "operations/retrait :: xpanel";
			}

			String success = MessageTextService.succesOperation(op.getMontant(), "un compte client", op.getIdOperation()+"", "RETRAIT");
			model.addAttribute("success", success);
			model.addAttribute("idOperation", op.getIdOperation());
			model.addAttribute("form", "false");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			model.addAttribute("errorMessage", e.getMessage());
			return "redirect:/retrait";
		}
		
		return "operations/retrait :: xpanel";
	}
	
	@RequestMapping(value = "confirmation", method = RequestMethod.POST)
	public String confirmation(@ModelAttribute OperationForm retraitForm, Errors errors, 
			RedirectAttributes ra, Model model){
		
		String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
		Account agentAccount = accountRepository.findOneByUsername(loggedUser);
		
		try {
			Operation op = operationService.retraitConfirmation(agentAccount.getUserId(), retraitForm.getNumero());
			
			String success = "Retrait effectué avec succès";
			model.addAttribute("success", success);
			model.addAttribute("idOperation", op.getIdOperation());
		} catch (Exception e) {
			e.printStackTrace();
			if(e.getMessage().startsWith("Erreur")){
				errors.reject("error.app.binding.webform", "Exception au Depot:");
				model.addAttribute("errorMessage", e.getMessage());
				addError(errors, model, e.getMessage());
				return CONF_VIEW_NAME;
			}
		}
		return CONF_VIEW_NAME;
	}
}
