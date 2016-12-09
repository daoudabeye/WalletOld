package org.wallet.operation;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface OperationRepository extends PagingAndSortingRepository<Operation, BigInteger> {

	/**
	 * Lister toutes les operations
	 */
	List<Operation> findAll();
	
	/**
	 * Rechercher l'Historique des oeprations d'un agent dans un ordre decroissant
	 * @param agentId identifiant de l'agent
	 * @param typeOperation type de l'operation
	 * @return liste des operations
	 */
	List<Operation> findAllByAgentOrderByIdOperationDesc(BigInteger agentId, String typeOperation);
	
	/**
	 * Rechercher l'Historique des oeprations d'un agent dans un ordre decroissant
	 * @param agentId identifiant de l'agent
	 * @param typeOperation type de l'operation
	 * @param pageable systeme de pagination
	 * @return
	 */
	List<Operation> findAllByAgentOrderByIdOperationDesc(BigInteger agentId, String typeOperation, Pageable pageable);
	
	/**
	 * Rechercher l'Historique des oprations d'un client dans un ordre decroissant
	 * @param clientId identifiant du client
	 * @param typeOperation type de l'operation
	 * @return liste des operations
	 */
	List<Operation> findAllByClientOrderByIdOperationDesc(BigInteger clientId, String typeOperation);
	
	/**
	 * Rechercher l'Historique des oprations d'un client dans un ordre decroissant
	 * @param clientId identifiant du client
	 * @param typeOperation type de l'operation
	 * @param pageable systeme de pagination
	 * @return
	 */
	List<Operation> findAllByClientOrderByIdOperationDesc(BigInteger clientId, String typeOperation, Pageable pageable);
	
	/**
	 * Liste des operation en fonction de leurs status
	 * @param status
	 * @return
	 */
	List<Operation> findByStatusOrderByIdOperationDesc(String status, Pageable pageable);
	
	/**
	 * Liste des operations dans une période donnée
	 * @param debut
	 * @param fin
	 * @return
	 */
	List<Operation> findByDateOperationBetweenOrderByIdOperationDesc(Date debut, Date fin);
	
	/**
	 * Historique des operations d'un agent dans une periode donnée
	 * @param agent identifiant de l'agent
	 * @param debut
	 * @param fin
	 * @return
	 */
	List<Operation> findByAgentAndDateOperationBetween(BigInteger agent, Date debut, Date fin, Pageable pageable);
	
	/**
	 * Historique des operations d'un client dans une période donnée
	 * @param client identifiand du client
	 * @param debut
	 * @param fin
	 * @return
	 */
	List<Operation> findByClientAndDateOperationBetweenOrderByIdOperationDesc(BigInteger client, Date debut, Date fin, Pageable pageable);	
	
	/**
	 * Rechercher une operation en fonction de l'id
	 * @param idOperation id de l'operation
	 * @return
	 */
	Operation findOneByIdOperationOrderByIdOperationDesc(BigInteger idOperation);
	
	/**
	 * Rechercher la dernière operation d'un client
	 * @param client identifiant du client
	 * @return
	 */
	Operation findOneByClientOrderByIdOperationDesc(BigInteger client);
	
	/**
	 * Recherche la dernière operation d'un agent
	 * @param agent
	 * @return
	 */
	Operation findOneByAgentOrderByIdOperationDesc(BigInteger agent);
	
	/**
	 * Trouver la dernière operation d'un agent
	 * @param agent
	 * @param type
	 * @return
	 */
	Operation findOneByAgentAndTypeOperationOrderByIdOperationDesc(BigInteger agent, String type);
	
	/**
	 * Rechercher une operation
	 * @param date
	 * @return
	 */
	Operation findOneByDateOperation(Date date);
	
	/**
	 * Trouver la dernière operation d'un agent
	 * @param agent
	 * @param type
	 * @return
	 */
	Operation findFirst1ByAgentAndTypeOperationOrderByIdOperationDesc(BigInteger agent, String type);
	
	/**
	 * Somme de l'ensemble des operations d'un agent
	 * @param userId de l'agent
	 * @param type le type d'operation
	 * @return la somme en valeur Double des operations
	 */
	@Query(value = "SELECT montant FROM v_total_operations WHERE agent = ?1 and type= ?2", nativeQuery = true)
	Double totalByAgent(BigInteger userId, String type);
	
	/**
	 * Nombre d'operations effectuées par l'agent
	 * @param userId
	 * @param type
	 * @return
	 */
	@Query(value = "SELECT nbr FROM v_total_operations WHERE agent = ?1 and type= ?2", nativeQuery = true)
	Double nombreOperaitonByAgent(BigInteger userId, String type);
	
	/**
	 * somme des operations d'un utilisateur quelconque dans un période donnée
	 * @param userId de l'utilisateur
	 * @param type le type d'operation
	 * @param debut borne superieur de l'intervalle
	 * @param fin borne inderieur de l'intervalle
	 * @return la somme en valeur Double des operations
	 */
	@Query(value = "SELECT sum(montant) FROM operations WHERE agent = ?1 and type= ?2 and date_operation between ?3 and ?4", nativeQuery = true)
	Double sommeOperationsByAgent(BigInteger userId, String type, Date debut, Date fin);
	
	/**
	 * Calcule le nombre de transaction par jours
	 * @param type
	 * @param jours
	 * @return
	 */
	@Query(value = "SELECT nbr FROM v_daily_transaction WHERE type= ?1 and jours=?2 and mois=?3 and annee=?4", nativeQuery = true)
	Long dailyOperation(String type, int jours, int mois, int annee);
	
	
	@Query(value = "SELECT nbr FROM v_agent_daytransaction WHERE type= ?1 and jours=?2 and mois=?3 and annee=?4 and agent=?5", nativeQuery = true)
	Long dailyUserOperation(String type, int jours, int mois, int annee, BigInteger userId);
	
	/**
	 * Nombre d'operation par jour effectuées par un agent
	 * @param type opereation(depot, retrait..)
	 * @param jours
	 * @param mois
	 * @param annee
	 * @param agentId identifiant(userId) de l'agent
	 * @return Long
	 */
	@Query(value = "SELECT nbr FROM v_agent_daytransaction WHERE type= ?1 and jours=?2 and mois=?3 and annee=?4 and agent=?5", nativeQuery = true)
	Long dailyAgentOperation(String type, int jours, int mois, int annee, BigInteger agentId);
	
	@Query(value = "SELECT nbr FROM v_client_daytransaction WHERE type= ?1 and jours=?2 and mois=?3 and annee=?4 and client=?5", nativeQuery = true)
	Long dailyClientOperation(String type, int jours, int mois, int annee, BigInteger clientId);
	
	@Query(value = "SELECT montant FROM v_agent_daytransaction WHERE type= ?1 and jours=?2 and mois=?3 and annee=?4 and agent=?5", nativeQuery = true)
	Double dailyAgentMontantOperation(String type, int jours, int mois, int annee, BigInteger agentId);
	
	@Query(value = "SELECT montant FROM v_client_daytransaction WHERE type= ?1 and jours=?2 and mois=?3 and annee=?4 and client=?5", nativeQuery = true)
	Double dailyClientMontantOperation(String type, int jours, int mois, int annee, BigInteger clientId);
	
	/**
	 * Calcule le nombre de transaction par mois
	 * @param type
	 * @param mois
	 * @param annee
	 * @return
	 */
	@Query(value = "SELECT nbr FROM v_month_transaction WHERE type= ?1 and mois = ?2 and annee=?3", nativeQuery = true)
	Long monthOperation(String type, int mois, int annee);
	
	@Query(value = "SELECT nbr FROM v_agent_month_transaction WHERE type= ?1 and mois=?2 and annee=?3 and agent=?4", nativeQuery = true)
	Long monthAgentOperation(String type, int mois, int annee, BigInteger agentId);
	
	@Query(value = "SELECT nbr FROM v_client_month_transaction WHERE type= ?1 and mois=?2 and annee=?3 and client=?4", nativeQuery = true)
	Long monthClientOperation(String type, int mois, int annee, BigInteger clientId);
	/**
	 * Le nombre de transaction par ans
	 * @param type
	 * @param annee
	 * @return
	 */
	@Query(value = "SELECT nbr FROM v_year_transaction WHERE type= ?1 and annee=?2", nativeQuery = true)
	Long yearOperation(String type, int annee);
	
	List<Operation> findFirst20ByStatutCodeOrderByIdOperationAsc(String statutCode);
	
	List<Operation> findFirst100ByAgentAndTypeOperationAndDateOperationBetween(BigInteger agentId, String typeOperation, Date start, Date end);
	
	List<Operation> findFirst100ByClientAndTypeOperationAndDateOperationBetween(BigInteger clientId, String typeOperation, Date start, Date end);
	
	List<Operation> findByAgentAndTypeOperationAndDateOperationAfter(BigInteger agentId, String typeOperation, Date start);
	
	List<Operation> findByClientAndTypeOperationAndDateOperationAfter(BigInteger clientId, String typeOperation, Date start);
	
	@Modifying
	@Query(value = "update operations set code= ?2 where id_operation = ?1 ", nativeQuery = true)
	int updateStatutCode(BigInteger idOperation, String code);
	
	@Modifying
	@Query(value = "update operations set code= ?2, status= ?3 where id_operation = ?1 ", nativeQuery = true)
	int updateStatutAndCode(BigInteger idOperation, String code, String statut);
	
	Operation findFirst1ByTypeOperationAndClientAndStatutCodeOrderByIdOperationDesc(String typeOperation, BigInteger userId, String statutCode);
	
	/**
	 * Historique
	 * @param typeOperation
	 * @param userId
	 * @return
	 */
	List<Operation> findFirst20ByTypeOperationAndAgentOrderByIdOperationDesc(String typeOperation, BigInteger userId);
	List<Operation> findFirst20ByTypeOperationAndClientOrderByIdOperationDesc(String typeOperation, BigInteger userId);
}
