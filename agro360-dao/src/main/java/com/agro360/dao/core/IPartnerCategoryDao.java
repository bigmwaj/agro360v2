package com.agro360.dao.core;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.core.PartnerCategoryDto;
import com.agro360.dto.core.PartnerCategoryPk;

@Repository
public interface IPartnerCategoryDao extends IDao<PartnerCategoryDto, PartnerCategoryPk>{

}
