package com.agro360.dao.common;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;

import com.agro360.dto.common.AbstractCategoryDto;
import com.agro360.dto.common.AbstractAssignmentDto;

@NoRepositoryBean
public interface IHierarchisableDao<T extends AbstractCategoryDto<T>, V extends AbstractAssignmentDto<T>, K> extends IDao<V, K>{

	default List<V> findAllCategories(String hierarchieCode){
		throw new RuntimeException("Cette méthode doit n'a pas encore été implémentée!");
	}
}
