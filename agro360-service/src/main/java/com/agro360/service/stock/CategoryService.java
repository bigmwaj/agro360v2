package com.agro360.service.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.stock.CategoryBean;
import com.agro360.dto.stock.CategoryDto;
import com.agro360.operation.logic.common.AbstractCategoryOperation;
import com.agro360.operation.logic.stock.CategoryOperation;
import com.agro360.service.common.AbstractCategoryService;

@Transactional(rollbackFor = Exception.class)
@Service("stock/CategoryService")
public class CategoryService extends AbstractCategoryService<CategoryBean> {

	@Autowired
	private CategoryOperation operation;

	@SuppressWarnings("unchecked")
	@Override
	protected AbstractCategoryOperation<CategoryDto, CategoryBean> getOperation() {
		return operation;
	}
	
}