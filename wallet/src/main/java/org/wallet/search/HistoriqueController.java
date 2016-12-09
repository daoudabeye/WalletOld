package org.wallet.search;

import java.util.ArrayList;
import java.util.List;

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
import org.wallet.operation.OperationService;
import org.wallet.support.web.MessageHelper;

@Controller
public class HistoriqueController {
	
	public static final String HISTORIQUE_VIEW="utilisateur/historique";

	@Autowired
	OperationService operationService;
	
	@Autowired
	AccountRepository accountRepository;
	
	@RequestMapping("/historique")
	public String historique(Model model){
		model.addAttribute(new HistoriqueForm());
		return HISTORIQUE_VIEW;
	}
	
	@RequestMapping(value = "/historique", method = RequestMethod.POST)
	public String getListe(@Valid @ModelAttribute HistoriqueForm historiqueForm, Errors errors, RedirectAttributes ra, Model model){
		
		if (errors.hasErrors()) {
			MessageHelper.addErrorAttribute(ra,"email.message");
			model.addAttribute(historiqueForm);
			return HISTORIQUE_VIEW;
		}
		
		String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
		Account agentAccount = accountRepository.findOneByUsername(loggedUser);
		
		List<Operation> operations = operationService.historique(agentAccount.getUserId(), historiqueForm.getKeywords(),
				historiqueForm.getDebut(), historiqueForm.getFin());
		
		model.addAttribute(historiqueForm);
		if(!operations.isEmpty())
			model.addAttribute("operations", operations);
		System.err.println(operations != null ? operations.size() : "resultat : 0");
		return HISTORIQUE_VIEW;
	}
	
	@ModelAttribute("typeOperation")
	private List<String> populateProfile(){
		List<String> profile = new ArrayList<>();
		profile.add("DEPOT");
		profile.add("RETRAIT");
		profile.add("TRANSFERT");
		profile.add("PAIEMENT");
		profile.add("ENVOI");
		return profile;
	}
}
