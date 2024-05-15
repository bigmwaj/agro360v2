package com.agro360.operation.logic.core;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.core.CategoryBean;
import com.agro360.dao.common.IDao;
import com.agro360.dao.core.ICategoryDao;
import com.agro360.dto.core.CategoryDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

@Service
public class CategoryOperation extends AbstractOperation<CategoryDto, String> {

	@Autowired
	private ICategoryDao dao;

	@Override
	protected IDao<CategoryDto, String> getDao() {
		return dao;
	}
	
	public CategoryBean map(CategoryDto dto) {
		var bean = new CategoryBean();
		
		bean.getCategoryCode().setValue(dto.getCategoryCode());
		bean.getDescription().setValue(dto.getDescription());
		
		if( dto.getParent() != null ) {
			bean.getParentCategoryCode().setValue(dto.getParent().getCategoryCode());
		}
		return bean;
	}
	
	@RuleNamespace("core/category/create")
	public CategoryBean createCategory(ClientContext ctx, CategoryBean bean) {
		var dto = new CategoryDto();
		
		setDtoValue(dto::setDescription, bean.getDescription());
		setDtoValue(dto::setCategoryCode, bean.getCategoryCode());
		
		var parent = dao.getReferenceById(bean.getParentCategoryCode().getValue());
		dto.setParent(parent);
		
		dto = super.save(ctx, dto);		
		return this.map(dto);
	}
	
	@RuleNamespace("core/category/update")
	public CategoryBean updateCategory(ClientContext ctx, CategoryBean bean) {
		var dto = dao.getReferenceById(bean.getCategoryCode().getValue());

		setDtoChangedValue(dto::setDescription, bean.getDescription());
		
		dto = super.save(ctx, dto);		
		return this.map(dto);
	}
	
	@RuleNamespace("core/category/delete")
	public void deleteCategory(CategoryBean bean) {
		var dto = dao.getReferenceById(bean.getCategoryCode().getValue());
		dao.delete(dto);
	}

	public List<CategoryBean> findChildrenCategory(String categoryCode) {
		var example = Example.of(new CategoryDto());
		example.getProbe().setParent(new CategoryDto());
		example.getProbe().getParent().setCategoryCode(categoryCode);
		return dao.findAll(example).stream().map(this::map).toList();
	}
	
	private void loadChildren(ClientContext ctx, CategoryBean bean, Integer deep) {
		if( deep > 0 ) {
			var children = findChildrenCategory(bean.getCategoryCode().getValue());
			if( children.isEmpty() ) {
				return;
			}
			bean.getChildren().addAll(children);
			deep--;
			
			for (CategoryBean child : children) {
				loadChildren(ctx, child, deep);
			}			
			
		}
	}
	
	public CategoryBean findCategoryByCode(ClientContext ctx, String categoryCode, Optional<Integer> deep) {
		var dto = dao.getReferenceById(categoryCode);
		var bean = this.map(dto);
		var deepVal = deep.orElse(0);
		loadChildren(ctx, bean, deepVal);
		return bean;
	}

}
