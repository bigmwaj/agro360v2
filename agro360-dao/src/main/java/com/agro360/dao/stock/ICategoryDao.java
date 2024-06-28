package com.agro360.dao.stock;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IAbstractCategoryDao;
import com.agro360.dto.stock.CategoryDto;

@Repository("stock/ICategoryDao")
public interface ICategoryDao extends IAbstractCategoryDao<CategoryDto>{

}
