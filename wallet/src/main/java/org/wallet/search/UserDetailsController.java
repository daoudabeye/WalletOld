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
import org.wallet.commission.CommissionRepository;
import org.wallet.operation.Operation;
import org.wallet.operation.PreOperationRepository;
import org.wallet.operation.VPreOperation;
import org.wallet.utilisateur.UpdateForm;
import org.wallet.utilisateur.Utilisateur;
import org.wallet.utilisateur.UtilisateurService;

@Controller
public class UserDetailsController {

	private static final String SEARCH_VIEW_NAME = "utilisateur/details";
	private static final String UPDATE_VIEW_NAME = "utilisateur/update";
	
	@Autowired
	UtilisateurService utilisateurService;
	
	@Autowired
	CommissionRepository commissionRepository;
	
	@Autowired
	PreOperationRepository preOperationRepository;

	@RequestMapping(value = "/search/details/{userId}", method = RequestMethod.GET)
	public ModelAndView showDetails(@PathVariable("userId") String userId) throws Exception {
		ModelAndView mav = new ModelAndView(SEARCH_VIEW_NAME);
		BigInteger id;
		VPreOperation infos;
		
		try {
			id = BigInteger.valueOf(Long.valueOf(userId));
			Utilisateur user = utilisateurService.findByUserId(id);
			
			Double tCommission = commissionRepository.totalByUtilisateur(id);
			if(tCommission == null) tCommission = 0.0;
			
			infos = preOperationRepository.findByUserId(id);
			
			List<Operation> depots = utilisateurService.last20Histo(id, "DEPOT");
			List<Operation> retraits = utilisateurService.last20Histo(id, "RETRAIT");
			
			mav.addObject("user", user);
			mav.addObject("solde", infos.getSold());
			mav.addObject("tCommission", tCommission);
			mav.addObject("depots", depots);
			mav.addObject("retraits", retraits);
			mav.addObject("infos", infos);
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Erreur : numero incorrect");
		}
		return mav;
	}
	
	@RequestMapping(value = "/search/details/modifier/{userId}", method = RequestMethod.GET)
	public ModelAndView modifier(@PathVariable("userId") String userId) throws Exception {
		ModelAndView mav = new ModelAndView(UPDATE_VIEW_NAME);
		BigInteger id;
		
		try {
			id = BigInteger.valueOf(Long.valueOf(userId));
			Utilisateur user = utilisateurService.findByUserId(id);
			
			mav.addObject("userId", user.getUserId());
			
			UpdateForm form = new UpdateForm();
			form.setNom(user.getNom());
			form.setPrenom(user.getPrenom());
			form.setEmail(user.getEmail());
			form.setGenre(user.getGenre());
			form.setNumeroPiece(user.getNumeroPiece());
			form.setTypePiece(user.getPieceIdentite());
			form.setVille(user.getVille());
			form.setAdresse(user.getAdresse());
			
			mav.addObject("utilisateurForm", form);
			
			mav.addObject("allRegion", populateRegion());
			mav.addObject("allPiece", populatePiece());
			mav.addObject("allGenre", populateGenre());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Erreur : numero incorrect");
		}
		
		return mav;
	}
	
	@RequestMapping(value = "/search/details/modifier/{userId}", method = RequestMethod.POST)
	public String modifier(@Valid @ModelAttribute UpdateForm updateForm, Errors errors, RedirectAttributes ra, Model model,
			@PathVariable("userId") String userId) throws Exception {
		if (errors.hasErrors()) {
			addError(errors, model, "Erreur : champ non validé");
			model.addAttribute("utilisateurFrom", updateForm);
			model.addAttribute("errorMessage", "Erreur de validation");
			return UPDATE_VIEW_NAME;
		}
		try {
			Utilisateur user = new Utilisateur();
			user.setUserId(BigInteger.valueOf(Long.valueOf(userId)));
			user.setAdresse(updateForm.getAdresse());
			user.setEmail(updateForm.getEmail());
			user.setGenre(updateForm.getGenre());
			user.setNom(updateForm.getNom());
			user.setPrenom(updateForm.getPrenom());
			user.setNumeroPiece(updateForm.getNumeroPiece());
			user.setPieceIdentite(updateForm.getTypePiece());
			user.setVille(updateForm.getVille());
			
			utilisateurService.update(user);
			
			model.addAttribute("success", "Mise à jour effectuée avec succes");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		
		return "redirect:/search/details/"+userId;	
	}
	
	private void addError(Errors errors, Model model, String msg){
		errors.reject("error.app.binding.webform", msg);
		model.addAttribute("errorMessage", msg);
	}
	
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

	private List<String> populateGenre(){
		List<String> genre  = new ArrayList<>();
		genre.add("HOMME");
		genre.add("FEMME");
		return genre;
	}
}
