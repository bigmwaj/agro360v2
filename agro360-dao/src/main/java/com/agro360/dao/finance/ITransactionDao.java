package com.agro360.dao.finance;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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
			value = "select \r\n"
					+ "	sum(\r\n"
					+ "    case transaction_type\r\n"
					+ "		when 'DEPENSE' then -montant\r\n"
					+ "		when 'RECETTE' then montant\r\n"
					+ "		else 0\r\n"
					+ "	end) as montant\r\n"
					+ "from fin_tbl_transaction where status not in ('ANNULEE', 'ENCOURS')", 
			nativeQuery = true
		)
	BigDecimal calculateBenefice();
	
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
			
	)
	List<TransactionDto> findTransactionsByCriteria(
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
