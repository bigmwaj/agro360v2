package com.agro360.service.mapper.tiers;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.agro360.dao.tiers.ICategoryDao;
import com.agro360.dto.tiers.CategoryDto;
import com.agro360.service.bean.tiers.CategoryBean;
import com.agro360.service.mapper.common.AbstractMapper;

@Component
public class CategoryMapper extends AbstractMapper {

	public final static String OPTION_HIRARCHIE_DEEP_KEY = "DEEP";

	@Autowired
	private ICategoryDao categoryDao;

	public CategoryBean mapToBean(CategoryDto dto) {
		return mapToBean(dto, Collections.emptyMap());
	}

	public CategoryBean mapToBean(CategoryDto dto, Map<String, Object> options) {
		CategoryBean bean = new CategoryBean();
		bean.getCategoryCode().setValue(dto.getCategoryCode());
		bean.getDescription().setValue(dto.getDescription());
		Object deep = options.getOrDefault(OPTION_HIRARCHIE_DEEP_KEY, null);
		if (deep instanceof Integer && ((Integer) deep) > 0) {
			Integer remainDeep = ((Integer) deep) - 1;
			List<CategoryBean> children = findChildren(dto).stream()
					.map(e -> mapToBean(e, Map.of(OPTION_HIRARCHIE_DEEP_KEY, remainDeep))).toList();
			bean.getChildren().addAll(children);
		}
		return bean;
	}

	public CategoryDto mapToDto(CategoryBean bean) {
		var dto = new CategoryDto();
		dto.setCategoryCode(bean.getCategoryCode().getValue());

		if (categoryDao.existsById(dto.getCategoryCode())) {
			dto = categoryDao.getById(dto.getCategoryCode());
		}
		setDtoValue(dto::setDescription, bean.getDescription());

		return dto;
	}

	private List<CategoryDto> findChildren(CategoryDto parentDto) {
		var ex = Example.of(new CategoryDto());
		ex.getProbe().setParent(new CategoryDto());
		ex.getProbe().getParent().setCategoryCode(parentDto.getCategoryCode());
		return categoryDao.findAll(ex);
	}
}
