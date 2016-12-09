package org.wallet.phone;

import java.math.BigInteger;
import java.util.List;

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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.wallet.account.Account;
import org.wallet.account.AccountRepository;
import org.wallet.search.SearchForm;

@Controller
public class TelephoneController {

	private static final String LISTE_PHONE_VIEW = "utilisateur/listePhones";
	private static final String EDIT_PHONE_VIEW = "utilisateur/ajouterNumero";

	@Autowired
	TelephoneService telephoneService;

	@Autowired
	AccountRepository accountRepository;

	@RequestMapping("phone/liste")
	public ModelAndView listePhone() throws Exception{
		ModelAndView mav = new ModelAndView(LISTE_PHONE_VIEW);
		List<Telephone> telephones = null ;
		try {
			String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
			Account account = accountRepository.findOneByUsername(loggedUser);

			telephones = telephoneService.liste(account.getUserId());
			mav.addObject("phoneListe",telephones);
			mav.addObject("userId", account.getUserId());
		} catch (Exception e) {
			// TODO: handle exception
		}

		if(telephones == null) throw new Exception("Utilisateur Inconnu");
		return mav;
	}

	@RequestMapping("phone/ajouter")
	public String editerPhone(Model model){
		model.addAttribute("phoneForm", new PhoneForm());
		return EDIT_PHONE_VIEW;
	}
	
	@RequestMapping(value = "phone/ajouter", method = RequestMethod.POST)
	public ModelAndView ajouterPhone(@Valid @ModelAttribute PhoneForm phoneForm, Errors errors, RedirectAttributes ra, Model model) throws Exception{
		
		ModelAndView mav = new ModelAndView(LISTE_PHONE_VIEW);
		try {
			String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
			Account account = accountRepository.findOneByUsername(loggedUser);

			telephoneService.save(phoneForm.getNumero(),phoneForm.getOperateur(), account);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return mav;
	}
	
	@RequestMapping(value = "phone/remove/{phoneId}")
	public String supprimer(Model model, @PathVariable BigInteger phoneId){
		try {
			telephoneService.remove(phoneId);
			model.addAttribute("success", "Compte supprimer avec succes");
			model.addAttribute(new SearchForm());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/phone/liste";
	}
	
	@RequestMapping(value = "phone/principal/{phoneId}")
	public String principal(Model model, @PathVariable BigInteger phoneId){
		try {
			String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
			Account account = accountRepository.findOneByUsername(loggedUser);
			
			telephoneService.principal(phoneId, account.getUserId());;
			model.addAttribute("success", "Numéro activé avec succès");
			model.addAttribute(new SearchForm());
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return "redirect:/phone/liste";
	}
}
