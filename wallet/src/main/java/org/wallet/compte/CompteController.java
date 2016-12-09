package org.wallet.compte;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.wallet.account.AccountService;
import org.wallet.phone.Telephone;
import org.wallet.phone.TelephoneRepository;

@Controller
public class CompteController {

	private static final String PIN_VIEW_NAME = "administration/pin";
	private static final String PASS_VIEW_NAME = "administration/motdepasse";

	
	@Autowired
	TelephoneRepository telephoneRepository;
	
	@Autowired
	CompteService compteService;
	
	@Autowired
	AccountService accountService;
	
	private String message;
	
	private String success;
	
	@RequestMapping(value = "pin")
	public String nouveau(Model model) {
		model.addAttribute(new PinForm());		
        return PIN_VIEW_NAME;
	}
	
	@RequestMapping(value = "pin", method = RequestMethod.POST)
	public String changerPin(@Valid @ModelAttribute PinForm pinForm, Errors errors, RedirectAttributes ra) 
	throws Exception{

		Telephone phone = telephoneRepository.findOneByPhone(pinForm.getNumero());
		if (errors.hasErrors() || phone == null) {
			errors.reject("error.app.binding.webform", message);
			this.message="erreur lors du changement :"+ phone== null ? "Numero incorrect" : "";
			return PIN_VIEW_NAME;
		}

		message = compteService.changerPin(phone.getUtilisateur().getUserId(), 
				pinForm.getNouveau(), pinForm.getAncien(), pinForm.getReset(), pinForm.getGenerer());
		
		if(message != null){
			errors.reject("error.app.binding.webform", message);
			return PIN_VIEW_NAME;
		}
		this.success = "Le code pin a ete change avec succes !";
		return "redirect:/pin";
	}

	@RequestMapping(value = "motdepasse")
	public String nouveauPass(Model model) {
		model.addAttribute(new PinForm());		
        return PASS_VIEW_NAME;
	}
	
	@RequestMapping(value = "motdepasse", method = RequestMethod.POST)
	public String changerPass(@Valid @ModelAttribute PinForm pinForm, Errors errors, RedirectAttributes ra) 
	throws Exception{
		
		Telephone phone = telephoneRepository.findOneByPhone(pinForm.getNumero());
		if (errors.hasErrors() || phone == null) {
			errors.reject("error.app.binding.webform", message);
			this.message="erreur lors du changement :"+ phone== null ? "Numero incorrect" : "";
			return PASS_VIEW_NAME;
		}
	
		message = accountService.changerPass(phone.getUtilisateur().getUserId(), 
				pinForm.getNouveau(), pinForm.getAncien(), pinForm.getReset(), pinForm.getGenerer());
		
		if(message != null){
			errors.reject("error.app.binding.webform", message);
			return PASS_VIEW_NAME;
		}
		this.success = "Le mot de passe a ete change avec succes !";
		return "redirect:/motdepasse";
	}
	
	@ModelAttribute("message")
	public String populateMessage(){
		return message;
	}
	
	@ModelAttribute("success")
	public String populateSuccess(){
		return success;
	}
}
