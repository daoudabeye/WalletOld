package org.wallet.operation;

import java.math.BigInteger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.wallet.compte.CompteService;
import org.wallet.support.web.MessageTextService;
import org.wallet.utilisateur.Utilisateur;

@Controller
public class BimController {

	private static final BigInteger CEQUESTRE = BigInteger.ONE;
	private static final String 	CEQUESTRE_NB = "7000001";
	
	private static final String D_VIEW_NAME = "bim/debiter";
	private static final String C_VIEW_NAME = "bim/crediter";
	private static final String S_VIEW_NAME = "bim/statut";
	private static final String I_VIEW_NAME = "bim/injecter";

	@Autowired
	OperationService operationService;
	
	@Autowired
	CompteService compteService;
	
	@RequestMapping(value = "statut")
	public String statut(Model model) {
		model.addAttribute("solde", compteService.find(CEQUESTRE).getSolduv());
		return S_VIEW_NAME;
	}

	@RequestMapping(value = "debiter")
	public String debiter(Model model) {
		model.addAttribute(new OperationForm());
		return D_VIEW_NAME;
	}

	@RequestMapping(value = "crediter")
	public String crediter(Model model) {
		model.addAttribute(new OperationForm());
		return C_VIEW_NAME;
	}
	
	@RequestMapping(value = "injecter")
	public String injecter(Model model) {
		model.addAttribute(new OperationForm());
		return I_VIEW_NAME;
	}

	@RequestMapping(value = "crediter", method = RequestMethod.POST)
	public String crediter(@Valid @ModelAttribute OperationForm form, Errors errors, RedirectAttributes ra, Model model) throws Exception{

		if (errors.hasErrors()) {
			addError(errors, model, "Erreur : champ non validé");
			return C_VIEW_NAME;
		}

		try {

			operationService.crediter(CEQUESTRE, Double.valueOf(form.getMontant()));

			String success = "Operation reussie, le Compte à été crédité";
			model.addAttribute("success", success);

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if(e.getMessage().startsWith("Erreur")){
				errors.reject("error.app.binding.webform", "Exception au Depot:");
				model.addAttribute("errorMessage", e.getMessage());
				addError(errors, model, e.getMessage());
				return C_VIEW_NAME;
			}
		}

		return C_VIEW_NAME;
	}

	@RequestMapping(value = "debiter", method = RequestMethod.POST)
	public String debiter(@Valid @ModelAttribute OperationForm form, Errors errors, RedirectAttributes ra, Model model) throws Exception{

		if (errors.hasErrors()) {
			addError(errors, model, "Erreur : champ non validé");
			return C_VIEW_NAME;
		}

		try {

			operationService.debiter(CEQUESTRE, Double.valueOf(form.getMontant()));

			String success = "Operation reussie, le Compte a été débité";
			model.addAttribute("success", success);

		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			if(e.getMessage().startsWith("Erreur")){
				errors.reject("error.app.binding.webform", "Exception au Depot:");
				model.addAttribute("errorMessage", e.getMessage());
				addError(errors, model, e.getMessage());
				return C_VIEW_NAME;
			}
		}

		return C_VIEW_NAME;
	}
	
	@RequestMapping(value = "injecter", method = RequestMethod.POST)
	public String transfert(@Valid @ModelAttribute OperationForm depotForm, Errors errors, RedirectAttributes ra, Model model) throws Exception{
		
		if (errors.hasErrors()) {
			addError(errors, model, "Erreur : champ non validé");
			return I_VIEW_NAME;
		}

		try {
			String info = "effectuer par :"+depotForm.getNom()+" "+depotForm.getPrenom();
			Double montant = Double.valueOf(depotForm.getMontant());
			Operation op = operationService.doOperation("TRANSFERT", CEQUESTRE_NB, depotForm.getNumero(),
					 montant, "-1441", info, false, false);
			
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
				return I_VIEW_NAME;
			}
		}
		
		return I_VIEW_NAME;
	}

	private void addError(Errors errors, Model model, String msg){
		errors.reject("error.app.binding.webform", msg);
		model.addAttribute("errorMessage", msg);
		model.addAttribute("user",new Utilisateur());
	}

}
