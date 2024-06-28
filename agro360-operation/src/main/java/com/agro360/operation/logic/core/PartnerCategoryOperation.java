package com.agro360.operation.logic.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.core.CategoryBean;
import com.agro360.bo.bean.core.PartnerCategoryBean;
import com.agro360.bo.mapper.CoreMapper;
import com.agro360.dao.common.IAbstractCategoryDao;
import com.agro360.dao.common.IDao;
import com.agro360.dao.core.ICategoryDao;
import com.agro360.dao.core.IPartnerCategoryDao;
import com.agro360.dto.core.CategoryDto;
import com.agro360.dto.core.PartnerCategoryDto;
import com.agro360.dto.core.PartnerCategoryPk;
import com.agro360.operation.logic.common.AbstractAssignmentOperation;

@Service
public class PartnerCategoryOperation extends AbstractAssignmentOperation<CategoryBean, 
		CategoryDto, 
		PartnerCategoryBean,
		PartnerCategoryDto, 
		PartnerCategoryPk>{

	@Autowired
	private IPartnerCategoryDao dao;

	@Override
	protected IDao<PartnerCategoryDto, PartnerCategoryPk> getDao() {
		return dao;
	}
	
	@Autowired
	private ICategoryDao categoryDao;

	@Override
	protected IAbstractCategoryDao<CategoryDto> getCategoryDao() {
		return categoryDao;
	}

	@Override
	protected PartnerCategoryDto newInstance(CategoryDto category) {
		return new PartnerCategoryDto(category);
	}

	@Override
	protected PartnerCategoryBean map(CategoryDto dto) {
		return CoreMapper.map(dto);
	}

	
}
