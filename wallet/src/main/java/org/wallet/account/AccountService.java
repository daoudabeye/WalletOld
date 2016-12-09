package org.wallet.account;

import java.math.BigInteger;
import java.util.Collections;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wallet.utilisateur.UtilisateurService;

@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class AccountService implements UserDetailsService {
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Autowired
	private UtilisateurService utilisateurService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostConstruct	
	protected void initialize() {
//		Utilisateur user = new Utilisateur("A", new Date(), "admin@wonderbank.com",
//				"HOMME", "admin", "ADMIN", "MALI", "08PV3", "AZER", "DAOUDA", "BAMAKO");
//		user = userRepo.save(user);
//
//		Account accn = new Account("admin", "admin", "ADMIN");
//		accn.setUserId(user.getUserId());
//		System.err.println(user.getUserId());
//		save(accn);
	}

	@Transactional
	public Account save(Account account) {
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		System.err.println(account.getUserId());
		accountRepository.save(account);
		return account;
	}
	
	@Transactional
	public String changerPass(BigInteger userId, String nouveau, String ancien, Boolean reset, Boolean generer){
		Account account = accountRepository.findByUserId(userId);
		
		if(account == null)
			return "Compte inexistant";
		String generated = RandomStringUtils.randomAlphanumeric(8);
		if(generer)
			account.setPassword(passwordEncoder.encode(generated));
		else if(reset)
			account.setPassword(passwordEncoder.encode("lemonway"));
		else
			account.setPassword(passwordEncoder.encode(nouveau));
		
		accountRepository.save(account);
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account account = accountRepository.findOneByUsername(username);
		
		if(account == null ) {
			throw new UsernameNotFoundException("user not found");
		}
		
		Boolean isLocked = utilisateurService.isBlocked(account.getUserId());
		if(isLocked){
			throw new UsernameNotFoundException("Compte bloqué!");
		}
		
		return createUser(account);
	}
	
	public void signin(Account account) {
		SecurityContextHolder.getContext().setAuthentication(authenticate(account));
	}
	
	private Authentication authenticate(Account account) {
		return new UsernamePasswordAuthenticationToken(createUser(account), null, Collections.singleton(createAuthority(account)));		
	}
	
	private User createUser(Account account) {
		return new User(account.getUsername(), account.getPassword(), Collections.singleton(createAuthority(account)));
	}

	private GrantedAuthority createAuthority(Account account) {
		return new SimpleGrantedAuthority(account.getRole());
	}

}
