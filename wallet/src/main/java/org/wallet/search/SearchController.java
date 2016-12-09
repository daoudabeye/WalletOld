package org.wallet.search;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.wallet.cash.Cash;
import org.wallet.cash.CashRepository;
import org.wallet.operation.GrilleRepository;
import org.wallet.phone.Telephone;
import org.wallet.phone.TelephoneRepository;
import org.wallet.support.web.MessageHelper;
import org.wallet.utilisateur.Utilisateur;
import org.wallet.utilisateur.UtilisateurService;

@Controller
public class SearchController {

    private static final String SEARCH_VIEW_NAME = "utilisateur/search";
    private static final String GRILLE_VIEW_NAME = "operations/grille";
    
    @Autowired
    UtilisateurService utilisateurService;
    
    @Autowired
	TelephoneRepository telephoneRepository;
    
    @Autowired
    GrilleRepository grilleRepository;
    
	@Autowired
	CashRepository cashRepository;
    
    private List<Utilisateur> user;
    private String key;
    private String critere;

    private String message;
    
	@RequestMapping(value = "search")
	public String search(Model model) {
		model.addAttribute(new SearchForm());	
        return SEARCH_VIEW_NAME;
	}
	
	@RequestMapping(value = "grille")
	public String grille(Model model) {
		model.addAttribute("grilles", grilleRepository.findAll());			
        return GRILLE_VIEW_NAME;
	}
	
	@RequestMapping(value = "search", method = RequestMethod.POST)
	public String search(@Valid @ModelAttribute SearchForm searchForm, Errors errors, RedirectAttributes ra) 
	throws Exception{
		
		if (errors.hasErrors()) {
			MessageHelper.addErrorAttribute(ra,"email.message");
			return SEARCH_VIEW_NAME;
		}
		
		if(searchForm.getCriteria().trim().equals("NUMERO"))
			user = utilisateurService.findByPhone(searchForm.getKeywords());
		else if(searchForm.getCriteria().trim().equals("NOM") || searchForm.getCriteria().trim().equals("PRENOM"))
			user = utilisateurService.findByNomOrPrenom(searchForm.getKeywords());
		else if(searchForm.getCriteria().trim().equals("EMAIL"))
			user = utilisateurService.findByEmail(searchForm.getKeywords());
		
		this.key = searchForm.getKeywords();
		this.critere = searchForm.getCriteria();
		
		if (user == null) {
			errors.reject("error.app.binding.webform", key);
			this.message = "Aucun resultat...";
			this.user = new ArrayList<>();
			return "redirect:/search";
		}
		
		return "redirect:/search";
	}
	
	/**
	 * 
	 * @param numero
	 * @param operation
	 * @return
	 */
	@RequestMapping(value = "/user/{operation}/{numero}", method = RequestMethod.GET)
	public ModelAndView showUser(@PathVariable("numero") String numero, @PathVariable("operation") String operation) {
		Utilisateur user;
		ModelAndView mav = new ModelAndView("operations/"+operation+" :: profile");
		Telephone tel = telephoneRepository.findOneByPhone(numero);
		if(tel != null){
			user = tel.getUtilisateur();
		}else{
			user = new Utilisateur();
			mav.addObject("notFoundMessage", "Numero incorrect ou Compte inexistant !");
			
		}
		
	    mav.addObject("user", user);
	    return mav;
	}
	
	@RequestMapping(value = "utilisateur/bloquer/{userId}")
	public String bloquer(Model model, @PathVariable BigInteger userId){
		utilisateurService.bloquer(userId);
		model.addAttribute("success", "Compte bloqué avec succes");
		model.addAttribute(new SearchForm());
		return SEARCH_VIEW_NAME;
	}
	
	@RequestMapping(value = "utilisateur/activer/{userId}")
	public String activer(Model model, @PathVariable BigInteger userId){
		utilisateurService.debloquer(userId);
		model.addAttribute("success", "Compte bloqué avec succes");
		model.addAttribute(new SearchForm());
		return SEARCH_VIEW_NAME;
	}
	
	@RequestMapping(value = "utilisateur/remove/{userId}")
	public String supprimer(Model model, @PathVariable BigInteger userId){
		utilisateurService.remove(userId);
		model.addAttribute("success", "Compte supprimer avec succes");
		model.addAttribute(new SearchForm());
		return SEARCH_VIEW_NAME;
	}
	
	/**
	 * 
	 * @param model
	 * @param code
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/cash/{type}/{code}", method = RequestMethod.GET)
	public ModelAndView showCash(Model model, @PathVariable("code") String code, @PathVariable("type") String type) {
		
		Cash cash = cashRepository.findByIdCode(code.trim());
		ModelAndView mav = new ModelAndView("cash/"+type+" :: cashdetail");
		if(cash == null){
			mav.addObject("notFoundMessage", "Aucun envoie sur ce code !");
		};
		mav.addObject("cash", cash);
	    return mav;
	}
	
	@ModelAttribute("allCriteria")
	private List<String> populateCriteria(){
		List<String> liste  = new ArrayList<>();
		liste.add("NUMERO ");
		liste.add("NOM ");
		liste.add("PRENOM ");
		liste.add("EMAIL ");
		liste.add("AGENTS ");
		liste.add("CLIENTS ");
		liste.add("LICENCIERS ");
		liste.add("AGREGATEURS ");
		return liste;
	}
	
	@ModelAttribute("message")
	private String populateMsg(){
		return message;
	}
	
	@ModelAttribute("resultList")
	private List<Utilisateur> populateResult(){
		return user;
	}
	
	@ModelAttribute("key")
	private String populateKey(){
		return key;
	}
	
	@ModelAttribute("critere")
	private String populateCritere(){
		return critere;
	}
}
