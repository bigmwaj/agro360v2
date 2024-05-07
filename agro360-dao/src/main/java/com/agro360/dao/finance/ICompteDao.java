package com.agro360.dao.finance;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.finance.CompteDto;
import com.agro360.vd.finance.CompteTypeEnumVd;

@Repository
public interface ICompteDao extends IDao<CompteDto, String>{
	
	@Query(
		value = "select \r\n"
				+ "sum(\r\n"
				+ "case transaction_type\r\n"
				+ "	when 'DEPENSE' then -montant\r\n"
				+ "	when 'RETRAIT' then -montant\r\n"
				+ "	when 'RECETTE' then montant\r\n"
				+ "	when 'DEPOT' then montant\r\n"
				+ "	else 0\r\n"
				+ "end) as montant\r\n"
				+ "from fin_tbl_transaction where compte_code = :compte", 
		nativeQuery = true
	)
	BigDecimal calculateSolde(@Param("compte") String compte);
	
	@Query(
			value = "   select dto from com.agro360.dto.finance.CompteDto dto"
					+ " left join dto.partner "
					+ " where (:type is null or dto.type = :type)"
					+ " and (:compte is null "
					+ "     or (dto.compteCode like :compte)"
					+ "     or (upper(dto.libelle) like :compte)"
					+ "     or (upper(dto.description) like :compte)"
					+ ")"
					+ " and (:partner is null"
					+ "		or (dto.partner.partnerCode like :partner)"
					+ "		or (upper(dto.partner.name) like :partner)"
					+ "		or (upper(dto.partner.firstName) like :partner)"
					+ "		or (upper(dto.partner.lastName) like :partner)"
					+ ")"
					+ "order by dto.compteCode asc "
					+ "limit :limit offset :offset"
				
		)
		List<CompteDto> findComptesByCriteria(
			@Param("offset") Integer offset, 
			@Param("limit") Short limit,
			@Param("compte") String compte, 
			@Param("type") CompteTypeEnumVd type, 
			@Param("partner")String partner
		);
		
		@Query(
			value = "   select count(dto) from com.agro360.dto.finance.CompteDto dto"
					+ " left join dto.partner "
					+ " where (:type is null or dto.type = :type)"
					+ " and (:compte is null "
					+ "     or (dto.compteCode like :compte)"
					+ "     or (upper(dto.libelle) like :compte)"
					+ "     or (upper(dto.description) like :compte)"
					+ ") "
					+ " and (:partner is null"
					+ "		or (dto.partner is not null and dto.partner.partnerCode like :partner)"
					+ "		or (dto.partner is not null and upper(dto.partner.name) like :partner)"
					+ "		or (dto.partner is not null and upper(dto.partner.firstName) like :partner)"
					+ "		or (dto.partner is not null and upper(dto.partner.lastName) like :partner)"
					+ ")"
				
		)
		Long countComptesByCriteria(
			@Param("compte") String compte, 
			@Param("type") CompteTypeEnumVd type, 
			@Param("partner")String partner
		);
}
