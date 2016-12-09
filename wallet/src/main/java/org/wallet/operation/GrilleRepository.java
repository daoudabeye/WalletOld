package org.wallet.operation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GrilleRepository extends JpaRepository<GrilleTarrifaire, GrilleTarrifairePK> {

	GrilleTarrifaire findOneByValeurMaxAndValeurMin(Double max, Double min);
	
	@Query("select g from GrilleTarrifaire g where g.valeurMin <= :montant AND g.valeurMax >= :montant")
	GrilleTarrifaire find(@Param("montant") Double montant);
}
