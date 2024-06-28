package com.agro360.operation.logic.common;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;

import com.agro360.bo.bean.common.AbstractCategoryBean;
import com.agro360.dto.common.AbstractCategoryDto;
import com.agro360.operation.context.ClientContext;

public abstract class AbstractCategoryOperation<T extends AbstractCategoryDto<T>, B extends AbstractCategoryBean<B>> 
extends AbstractOperation<T, String> {
	
	protected abstract B getBeanInstance();
	
	protected abstract T getDtoInstance();
	
	public B map(T dto) {
		
		var bean = getBeanInstance();
		
		bean.getCategoryCode().setValue(dto.getCategoryCode());
		bean.getDescription().setValue(dto.getDescription());
		
		if( dto.getParent() != null ) {
			bean.getParentCategoryCode().setValue(dto.getParent().getCategoryCode());
		}
		return bean;
	}
	
	public B createCategory(ClientContext ctx, B bean) {
		var dto = getDtoInstance();
		var categoryCode = bean.getParentCategoryCode().getValue();
		
		setDtoValue(dto::setDescription, bean.getDescription());
		setDtoValue(dto::setCategoryCode, bean.getCategoryCode());
		
		var parent = getDao().getReferenceById(categoryCode);
		dto.setParent(parent);
		
		dto = super.save(ctx, dto);		
		return this.map(dto);
	}
	
	public B updateCategory(ClientContext ctx, B bean) {
		var dto = getDao().getReferenceById(bean.getCategoryCode().getValue());

		setDtoChangedValue(dto::setDescription, bean.getDescription());
		
		dto = super.save(ctx, dto);		
		return this.map(dto);
	}
	
	public void deleteCategory(B bean) {
		var dto = getDao().getReferenceById(bean.getCategoryCode().getValue());
		getDao().delete(dto);
	}

	public List<B> findChildrenCategory(String categoryCode) {
		var example = Example.of(getDtoInstance());
		example.getProbe().setParent(getDtoInstance());
		example.getProbe().getParent().setCategoryCode(categoryCode);
		return getDao().findAll(example).stream().map(this::map).toList();
	}
	
	private void loadChildren(ClientContext ctx, B bean, Integer deep) {
		if( deep > 0 ) {
			var children = findChildrenCategory(bean.getCategoryCode().getValue());
			if( children.isEmpty() ) {
				return;
			}
			bean.getChildren().addAll(children);
			deep--;
			
			for (var child : children) {
				loadChildren(ctx, child, deep);
			}			
		}
	}
	
	public B findCategoryByCode(ClientContext ctx, String categoryCode, Optional<Integer> deep) {
		var dto = getDao().getReferenceById(categoryCode);
		var bean = this.map(dto);
		var deepVal = deep.orElse(0);
		loadChildren(ctx, bean, deepVal);
		return bean;
	}

}
