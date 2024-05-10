package com.agro360.dao.av;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.av.FactureDto;
import com.agro360.vd.av.FactureStatusEnumVd;
import com.agro360.vd.av.FactureTypeEnumVd;

@Repository
public interface IFactureDao extends IDao<FactureDto, String>{
	
	@Query(
		value ="select "
			+ " sum(prixTotal - cumulPaiement)"
			+ " from com.agro360.dto.av.FactureDto dto"
			+ " where dto.type = :type and dto.status not in ('ANNL', 'CLOT')"
	)
	BigDecimal calculerDettes(@Param("type") FactureTypeEnumVd type);
	
	@Query(
		value = "   select dto from com.agro360.dto.av.FactureDto dto"
				+ " where (:code is null or dto.factureCode like %:code%)"
				+ " and (:type is null or dto.type = :type)"
				+ " and (:dateDebut is null or dto.date >= :dateDebut) "
				+ " and (:dateFin is null or dto.date <= :dateFin)"
				+ " and (:status is null or dto.status in (:status))"
				+ " and (:partner is null"
				+ "		or (dto.partner.partnerCode like %:partner%)"
				+ "		or (upper(dto.partner.name) like %:partner%)"
				+ "		or (upper(dto.partner.firstName) like %:partner%)"
				+ "		or (upper(dto.partner.lastName) like %:partner%)"
				+ ")"
				+ "order by dto.factureCode "
				+ "limit :limit offset :offset"
			
	)
	List<FactureDto> findFacturesByCriteria(
		@Param("offset") Integer offset, 
		@Param("limit") Short limit,
		@Param("code") String code, 
		@Param("type") FactureTypeEnumVd type, 
		@Param("dateDebut") LocalDate dateDebut, 
		@Param("dateFin") LocalDate dateFin,
		@Param("status") List<FactureStatusEnumVd> status,
		@Param("partner")String partner
	);
	
	@Query(
		value = "   select count(dto) from com.agro360.dto.av.FactureDto dto"
				+ " where (:code is null or dto.factureCode like %:code%)"
				+ " and (:type is null or dto.type = :type)"
				+ " and (:dateDebut is null or dto.date >= :dateDebut) "
				+ " and (:dateFin is null or dto.date <= :dateFin)"
				+ " and (:status is null or dto.status in (:status))"
				+ " and (:partner is null"
				+ "		or (dto.partner.partnerCode like %:partner%)"
				+ "		or (upper(dto.partner.name) like %:partner%)"
				+ "		or (upper(dto.partner.firstName) like %:partner%)"
				+ "		or (upper(dto.partner.lastName) like %:partner%)"
				+ ")"
			
	)
	Long countFacturesByCriteria(
		@Param("code") String code, 
		@Param("type") FactureTypeEnumVd type, 
		@Param("dateDebut") LocalDate dateDebut, 
		@Param("dateFin") LocalDate dateFin,
		@Param("status") List<FactureStatusEnumVd> status,
		@Param("partner")String partner
	);

	List<FactureDto> findAllByCommandeCommandeCode(String commandeCode);
	
}
