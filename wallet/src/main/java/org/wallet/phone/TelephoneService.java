package org.wallet.phone;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.wallet.account.Account;

@Service
public class TelephoneService {

	@Autowired
	TelephoneRepository telephoneRepository;

	@Transactional
	public Telephone save(Telephone tel){
		telephoneRepository.save(tel);
		return tel;
	}
	
	@Transactional
	public void principal(BigInteger phoneId, BigInteger userId){
		List<Telephone> phones = telephoneRepository.findByUtilisateurUserId(userId);
		for(Telephone phone : phones){
			if(phone.getId().equals(phoneId))
				phone.setStatus(true);
			else
				phone.setStatus(false);
		}
		
		for(Telephone phone : phones){
			telephoneRepository.updateStatus(phone.getStatus(), phone.getId());
		}
	}
	
	@Transactional
	public Telephone remove(Telephone tel){
		Assert.isTrue(tel.getStatus(), "Impossible de suuprimer, le numero est actif.");
		
		telephoneRepository.delete(tel);
		return tel;
	}
	
	@Transactional
	public Telephone remove(BigInteger id){
		Telephone phone = telephoneRepository.findOne(id);
		System.err.println(phone.getStatus());
		
		Assert.isTrue(!phone.getStatus(), "Impossible de suuprimer, le numero est actif.");
		
		telephoneRepository.delete(phone);
		return phone;
	}

	@Transactional
	public List<Telephone> liste(BigInteger userId){
		return telephoneRepository.findByUtilisateurUserId(userId);
	}

	@Transactional
	public Telephone findOne(BigInteger id){
		return telephoneRepository.findOne(id);
	}

	@Transactional
	public Telephone save(String numero, String operateur, Account accn) throws Exception{
		Telephone activePhone = telephoneRepository.findFirst1ByUtilisateurUserIdAndStatus(accn.getUserId(), true);

		Assert.notNull(activePhone, "Vous ne disposez d'aucun numero Actif");
		Telephone newPhone = new Telephone();
		newPhone.setPhone(numero);
		newPhone.setOperateur(operateur);
		newPhone.setUtilisateur(activePhone.getUtilisateur());
		newPhone.setRole(activePhone.getRole());
		newPhone.setStatus(false);
		newPhone = telephoneRepository.save(newPhone);
		return newPhone;
	}
}
