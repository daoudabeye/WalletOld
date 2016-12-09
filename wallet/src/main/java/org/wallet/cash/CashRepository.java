package org.wallet.cash;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CashRepository extends JpaRepository<Cash, BigInteger> {
	
	Cash findByIdCodeAndIdMotDePasse(String code, String motdepasse);
	
	Cash findByIdCode(String code);
}
