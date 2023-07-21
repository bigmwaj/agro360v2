package com.agro360.dao.tiers;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.tiers.TiersCategoryDto;
import com.agro360.dto.tiers.TiersCategoryPk;

@Repository
public interface ITiersCategoryDao extends IDao<TiersCategoryDto, TiersCategoryPk>{

}
