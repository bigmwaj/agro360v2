//package com.agro360.operation.logic.core;
//
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.List;
//import java.util.function.Predicate;
//import java.util.stream.Collectors;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Example;
//import org.springframework.data.domain.ExampleMatcher;
//import org.springframework.stereotype.Service;
//
//import com.agro360.bo.bean.core.PartnerBean;
//import com.agro360.bo.bean.core.PartnerCategoryBean;
//import com.agro360.bo.mapper.CoreMapper;
//import com.agro360.dao.common.IDao;
//import com.agro360.dao.core.ICategoryDao;
//import com.agro360.dao.core.IPartnerCategoryDao;
//import com.agro360.dto.core.CategoryDto;
//import com.agro360.dto.core.PartnerCategoryDto;
//import com.agro360.dto.core.PartnerCategoryPk;
//import com.agro360.operation.context.ClientContext;
//import com.agro360.operation.logic.common.AbstractOperation;
//
//@Service
//public class PartnerCategoryOperationBak extends AbstractOperation<PartnerCategoryDto, PartnerCategoryPk>{
//
//	@Autowired
//	private IPartnerCategoryDao dao;
//	
//	@Autowired
//	private ICategoryDao categoryDao;
//	
//	@Override
//	protected IDao<PartnerCategoryDto, PartnerCategoryPk> getDao() {
//		return dao;
//	}
//	
//	private List<PartnerCategoryDto> findPartnerCategoryDtos(String partnerCode){
//		var probe = new PartnerCategoryDto();
//		var matcher = ExampleMatcher.matchingAll();
//		probe.setPartnerCode(partnerCode);
//		matcher = matcher.withMatcher("partnerCode", ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase());
//		var example = Example.of(probe, matcher);
//		return dao.findAll(example).stream().collect(Collectors.toList());
//	}
//
//	public PartnerCategoryBean findRootCategoryHierarchy(ClientContext ctx, int deep) {
//		var root = categoryDao.getReferenceById("ROOT");
//		var bean = CoreMapper.map(new PartnerCategoryDto(root));
//		
//		addChildCategories(ctx, bean, deep);
//		
//		return bean;
//	}
//	
//	private void addChildCategories(ClientContext ctx, PartnerCategoryBean bean, int deep){
//		if( deep > 0 ) {
//			
//			var children = findChildCategories(ctx, bean.getCategoryCode().getValue());
//			bean.getChildren().addAll(children);
//			
//			for (PartnerCategoryBean child : children) {
//				addChildCategories(ctx, child, deep - 1);
//			}			
//		}
//	}
//	
//	public List<PartnerCategoryBean> findChildCategories(ClientContext ctx, String categoryCode){
//		var probe = new CategoryDto();
//		probe.setParent(new CategoryDto());
//		probe.getParent().setCategoryCode(categoryCode);
//
//		var matcher = ExampleMatcher.matchingAll();
//		matcher = matcher.withMatcher("parent.categoryCode", ExampleMatcher.GenericPropertyMatchers.exact().ignoreCase());
//		var example = Example.of(probe, matcher);
//		
//		return categoryDao.findAll(example).stream().map(CoreMapper::map).collect(Collectors.toList());
//	}
//	
//	public PartnerCategoryBean findCategoryHierarchyFromLeaves(ClientContext ctx, String partnerCode) {
//		
//		var partnerCategoryDtos = findPartnerCategoryDtos(partnerCode);
//		
//		if( partnerCategoryDtos.isEmpty() ) {
//			return findRootCategoryHierarchy(ctx, 5);
//		}
//		
//		var categoryMap = new HashMap<String, PartnerCategoryBean>();
//		
//		for (PartnerCategoryDto dto : partnerCategoryDtos) {
//			var category = dto.getCategory();
//			
//			while( category != null ){
//				var code = category.getCategoryCode();
//				if( !categoryMap.containsKey(code) ) {
//					var categoryBean = CoreMapper.map(new PartnerCategoryDto(category));
//					categoryBean.getSelected().setValue(true);
//					categoryMap.put(code, categoryBean);
//				}
//				
//				var categoryBean = categoryMap.get(code);
//				
//				var parent = category.getParent();
//				if( parent != null ) {
//					var parentCode = parent.getCategoryCode();
//					if( !categoryMap.containsKey(parentCode) ) {
//						var parentBean = CoreMapper.map(new PartnerCategoryDto(parent));
//						categoryMap.put(parentCode, parentBean);
//					}
//					
//					var parentBean = categoryMap.get(parentCode);
//					parentBean.getChildren().add(categoryBean);
//				}
//				
//				category = parent;
//			}
//		}
//		
//		return categoryMap.get("ROOT");
//	}
//	
//	private List<PartnerCategoryDto> findNewsPartnerCategoryDtos(PartnerBean bean, Collection<String> categoryCodes){
//		var currentPartnerCategoryDtos = findPartnerCategories(bean);
//		
//		Predicate<String> notExists = categoryCode -> currentPartnerCategoryDtos.stream()
//				.map(PartnerCategoryDto::getCategory)
//				.map(CategoryDto::getCategoryCode)
//				.noneMatch(categoryCode::equals);
//		
//		return categoryCodes.stream()
//				.filter(notExists)
//				.map(categoryDao::getReferenceById)
//				.map(PartnerCategoryDto::new)
//				.map(e -> {e.setPartnerCode(bean.getPartnerCode().getValue()); return e;})
//				.collect(Collectors.toList());
//	}
//	
//	private List<PartnerCategoryDto> findPartnerCategories(PartnerBean bean){
//		return findPartnerCategoryDtos(bean.getPartnerCode().getValue());
//	}
//	
//	private List<PartnerCategoryDto> findObsoletesPartnerCategoryDtos(PartnerBean bean, Collection<String> categoryCodes){
//
//		var currentPartnerCategoryDtos = findPartnerCategories(bean);
//		Predicate<PartnerCategoryDto> isObsolete;		
//		isObsolete = t -> categoryCodes.stream().anyMatch(t.getCategory().getCategoryCode()::equals);
//		
//		return currentPartnerCategoryDtos.stream()
//				.filter(isObsolete)
//				.collect(Collectors.toList());
//		
//	}
//	
//	public void syncPartnerCategories(ClientContext ctx, PartnerBean bean, Collection<String> categoryCodes) {
//		var newsPartnerCategoryDtos = findNewsPartnerCategoryDtos(bean, categoryCodes);
//		var obsoletesPartnerCategoryDtos = findObsoletesPartnerCategoryDtos(bean, categoryCodes);
//		
//		if( ! newsPartnerCategoryDtos.isEmpty() ) {
//			for (var partnerCategoryDto : newsPartnerCategoryDtos) {
//				save(ctx, partnerCategoryDto);
//			}
//		}
//		
//		if( ! obsoletesPartnerCategoryDtos.isEmpty() ) {
//			dao.deleteAll(obsoletesPartnerCategoryDtos);
//		}
//			
//	}
//
//	public void deleteAllPartnerCategories(ClientContext ctx, PartnerBean bean) {
//		var dtos = findPartnerCategoryDtos(bean.getPartnerCode().getValue());
//		dao.deleteAll(dtos);
//	}
//
//}
