package org.wallet.cash;

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
import org.wallet.phone.Telephone;
import org.wallet.phone.TelephoneRepository;
import org.wallet.support.web.MessageTextService;
import org.wallet.utilisateur.Utilisateur;

@Controller
public class CashController {

	private static final String SEND_VIEW_NAME = "cash/cashSend";
	private static final String RECEIPT_VIEW_NAME = "cash/cashReceipt";
	
	@Autowired
	CashRepository cashRepository;
	
	@Autowired
	TelephoneRepository telephoneRepository;
	
	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	CashService cashService;
			
	@RequestMapping(value = "envoidargent")
	public String cash(Model model) {
		model.addAttribute(new CashForm());
        return SEND_VIEW_NAME;
	}
	
	@RequestMapping(value = "receptiondargent")
	public String cashReceipt(Model model) {
		model.addAttribute(new CashReceipForm());
        return RECEIPT_VIEW_NAME;
	}
	
	@RequestMapping(value = "envoidargent", method = RequestMethod.POST)
	public String cash(@Valid @ModelAttribute CashForm cashForm, Errors errors, RedirectAttributes ra, Model model) throws Exception{
		
		if (errors.hasErrors()) {
			addError(errors, model, errors.toString());
			return SEND_VIEW_NAME;
		}
		
		String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
		Account agentAccount = accountRepository.findOneByUsername(loggedUser);
		Telephone phoneAgent = telephoneRepository.findFirst1ByStatus(agentAccount.getUserId());
		if(phoneAgent == null){
			addError(errors, model, "Aucun numero Actif, "
					+ "pour faire un envoi vous devez avoir au moins un numero actif");
			return SEND_VIEW_NAME;
		}
		
		try {
			Operation op = cashService.envoi(phoneAgent.getPhone(), cashForm);
			
			String success = MessageTextService.cash(op.getMontant(), cashForm.getRnumero(), op.getIdOperation()+"", op.getDetail(), "ENVOI");
			model.addAttribute("success", success);
			model.addAttribute("idOperation", op.getIdOperation());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if(e.getMessage().startsWith("Erreur")){
				errors.reject("error.app.binding.webform", e.getMessage());
				model.addAttribute("errorMessage", e.getMessage());
				return SEND_VIEW_NAME;
			}
				
			throw new Exception(e);
		}
		return SEND_VIEW_NAME;
	}
	
	@RequestMapping(value = "receptiondargent", method = RequestMethod.POST)
	public String cashReceipt(@Valid @ModelAttribute CashReceipForm cashReceipForm, Errors errors, RedirectAttributes ra, Model model) throws Exception{
		
		if (errors.hasErrors()) {
			return RECEIPT_VIEW_NAME;
		}
		String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
		Account agentAccount = accountRepository.findOneByUsername(loggedUser);
		Telephone phoneAgent = telephoneRepository.findFirst1ByStatus(agentAccount.getUserId());
		
		try {
			Operation op = cashService.recevoir(phoneAgent.getPhone(), cashReceipForm.getCode(), cashReceipForm.getMotDePasse());
			
			String success = "Reception de "+op.getMontant()+" confirmer avec succès";
			model.addAttribute("success", success);
			model.addAttribute("idOperation", op.getIdOperation());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if(e.getMessage().startsWith("Erreur")){
				errors.reject("error.app.binding.webform", e.getMessage());
				model.addAttribute("errorMessage", e.getMessage());
				return RECEIPT_VIEW_NAME;
			}
				
			throw new Exception(e);
		}
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
        //MessageHelper.addSuccessAttribute(ra, "depot.success");
		return RECEIPT_VIEW_NAME;
	}
	
	private void addError(Errors errors, Model model, String msg){
		errors.reject("error.app.binding.webform", msg);
		model.addAttribute("errorMessage", msg);
		model.addAttribute("user",new Utilisateur());
	}
	
	@ModelAttribute("allPays")
	private List<String> populateRegion(){
		List<String> region = new ArrayList<>();
		region.add("MALI");
		region.add("SENEGAL");
		region.add("COTE D'IVOIRE");
		region.add("NIGER");
		region.add("BOURKINA");
		region.add("LOME");
		return region;
	}

}
