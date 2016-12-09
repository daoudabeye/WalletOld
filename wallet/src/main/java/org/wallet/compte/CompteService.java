package org.wallet.compte;

import java.math.BigDecimal;
import java.math.BigInteger;

import javax.transaction.Transactional;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class CompteService {

	@Autowired
	CompteRepository compteRepository;
	
	public Compte find(BigInteger userId){
		return compteRepository.findByUserId(userId);
	}
	
	@Transactional
	public String changerPin(BigInteger userId, String nouveau, String ancien, Boolean reset, Boolean generer){
		Compte compte = compteRepository.findByUserId(userId);
		if(compte == null)
			return "Compte inexistant";
		if(compte.getGeler())
			return "Compte geler";
		if(!compte.getPin().equals(ancien.trim()))
			return "Ancien code incorrect";
		if(generer)
			compte.setPin(RandomStringUtils.randomNumeric(4));
		else if(reset)
			compte.setPin("0000");
		else
			compte.setPin(nouveau);
		
		compteRepository.save(compte);
		return null;
	}
	
	@Transactional
	public String geler(BigInteger userId, Boolean geler){
		Compte compte = compteRepository.findByUserId(userId);
		if(compte == null)
			return "Compte inexistant";
		if(geler)
			compte.setGeler(true);
		else
			compte.setGeler(false);
		return null;
	}
	
	@Transactional
	public String etat(BigInteger userId, String newEtat){
		Compte compte = compteRepository.findByUserId(userId);
		if(compte == null)
			return "Compte inexistant";
		compte.setEtat(newEtat);
		return null;
	}
	
	@Transactional
	public Boolean debiter(BigInteger userId, Double montant){
		BigDecimal m = BigDecimal.valueOf(montant);
		compteRepository.debiterSolduv(m, userId);
		return true;
	}
	
	@Transactional
	public Boolean crediter(BigInteger userId, Double montant){
		BigDecimal m = BigDecimal.valueOf(montant);
		compteRepository.crediterSolduv(m, userId);
		return true;
	}
	
	@Transactional
	public Boolean debiterCrediter(BigInteger compteDebiter, Double montantDebiter, BigInteger compteCrediter, Double montantCrditer){
		BigDecimal d = BigDecimal.valueOf(montantDebiter);
		BigDecimal c = BigDecimal.valueOf(montantCrditer);
		
		Compte cpt = compteRepository.getOne(compteDebiter);
		if((cpt.getSolduv().doubleValue() - montantDebiter) < 0)
			return false;
		
		Assert.isTrue(!((cpt.getSolduv().doubleValue() - montantDebiter) < 0), "solde debiteur Inssuffisant");
		
		compteRepository.debiterSolduv(d, compteDebiter);
		compteRepository.crediterSolduv(c, compteCrediter);
		
		return true;
	}
}
