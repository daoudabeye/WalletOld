package org.wallet.account;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	Account findOneByUsername(String username);
	
	Account findByUserId(BigInteger userId);
}