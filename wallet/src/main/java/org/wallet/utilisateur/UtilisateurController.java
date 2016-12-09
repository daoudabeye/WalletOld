package org.wallet.utilisateur;

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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.wallet.account.Account;
import org.wallet.account.AccountRepository;
import org.wallet.phone.RoleRepository;
import org.wallet.phone.Telephone;
import org.wallet.phone.TelephoneRepository;
import org.wallet.support.web.MessageHelper;

@Controller
public class UtilisateurController {

	private static final String CREER_VIEW_NAME = "utilisateur/nouveau";

	@Autowired
	private UtilisateurService utilisateurService;

	@Autowired
	private UtilisateurRepository utilisateurRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TelephoneRepository telephoneRepository;

	private String message;

	private String success;

	private Boolean update = false;
	private BigInteger updatedUserId;

	@RequestMapping(value = "creer")
	public String nouveau(Model model) {
		model.addAttribute(new UtilisateurForm());		
		return CREER_VIEW_NAME;
	}

	@RequestMapping(value = "creer", method = RequestMethod.POST)
	public String creer(@Valid @ModelAttribute UtilisateurForm utilisateurForm, Errors errors, RedirectAttributes ra) 
			throws Exception{

		if (errors.hasErrors()) {
			MessageHelper.addErrorAttribute(ra,"email.message");
			return CREER_VIEW_NAME;
		}
		try {
			if(!update)
				message = utilisateurService.save(utilisateurForm);
			else
				utilisateurService.update(updatedUserId, utilisateurForm);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception(e);
		}

		if(message != null){
			errors.reject("error.app.binding.webform", message);
			return CREER_VIEW_NAME;
		}
		update = false;
		//WEB-INF/i18n/messages.properties and /WEB-INF/views/homeSignedIn.html
		//MessageHelper.addSuccessAttribute(ra, "signup.success");
		return "redirect:/creer";
	}

	@RequestMapping(value = "getUpdatedUser/{userId}")
	public String update(Model model, @PathVariable BigInteger userId){
		this.update = true;
		Utilisateur user = new Utilisateur();
		Telephone tel;
		String role = "", numero = "", operateur = "";
		Boolean mobile = false;
		if(userId != null){
			user = utilisateurRepository.findOneByUserId(userId);
			Account accn = accountRepository.findByUserId(userId);
			role = accn.getRole();
			tel = telephoneRepository.findOneByPhone(accn.getUsername());
			numero = tel.getPhone();
			operateur = tel.getOperateur();
			mobile = tel.getStatus();

			this.updatedUserId = userId;
		}

		model.addAttribute(new UtilisateurForm(role, user.getGenre(), user.getEmail(), user.getNom(),
				user.getPrenom(), numero, user.getAdresse(), user.getVille(), 
				operateur, mobile == true?"ACTIF":"INACTIF", "0", user.getDateNaissance().toString(), 
						user.getPieceIdentite(), user.getNumeroPiece()));

		return CREER_VIEW_NAME;
	}

	@ModelAttribute("message")
	private String populateMsg(){
		return message;
	}
	
	@ModelAttribute("update")
	private Boolean populateUpdateAtt(){
		return update;
	}

	@ModelAttribute("allProfile")
	private List<String> populateProfile(){
		List<String> profile = new ArrayList<>();
		profile.add("ADMIN");
		profile.add("AGREGATEUR");
		profile.add("LICENCIER");
		profile.add("AGENT");
		profile.add("CAISSE");
		profile.add("CLIENT");
		profile.add("MARCHAND");
		return profile;
	}

	@ModelAttribute("allRegion")
	private List<String> populateRegion(){
		List<String> region = new ArrayList<>();
		region.add("BAMAKO");
		region.add("KAYES");
		region.add("SEGOU");
		region.add("SIKASSO");
		region.add("KOULIKORO");
		region.add("NIONO");
		return region;
	}

	@ModelAttribute("allPiece")
	private List<String> populatePiece(){
		List<String> piece = new ArrayList<>();
		piece.add("CARTE D'IDENTITE");
		piece.add("PERMIS DE CONDUIRE");
		piece.add("PASSPORT");
		piece.add("CARTE MILITAIRE");
		piece.add("CARTE DE SEJOUR");
		piece.add("CARTE CONSULAIRE");
		piece.add("CARTE ETUDIANT");
		piece.add("CARTE PROFESSIONNEL");
		piece.add("NINA");
		return piece;
	}

	@ModelAttribute("allGenre")
	private List<String> populateGenre(){
		List<String> genre  = new ArrayList<>();
		genre.add("HOMME");
		genre.add("FEMME");
		return genre;
	}

	@ModelAttribute("operateur")
	private List<String> populateOperateur(){
		List<String> liste  = new ArrayList<>();
		liste.add("ORANGE ");
		liste.add("MALITEL ");
		return liste;
	}

	@ModelAttribute("mobile")
	private List<String> populateMobile(){
		List<String> liste  = new ArrayList<>();
		liste.add("ACTIF ");
		liste.add("INACTIF ");
		return liste;
	}

	@ModelAttribute("success")
	public String populateSuccess(){
		return success;
	}
}
