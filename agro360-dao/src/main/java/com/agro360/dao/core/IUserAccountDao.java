package com.agro360.dao.core;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.core.UserAccountDto;

@Repository
public interface IUserAccountDao extends IDao<UserAccountDto, String>{
	
}
