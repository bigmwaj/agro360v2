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
				+ " where (:status is null or dto.status = :status)"
				+ " and (:type is null or dto.type = :type)"
				+ " and (:phone is null or dto.phone like :phone)"
				+ " and (:email is null or dto.email like :email)"
				+ " and (:city is null or dto.city like :city)"
				+ " and (:code is null "
				+ "     or upper(dto.partnerCode) like :code"
				+ "		or upper(dto.name) like :code"
				+ "		or upper(dto.firstName) like :code"
				+ "		or upper(dto.lastName) like :code"
				+ ")"
				+ "order by dto.partnerCode asc "
				+ "limit :limit offset :offset"
			
	)
	List<PartnerDto> findPartnersByCriteria(
			@Param("offset") Integer offset, 
			@Param("limit") Short limit,
			@Param("code") String code, 
			@Param("type") PartnerTypeEnumVd type, 
			@Param("status") PartnerStatusEnumVd status,
			@Param("phone") String phone, 
			@Param("email") String email,
			@Param("city")  String city
	);

	@Query(
		value = "   select count(dto) from com.agro360.dto.core.PartnerDto dto"
				+ " where (:status is null or dto.status = :status)"
				+ " and (:type is null or dto.type = :type)"
				+ " and (:phone is null or dto.phone like :phone)"
				+ " and (:email is null or dto.email like :email)"
				+ " and (:city is null or dto.city like :city)"
				+ " and (:code is null"
				+ "     or upper(dto.partnerCode) like :code"
				+ "		or upper(dto.name) like :code"
				+ "		or upper(dto.firstName) like :code"
				+ "		or upper(dto.lastName) like :code"
				+ ")"
			
	)
	Long countPartnersByCriteria(
		@Param("code") String code, 
		@Param("type") PartnerTypeEnumVd type,
		@Param("status") PartnerStatusEnumVd status,
		@Param("phone") String phone, 
		@Param("email") String email,
		@Param("city")  String city
	);
}
