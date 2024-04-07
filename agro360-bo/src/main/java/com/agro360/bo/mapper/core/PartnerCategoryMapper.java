package com.agro360.bo.mapper.core;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.core.PartnerCategoryBean;
import com.agro360.bo.mapper.common.AbstractMapper;
import com.agro360.dto.core.CategoryDto;
import com.agro360.dto.core.PartnerCategoryDto;
import com.agro360.dto.core.PartnerDto;

@Component
public class PartnerCategoryMapper extends AbstractMapper {

	public static final String ROOT_CATEGORY_ID = "ROOT";

	public static final String ROOT_CATEGORY_NOT_FOUND_MSG = "Catégorie ROOT introuvable!";

	public static final Supplier<RuntimeException> ROOT_CATEGORY_NOT_FOUND_EXP = () -> new RuntimeException(ROOT_CATEGORY_NOT_FOUND_MSG);

	public PartnerCategoryBean map(PartnerCategoryDto dto) {
		var bean = new PartnerCategoryBean();
		bean.getCategoryCode().setValue(dto.getCategory().getCategoryCode());
		bean.getDescription().setValue(dto.getCategory().getDescription());

		return bean;
	}

	public PartnerCategoryBean map(CategoryDto dto) {
		var bean = new PartnerCategoryBean();
		bean.getCategoryCode().setValue(dto.getCategoryCode());
		bean.getDescription().setValue(dto.getDescription());

		return bean;
	}


//	private PartnerCategoryBean addToBeansHierarchie(CategoryDto category) {
//		return addToBeansHierarchie(category, Map.of(category, Collections.emptySet()));
//	}

//	private PartnerCategoryBean addToBeansHierarchie(CategoryDto category, Map<CategoryDto, Set<CategoryDto>> hierarchie) {
//		PartnerCategoryBean bean = map(new PartnerCategoryDto(category));
//		Set<CategoryDto> children = hierarchie.get(category);
//		bean.getSelected().setValue(children.isEmpty() && !ROOT_CATEGORY_ID.equals(bean.getCategoryCode().getValue()));
//		
//		for (CategoryDto child : children) {
//			PartnerCategoryBean childBean = addToBeansHierarchie(child, hierarchie);
//			bean.getChildren().add(childBean);
//		}
//		return bean;
//	}

	private void reduceToSelectedPartnerCategories(List<PartnerCategoryDto> selectedPartnerCategories, PartnerBean partnerBean,
			PartnerCategoryBean bean) {
//		if (Boolean.TRUE.equals(bean.getSelected().getValue())) {
//			selectedPartnerCategories.add(mapToDto(partnerBean, bean));
//		}
//		for (PartnerCategoryBean childBean : bean.getChildren()) {
//			reduceToSelectedPartnerCategories(selectedPartnerCategories, partnerBean, childBean);
//		}
	}

	public List<PartnerCategoryDto> reduceToSelectedPartnerCategories(PartnerBean partnerBean, PartnerCategoryBean hierarchie) {
		List<PartnerCategoryDto> selectedPartnerCategories = new ArrayList<>();
		for (PartnerCategoryBean childBean : hierarchie.getChildren()) {
			reduceToSelectedPartnerCategories(selectedPartnerCategories, partnerBean, childBean);
		}
		return selectedPartnerCategories;
	}

//	private CategoryDto getRootCategory(Collection<CategoryDto> categories) {
//		return categories.stream().filter(e -> ROOT_CATEGORY_ID.equals(e.getCategoryCode())).findFirst()
//				.orElseThrow(ROOT_CATEGORY_NOT_FOUND_EXP);
//	}

//	private List<PartnerCategoryDto> getPartnerCategories(PartnerDto partner) {
//		if (null == partner || null == partner.getPartnerCode() || partner.getPartnerCode().isBlank()) {
//			return Collections.emptyList();
//		}
//
//		var ex = Example.of(new PartnerCategoryDto());
//		ex.getProbe().setPartnerCode(partner.getPartnerCode());
//
//		return partnerCategoryDao.findAll(ex);
		
//		return null;
//	}

	public PartnerCategoryBean mapToPartnerCategoryHierarchieBean(PartnerDto partnerDto) {
//		var partnerCategories = getPartnerCategories(partnerDto);
//		if (partnerCategories.isEmpty()) {
//			return categoryDao.findById(ROOT_CATEGORY_ID).map(this::addToBeansHierarchie).orElseThrow(ROOT_CATEGORY_NOT_FOUND_EXP);
//		} else {
//			var hierarchie = mapToParentChildCategories(partnerCategories);
//			var root = getRootCategory(hierarchie.keySet());
//			return addToBeansHierarchie(root, hierarchie);
//		}
		
		return null;	
	}

//	private void mapToParentChildCategories(Map<CategoryDto, Set<CategoryDto>> parentChildMap, CategoryDto category) {
//		var parent = category.getParent();
//		if (parent != null) {
//			if (!parentChildMap.containsKey(parent)) {
//				parentChildMap.put(parent, new HashSet<>());
//			}
//			parentChildMap.get(parent).add(category);
//			mapToParentChildCategories(parentChildMap, parent);
//		}
//		if (!parentChildMap.containsKey(category)) {
//			parentChildMap.put(category, new HashSet<>());
//		}
//	}

//	private Map<CategoryDto, Set<CategoryDto>> mapToParentChildCategories(List<PartnerCategoryDto> partnerCategories) {
//		Map<CategoryDto, Set<CategoryDto>> parentChildMap = new HashMap<>();
//		for (PartnerCategoryDto dto : partnerCategories) {
//			mapToParentChildCategories(parentChildMap, dto.getCategory());
//		}
//		return parentChildMap;
//	}
}
