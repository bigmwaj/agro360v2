package com.agro360.dao.tiers;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.tiers.TiersDto;

@Repository
public interface ITiersDao extends IDao<TiersDto, String>{
	
}
