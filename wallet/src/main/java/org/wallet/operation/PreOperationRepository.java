package org.wallet.operation;

import java.math.BigInteger;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PreOperationRepository extends PagingAndSortingRepository<VPreOperation, BigInteger> {

	VPreOperation findByNumero(String numero);
	
	@Query(value = "SELECT v FROM VPreOperation v WHERE v.userId=?1")
	VPreOperation findByUserId(BigInteger userId);
}
