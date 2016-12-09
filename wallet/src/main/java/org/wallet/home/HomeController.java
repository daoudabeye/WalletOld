package org.wallet.home;

import java.math.BigInteger;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.wallet.account.Account;
import org.wallet.account.AccountRepository;
import org.wallet.commission.CommissionRepository;
import org.wallet.commission.MdrService;
import org.wallet.compte.Compte;
import org.wallet.compte.CompteRepository;
import org.wallet.operation.OperationRepository;
import org.wallet.operation.OperationService;
import org.wallet.phone.TelephoneRepository;

@Controller
public class HomeController {

	@Autowired
	MdrService mdrService;

	@Autowired
	TelephoneRepository telephoneRepository;

	@Autowired
	CompteRepository compteRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	OperationRepository operationRepository;
	
	@Autowired
	OperationService operationService;

	@Autowired
	CommissionRepository commissionRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Principal principal) {
		return principal != null ? "redirect:/home" : "home/welcome";
	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(Principal principal, Model model) {
		Assert.notNull(principal);
		Account account = accountRepository.findOneByUsername(principal.getName());
		BigInteger userId = account.getUserId();
		
		Double sold = 0.0;
		Compte compte = compteRepository.findByUserId(userId);
		if(compte != null) sold = compte.getSolduv().doubleValue();

		Double tCommission = commissionRepository.totalByUtilisateur(userId);
		if(tCommission == null) tCommission = 0.0;
		model.addAttribute("sold", sold);

		model.addAttribute("tCommission", tCommission);
		return "home/dashboard";
	}
}
