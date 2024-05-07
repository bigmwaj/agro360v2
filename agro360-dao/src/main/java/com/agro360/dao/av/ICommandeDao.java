package com.agro360.dao.av;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.av.CommandeDto;
import com.agro360.vd.av.CommandeStatusEnumVd;
import com.agro360.vd.av.CommandeTypeEnumVd;

@Repository
public interface ICommandeDao extends IDao<CommandeDto, String>{

	@Query(
		value = "   select dto from com.agro360.dto.av.CommandeDto dto"
				+ " where (:code is null or dto.commandeCode like %:code%)"
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
				+ "order by dto.commandeCode "
				+ "limit :limit offset :offset"
			
	)
	List<CommandeDto> findCommandesByCriteria(
		@Param("offset") Integer offset, 
		@Param("limit") Short limit,
		@Param("code") String code, 
		@Param("type") CommandeTypeEnumVd type, 
		@Param("dateDebut") LocalDate dateDebut, 
		@Param("dateFin") LocalDate dateFin,
		@Param("status") List<CommandeStatusEnumVd> status,
		@Param("partner")String partner
	);
	
	@Query(
		value = "   select count(dto) from com.agro360.dto.av.CommandeDto dto"
				+ " where (:code is null or dto.commandeCode like %:code%)"
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
	Long countCommandesByCriteria(
		@Param("code") String code, 
		@Param("type") CommandeTypeEnumVd type, 
		@Param("dateDebut") LocalDate dateDebut, 
		@Param("dateFin") LocalDate dateFin,
		@Param("status") List<CommandeStatusEnumVd> status,
		@Param("partner")String partner
	);
}
