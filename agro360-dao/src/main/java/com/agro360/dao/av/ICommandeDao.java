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
				+ " and (:compte is null or dto.compte.compteCode = :compte)"
				+ " and (:ville is null or upper(dto.partner.city) like %:ville%)"
				+ " and (:partner is null"
				+ "		or (dto.partner.partnerCode like %:partner%)"
				+ "		or (upper(dto.partner.name) like %:partner%)"
				+ "		or (upper(dto.partner.firstName) like %:partner%)"
				+ "		or (upper(dto.partner.lastName) like %:partner%)"
				+ ")"
			
	)
	List<CommandeDto> findCommandesByCriteria(
		@Param("code") String code, 
		@Param("type") CommandeTypeEnumVd type, 
		@Param("dateDebut") LocalDate dateDebut, 
		@Param("dateFin") LocalDate dateFin,
		@Param("status") List<CommandeStatusEnumVd> status,
		@Param("partner")String partner, 
		@Param("compte")String compte,
		@Param("ville")String ville
	);
}
