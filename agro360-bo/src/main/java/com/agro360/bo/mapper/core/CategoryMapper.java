package com.agro360.bo.mapper.core;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.core.CategoryBean;
import com.agro360.bo.mapper.common.AbstractMapper;
import com.agro360.dto.core.CategoryDto;

@Component
public class CategoryMapper extends AbstractMapper {

	public CategoryBean map(CategoryDto dto) {
		var bean = new CategoryBean();
		
		bean.getCategoryCode().setValue(dto.getCategoryCode());
		bean.getDescription().setValue(dto.getDescription());
		
		if( dto.getParent() != null ) {
			bean.getParentCategoryCode().setValue(dto.getParent().getCategoryCode());
		}
		return bean;
	}
}
