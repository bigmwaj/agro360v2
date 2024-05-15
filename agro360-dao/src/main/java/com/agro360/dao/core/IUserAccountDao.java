package com.agro360.dao.core;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.core.UserAccountDto;

@Repository
public interface IUserAccountDao extends IDao<UserAccountDto, String> {

	@Query(
		value = "   select dto from com.agro360.dto.core.UserAccountDto dto"
				+ " left join com.agro360.dto.core.PartnerDto partner "
				+ " on dto.partnerCode = partner.partnerCode "
				+ " where :username is not null"
				+ "		and (upper(partner.email) = :username or upper(partner.phone) = :username)"
				
		)
	Optional<UserAccountDto> findOneByPartnerPhoneOrPartnerEmail(@Param("username") String username);
	
}
