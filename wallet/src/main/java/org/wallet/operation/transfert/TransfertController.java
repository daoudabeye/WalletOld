package org.wallet.operation.transfert;

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
public class TransfertController {

	private static final String TRANS_VIEW_NAME = "operations/transfert";
	
	@Autowired
	TelephoneRepository telephoneRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	OperationService operationService;
	
	@RequestMapping(value = "transfert")
	public String transfert(Model model) {
		OperationForm opF = new OperationForm();
		model.addAttribute(opF);
		model.addAttribute("user",new Utilisateur());
        return TRANS_VIEW_NAME;
	}
	
	@RequestMapping(value = "transfert", method = RequestMethod.POST)
	public String transfert(@Valid @ModelAttribute OperationForm depotForm, Errors errors, RedirectAttributes ra, Model model) throws Exception{
		
		if (errors.hasErrors()) {
			addError(errors, model, "Erreur : champ non validé");
			return TRANS_VIEW_NAME;
		}
		
		String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
		Account agentAccount = accountRepository.findOneByUsername(loggedUser);
		Telephone phoneAgent = telephoneRepository.findFirst1ByStatus(agentAccount.getUserId());
		
		if(phoneAgent == null){
			addError(errors, model, "Aucun numero Actif, "
					+ "pour faire des operations vous devez avoir au moins un numero actif");
			return TRANS_VIEW_NAME;
		}
		
		try {
			String info = "effectuer par :"+depotForm.getNom()+" "+depotForm.getPrenom();
			Double montant = Double.valueOf(depotForm.getMontant());
			Operation op = operationService.webTransfert(phoneAgent.getPhone(), depotForm.getNumero(), montant, info);
			
			String success = MessageTextService.succesOperation(op.getMontant(), depotForm.getNumero(), op.getIdOperation()+"", "TRANSFERT");
			model.addAttribute("success", success);
			model.addAttribute("idOperation", op.getIdOperation());
			
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if(e.getMessage().startsWith("Erreur")){
				errors.reject("error.app.binding.webform", "Exception au Depot:");
				model.addAttribute("errorMessage", e.getMessage());
				addError(errors, model, e.getMessage());
				return TRANS_VIEW_NAME;
			}
		}
		
		return TRANS_VIEW_NAME;
	}
	
	private void addError(Errors errors, Model model, String msg){
		errors.reject("error.app.binding.webform", msg);
		model.addAttribute("errorMessage", msg);
		model.addAttribute("user",new Utilisateur());
	}
	
}
