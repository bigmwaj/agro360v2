package com.agro360.operation.logic.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.core.CategoryBean;
import com.agro360.dao.common.IDao;
import com.agro360.dao.core.ICategoryDao;
import com.agro360.dto.core.CategoryDto;
import com.agro360.operation.logic.common.AbstractCategoryOperation;

@Service("core/CategoryOperation")
public class CategoryOperation extends AbstractCategoryOperation<CategoryDto, CategoryBean> {

	@Autowired
	private ICategoryDao dao;

	@Override
	protected IDao<CategoryDto, String> getDao() {
		return dao;
	}

	@Override
	protected CategoryBean getBeanInstance() {
		return new CategoryBean();
	}

	@Override
	protected CategoryDto getDtoInstance() {
		return new CategoryDto();
	}

}
