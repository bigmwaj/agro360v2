package com.agro360.dao.finance;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.finance.TransactionDto;
import com.agro360.vd.finance.TransactionStatusEnumVd;
import com.agro360.vd.finance.TransactionTypeEnumVd;

@Repository
public interface ITransactionDao extends IDao<TransactionDto, String>{
	
	@Query(
	value ="select dto.transaction_type as type, sum(dto.montant) as montant,"
		+ " adddate(dto.transaction_date, 6 - weekday(dto.transaction_date)) semaine "
		+ " from fin_tbl_transaction dto "
		+ " where dto.transaction_type = :type and status not in ('ANNULEE')"
		+ " group by dto.transaction_type, semaine"
		+ " having semaine >= :debut and semaine <= :fin",
		nativeQuery = true
	)
	List<Map<String, Object>> calculerCumul(
		@Param("type") String type, 
		@Param("debut") LocalDate debut,
		@Param("fin") LocalDate fin
	);
	
	@Query(
		value = "   select dto from com.agro360.dto.finance.TransactionDto dto"
				+ " where (:code is null or dto.transactionCode like %:code%)"
				+ " and (:type is null or dto.type = :type)"
				+ " and (:dateDebut is null or dto.date >= :dateDebut) "
				+ " and (:dateFin is null or dto.date <= :dateFin)"
				+ " and (:status is null or dto.status in (:status))"
				+ " and (:compte is null or dto.compte.compteCode = :compte)"
				+ " and (:rubrique is null or dto.rubrique.rubriqueCode = :rubrique)"
				+ " and (:partner is null"
				+ "		or (dto.partner.partnerCode like %:partner%)"
				+ "		or (upper(dto.partner.name) like %:partner%)"
				+ "		or (upper(dto.partner.firstName) like %:partner%)"
				+ "		or (upper(dto.partner.lastName) like %:partner%)"
				+ ")"
				+ "order by dto.transactionCode asc "
				+ "limit :limit offset :offset"
			
	)
	List<TransactionDto> findTransactionsByCriteria(
		@Param("offset") Integer offset, 
		@Param("limit") Short limit,
		@Param("code") String code, 
		@Param("type") TransactionTypeEnumVd type, 
		@Param("dateDebut") LocalDate dateDebut, 
		@Param("dateFin") LocalDate dateFin,
		@Param("status") List<TransactionStatusEnumVd> status,
		@Param("partner")String partner, 
		@Param("compte")String compte, 
		@Param("rubrique")String rubrique
	);

	@Query(
		value = "   select count(dto) from com.agro360.dto.finance.TransactionDto dto"
				+ " where (:code is null or dto.transactionCode like %:code%)"
				+ " and (:type is null or dto.type = :type)"
				+ " and (:dateDebut is null or dto.date >= :dateDebut) "
				+ " and (:dateFin is null or dto.date <= :dateFin)"
				+ " and (:status is null or dto.status in (:status))"
				+ " and (:compte is null or dto.compte.compteCode = :compte)"
				+ " and (:rubrique is null or dto.rubrique.rubriqueCode = :rubrique)"
				+ " and (:partner is null"
				+ "		or (dto.partner.partnerCode like %:partner%)"
				+ "		or (upper(dto.partner.name) like %:partner%)"
				+ "		or (upper(dto.partner.firstName) like %:partner%)"
				+ "		or (upper(dto.partner.lastName) like %:partner%)"
				+ ")"
			
	)
	Long countTransactionsByCriteria(
		@Param("code") String code, 
		@Param("type") TransactionTypeEnumVd type, 
		@Param("dateDebut") LocalDate dateDebut, 
		@Param("dateFin") LocalDate dateFin,
		@Param("status") List<TransactionStatusEnumVd> status,
		@Param("partner")String partner, 
		@Param("compte")String compte, 
		@Param("rubrique")String rubrique
	);
}
