package com.agro360.dao.tiers;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.tiers.CategoryDto;

@Repository
public interface ICategoryDao extends IDao<CategoryDto, String>{

}
