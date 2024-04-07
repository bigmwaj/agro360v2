package com.agro360.dao.core;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.core.CategoryDto;

@Repository
public interface ICategoryDao extends IDao<CategoryDto, String>{

}
