package com.agro360.dao.core;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IAbstractCategoryDao;
import com.agro360.dto.core.CategoryDto;

@Repository("core/ICategoryDao")
public interface ICategoryDao extends IAbstractCategoryDao<CategoryDto>{

}
