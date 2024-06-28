package com.agro360.operation.logic.common;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.agro360.bo.bean.common.AbstractAssignmentBean;
import com.agro360.bo.bean.common.AbstractCategoryBean;
import com.agro360.bo.bean.common.IAssignmentBean;
import com.agro360.dao.common.IAbstractCategoryDao;
import com.agro360.dao.common.IHierarchisableDao;
import com.agro360.dto.common.AbstractAssignmentDto;
import com.agro360.dto.common.AbstractCategoryDto;
import com.agro360.operation.context.ClientContext;

public abstract class AbstractAssignmentOperation<
	C extends AbstractCategoryBean<C>,
	T extends AbstractCategoryDto<T>, 
	B extends AbstractAssignmentBean<B, C>,
	V extends AbstractAssignmentDto<T>, 
	K> extends AbstractOperation<V, K>{

	protected abstract IAbstractCategoryDao<T> getCategoryDao();
	
	protected abstract V newInstance(T category);
	
	protected abstract B map(T dto);

	public B findRoot(ClientContext ctx, int deep) {
		var root = getCategoryDao().getReferenceById("ROOT");
		var bean = map(root);
		
		addChildren(ctx, bean, deep);
		
		return bean;
	}
	
	public void syncAssignments(ClientContext ctx, IAssignmentBean bean, Collection<String> selectedCategoryCodes) {
		var newAssignmentDtos = findNewAssignmentDtos(bean, selectedCategoryCodes);
		var obsoletesAssignmentDtos = findObsoleteAssignmentDtos(bean, selectedCategoryCodes);
		
		if( ! newAssignmentDtos.isEmpty() ) {
			for (var dto : newAssignmentDtos) {
				save(ctx, dto);
			}
		}
		
		if( ! obsoletesAssignmentDtos.isEmpty() ) {
			getDao().deleteAll(obsoletesAssignmentDtos);
		}
	}

	public void deleteAll(ClientContext ctx, IAssignmentBean bean) {
		var dtos = findCategoryDtos(bean);
		getDao().deleteAll(dtos);
	}

	public List<B> findChildren(ClientContext ctx, String categoryCode){
		return getCategoryDao().findAllByParentCategoryCode(categoryCode)
				.stream().map(this::map)
				.collect(Collectors.toList());
	}
	
	public B findAssignmentsFromLeaves(ClientContext ctx, IAssignmentBean bean) {
		
		var dtos = findCategoryDtos(bean);
		
		if( dtos.isEmpty() ) {
			return findRoot(ctx, 5);
		}
		
		var categoryMap = new HashMap<String, B>();
		
		for (var dto : dtos) {
			var category = dto.getCategory();
			
			while( category != null ){
				var code = category.getCategoryCode();
				if( !categoryMap.containsKey(code) ) {
					var categoryBean = map(category);
					categoryBean.getSelected().setValue(true);
					categoryMap.put(code, categoryBean);
				}
				
				var categoryBean = categoryMap.get(code);
				
				var parent = category.getParent();
				if( parent != null ) {
					var parentCode = parent.getCategoryCode();
					if( !categoryMap.containsKey(parentCode) ) {
						var parentBean = this.map(parent);
						categoryMap.put(parentCode, parentBean);
					}
					
					var parentBean = categoryMap.get(parentCode);
					parentBean.getChildren().add(categoryBean);
				}
				
				category = parent;
			}
		}
		
		return categoryMap.get("ROOT");
	}
	
	private List<V> findCategoryDtos(IAssignmentBean bean){
		@SuppressWarnings("unchecked")
		IHierarchisableDao<T, V, K> dao = (IHierarchisableDao<T, V, K>) getDao();
		return dao.findAllCategories(bean.getAssigneeCode());
	}
	
	private void addChildren(ClientContext ctx, B bean, int deep){
		if( deep > 0 ) {
			
			var children = findChildren(ctx, bean.getCategoryCode().getValue());
			bean.getChildren().addAll(children);
			
			for (var child : children) {
				addChildren(ctx, child, deep - 1);
			}			
		}
	}
	
	private List<V> findNewAssignmentDtos(IAssignmentBean bean, Collection<String> categoryCodes){
		var currentCategoryDtos = findCategoryDtos(bean);
		
		Predicate<String> notExists = categoryCode -> currentCategoryDtos.stream()
				.map(AbstractAssignmentDto::getCategory)
				.map(AbstractCategoryDto::getCategoryCode)
				.noneMatch(categoryCode::equals);
		
		return categoryCodes.stream()
				.filter(notExists)
				.map(getCategoryDao()::getReferenceById)
				.map(this::newInstance)
				.map(e -> {e.setAssigneeCode(bean.getAssigneeCode()); return e;})
				.collect(Collectors.toList());
	}
	
	private List<V> findObsoleteAssignmentDtos(IAssignmentBean bean, Collection<String> selectedCategoryCodes){
		var currentCategoryDtos = findCategoryDtos(bean);
		Predicate<V> isObsolete = t -> selectedCategoryCodes.stream()
				.noneMatch(t.getCategory().getCategoryCode()::equals);
		
		return currentCategoryDtos.stream()
				.filter(isObsolete)
				.collect(Collectors.toList());
		
	}

}
