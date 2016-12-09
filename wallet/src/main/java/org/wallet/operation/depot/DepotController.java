package org.wallet.operation.depot;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
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
public class DepotController {

	private static final String DEPOT_VIEW_NAME = "operations/depot";
	
	@Autowired
	TelephoneRepository telephoneRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	OperationService opertionService;
	
	@RequestMapping(value = "depot")
	public String depot(Model model) {
		model.addAttribute(new OperationForm());
		model.addAttribute("user",new Utilisateur());
        return DEPOT_VIEW_NAME;
	}
	
	@RequestMapping(value = "depot", method = RequestMethod.POST)
	public String depot(@Valid @ModelAttribute OperationForm depotForm, Errors errors, RedirectAttributes ra, Model model) throws Exception{
		
		if (errors.hasErrors()) {
			addError(errors, model, "Erreur : champ non validé");
			return DEPOT_VIEW_NAME;
		}
		
		String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
		Account agentAccount = accountRepository.findOneByUsername(loggedUser);
		Telephone phoneAgent = telephoneRepository.findFirst1ByStatus(agentAccount.getUserId());
		
		if(phoneAgent == null){
			addError(errors, model, "Aucun numero Actif, "
					+ "pour faire des operations vous devez avoir au moins un numero actif");
			return DEPOT_VIEW_NAME;
		}
		
		try {
			String info = "Reçu par "+depotForm.getNom()+" "+depotForm.getPrenom();
			
			Operation op = opertionService.webDepot(phoneAgent.getPhone(), depotForm.getNumero(),Double.valueOf(depotForm.getMontant()), info);
			
			String success = MessageTextService.succesOperation(op.getMontant(), depotForm.getNumero(), op.getIdOperation()+"", "DEPOT");
			model.addAttribute("success", success);
			model.addAttribute("idOperation", op.getIdOperation());
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if(e.getMessage().startsWith("Erreur")){
				errors.reject("error.app.binding.webform", "Erreue lors du Depot:");
				model.addAttribute("errorMessage", e.getMessage());
				addError(errors, model, e.getMessage());
				return DEPOT_VIEW_NAME;
			}
			throw new Exception(e.getMessage());
		}
		
		return DEPOT_VIEW_NAME;
	}
	
	private void addError(Errors errors, Model model, String msg){
		errors.reject("error.app.binding.webform", msg);
		model.addAttribute("errorMessage", msg);
		model.addAttribute("user",new Utilisateur());
	}
	
}
