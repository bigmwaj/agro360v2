package com.agro360.dao.finance;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.finance.TransactionDto;

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
}
