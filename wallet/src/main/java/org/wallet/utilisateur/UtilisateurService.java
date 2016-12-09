package org.wallet.utilisateur;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.castor.core.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wallet.account.Account;
import org.wallet.account.AccountRepository;
import org.wallet.account.AccountService;
import org.wallet.commission.Commission;
import org.wallet.commission.CommissionRepository;
import org.wallet.commission.Mdr;
import org.wallet.commission.MdrRepository;
import org.wallet.commission.MdrService;
import org.wallet.compte.Compte;
import org.wallet.compte.CompteRepository;
import org.wallet.operation.Operation;
import org.wallet.operation.OperationService;
import org.wallet.phone.Role;
import org.wallet.phone.RoleRepository;
import org.wallet.phone.Telephone;
import org.wallet.phone.TelephoneRepository;
import org.wallet.phone.TelephoneService;

@Service
public class UtilisateurService {

	private static final BigInteger CPT_COMMISSION = BigInteger.valueOf(Long.valueOf(2));
	
	@Autowired
	UtilisateurRepository utilisateurRepository;

	@Autowired
	private TelephoneService telephoneService;

	@Autowired
	private TelephoneRepository telephoneRepository;

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	CompteRepository compteRepository;

	@Autowired
	MdrRepository mdrRepository;

	@Autowired
	private OperationService operationService;

	@Autowired
	private CommissionRepository commissionRepository;

	@Autowired
	MdrService mdrService;

	@Transactional
	public Utilisateur save(Utilisateur user){
		utilisateurRepository.save(user);
		return user;
	}

	@Transactional
	public void remove(BigInteger userId){
		utilisateurRepository.delete(userId);
	}

	@Transactional
	public Utilisateur update(Utilisateur user){
		System.out.println(user.getUserId());
		utilisateurRepository.update(user.getNom(), user.getPrenom(), user.getEmail(),
				user.getAdresse(), user.getVille(),	user.getPieceIdentite(), 
				user.getNumeroPiece(), user.getGenre(), user.getUserId());
		return user;
	}

	public Utilisateur findByUserId(BigInteger userId) throws Exception{
		Utilisateur user = utilisateurRepository.findOne(userId);
		if(user == null) throw new Exception();

		return user;
	}

	@Transactional
	public List<Utilisateur> findByNomOrPrenom(String key){
		return utilisateurRepository.findByNomOrPrenom(key, key);
	}

	@Transactional
	public List<Utilisateur> findByEmail(String key){
		List<Utilisateur> userL = new ArrayList<Utilisateur>();
		userL = utilisateurRepository.findByEmail(key);		
		return userL;
	}

	@Transactional
	public List<Utilisateur> findByPhone(String key){
		Telephone phone = telephoneRepository.findOneByPhone(key.trim());
		Utilisateur user;
		if(phone == null)
			return null;

		user = phone.getUtilisateur();
		List<Utilisateur> userL = new ArrayList<Utilisateur>();
		userL.add(user);
		return userL;
	}

	@Transactional
	public String save(UtilisateurForm utilisateurForm){

		String loggedUser = SecurityContextHolder.getContext().getAuthentication().getName();
		Account account = accountRepository.findOneByUsername(loggedUser);
		Telephone phoneAgent = telephoneRepository.findFirst1ByStatus(account.getUserId());
		
		Assert.notNull(phoneAgent, "150 : Vous devez avoir au moins un numero actif.");

		BigDecimal depot = BigDecimal.valueOf(Double.valueOf(utilisateurForm.getDepot()));
		Compte compteAgent = compteRepository.findOne(account.getUserId());

		if(compteAgent.getSolduv().compareTo(depot) < 0)
			return "151 : Solde insuffisant pour effectuer le depot";

		String currentAgentRole = utilisateurRepository.findRole(phoneAgent.getPhone());
		if(currentAgentRole.equals("AGREGATEUR") && utilisateurForm.getProfile().equals("LICENCIER")){
			Long nbrLicencier = mdrService.nbrInscris(account.getUserId(), "LICENCIER");
			if(nbrLicencier > 124)
				return "152 : Vous avez atteint le nombre Maximum de compte LICENCIER autoriser";
		}else if(currentAgentRole.equals("LICENCIER") && utilisateurForm.getProfile().equals("AGENT")){
			Long nbrAgent = mdrService.nbrInscris(account.getUserId(), "AGENT");
			if(nbrAgent > 2)
				return "153 : Vous avez atteint le nombre Maximum de compte AGENT autoriser";
		}

		Utilisateur user = this.save(utilisateurForm.createAccount());
		Compte compte = new Compte(user.getUserId(), "ACTIF", false, "1234", BigDecimal.ZERO, BigDecimal.ZERO);
		compteRepository.save(compte);

		Role role = roleRepository.findByRoleName(utilisateurForm.getProfile());
		telephoneService.save(utilisateurForm.createTelephone(role));

		accountService.save(utilisateurForm.createWebAccount(user.getUserId(), role));

		Mdr currentAgentMdr = mdrRepository.findOne(account.getUserId());
		if(currentAgentMdr != null)
			mdrRepository.save(new Mdr(user.getUserId(), compteAgent.getUserId(), currentAgentMdr.getAgregateurId(), 0, compteAgent.getUserId()));
		else
			mdrRepository.save(new Mdr(user.getUserId(), compteAgent.getUserId(), BigInteger.ONE, 0, compteAgent.getUserId()));

		Double montantDepot = Double.valueOf(utilisateurForm.getDepot());
		if(montantDepot > 100){
			Double commission = 0.0;
			if(account.getRole().equals("AGENT") && montantDepot >= 100)
				commission = 100.0;
			else if((account.getRole().equals("LICENCIER") || account.getRole().equals("AGREGATEUR")) && montantDepot >= 200)
				commission = 200.0;
			
			try {
				operationService.webDepot(phoneAgent.getPhone(), utilisateurForm.getNumero(), montantDepot, "Depot initial");
				commissionRepository.save(new Commission(new Date(), "Depot initial", commission, "DEPOT", account.getUserId()));
				commissionRepository.save(new Commission(new Date(), "Depot initial", -commission, "DEPOT", CPT_COMMISSION));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	@Transactional
	public String update(BigInteger userId,UtilisateurForm utilisateurForm){
		Utilisateur user = new Utilisateur();
		user.setNom(utilisateurForm.getNom());
		user.setPrenom(utilisateurForm.getPrenom());
		user.setEmail(utilisateurForm.getEmail());
		user.setAdresse(utilisateurForm.getAdresse());
		user.setVille(utilisateurForm.getVille());
		user.setPieceIdentite(utilisateurForm.getTypePiece());
		user.setNumeroPiece(utilisateurForm.getNumeroPiece());
		user.setGenre(utilisateurForm.getGenre());
		user.setUserId(userId);

		this.update(user);

		return null;
	}

	@Transactional
	public Boolean isBlocked(BigInteger userId){
		Boolean b = null;
		try {
			b = utilisateurRepository.isLocked(userId);
			if(b == null) return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return b;
	}
	@Transactional
	public Boolean bloquer(BigInteger userId){
		utilisateurRepository.locked(userId);
		return true;
	}
	@Transactional
	public Boolean debloquer(BigInteger userId){
		utilisateurRepository.unlocked(userId);
		return true;
	}
	
	public List<Operation> last20Histo(BigInteger userId, String typeOperation){
		return last20Histo(userId, typeOperation);
	}
}
