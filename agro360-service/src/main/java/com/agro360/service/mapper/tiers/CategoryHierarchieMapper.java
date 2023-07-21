package com.agro360.service.mapper.tiers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.tiers.ICategoryDao;
import com.agro360.dto.tiers.CategoryDto;
import com.agro360.service.bean.tiers.CategoryHierarchieBean;
import com.agro360.service.mapper.common.AbstractMapper;

@Component
public class CategoryHierarchieMapper extends AbstractMapper {

	@Autowired
	private ICategoryDao categoryDao;

	public CategoryHierarchieBean mapToBean(CategoryDto dto) {
		CategoryHierarchieBean bean = new CategoryHierarchieBean();
		bean.getCategoryCode().setValue(dto.getCategoryCode());
		bean.getDescription().setValue(dto.getDescription());

		return bean;
	}

	public CategoryDto mapToDto(CategoryHierarchieBean bean) {
		CategoryDto dto = new CategoryDto();
		dto.setCategoryCode(bean.getCategoryCode().getValue());
		
		if (categoryDao.existsById(dto.getCategoryCode())) {
			dto = categoryDao.getById(dto.getCategoryCode());
		}
		dto.setDescription(bean.getDescription().getValue());

		return dto;
	}
}
