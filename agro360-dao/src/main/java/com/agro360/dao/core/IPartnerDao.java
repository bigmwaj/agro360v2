package com.agro360.dao.core;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.core.PartnerDto;
import com.agro360.vd.core.PartnerStatusEnumVd;
import com.agro360.vd.core.PartnerTypeEnumVd;

@Repository
public interface IPartnerDao extends IDao<PartnerDto, String>{

	@Query(
		value = "   select dto from com.agro360.dto.core.PartnerDto dto"
				+ " where (:code is null or dto.partnerCode like %:code%)"
				+ " and (:type is null or dto.type = :type)"
				+ " and (:status is null or dto.status in (:status))"
				+ " and (:city is null or dto.city = :city)"
				+ " and (:phone is null or dto.phone = :phone)"
				+ " and (:email is null or dto.email = :email)"
				+ " and (:name is null"
				+ "		or (upper(dto.name) like %:name%)"
				+ "		or (upper(dto.firstName) like %:name%)"
				+ "		or (upper(dto.lastName) like %:name%)"
				+ ")"
				+ "order by dto.partnerCode asc "
				+ "limit :limit offset :offset"
			
	)
	List<PartnerDto> findPartnersByCriteria(
			@Param("offset") Integer offset, 
			@Param("limit") Short limit,
			@Param("code") String code, 
			@Param("type") PartnerTypeEnumVd type, 
			@Param("city") String city, 
			@Param("phone") String phone,
			@Param("email") String email,
			@Param("status") List<PartnerStatusEnumVd> status,
			@Param("name")String name
	);
	

	@Query(
		value = "   select count(dto) from com.agro360.dto.core.PartnerDto dto"
				+ " where (:code is null or upper(dto.partnerCode) like %:code%)"
				+ " and (:type is null or dto.type = :type)"
				+ " and (:status is null or dto.status in (:status))"
				+ " and (:city is null or dto.city = :city)"
				+ " and (:phone is null or dto.phone = :phone)"
				+ " and (:email is null or dto.email = :email)"
				+ " and (:name is null"
				+ "		or (upper(dto.name) like %:name%)"
				+ "		or (upper(dto.firstName) like %:name%)"
				+ "		or (upper(dto.lastName) like %:name%)"
				+ ")"
			
	)
	Long countPartnersByCriteria(
		@Param("code") String code, 
		@Param("type") PartnerTypeEnumVd type, 
		@Param("city") String city, 
		@Param("phone") String phone,
		@Param("email") String email,
		@Param("status") List<PartnerStatusEnumVd> status,
		@Param("name")String name
	);
}
