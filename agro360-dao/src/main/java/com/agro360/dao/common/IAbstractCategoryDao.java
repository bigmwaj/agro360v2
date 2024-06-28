package com.agro360.dao.common;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.agro360.dto.common.AbstractCategoryDto;

@NoRepositoryBean
public interface IAbstractCategoryDao<T extends AbstractCategoryDto<?>> extends IDao<T, String>{

	List<T> findAllByParentCategoryCode(String categoryCode);
}
