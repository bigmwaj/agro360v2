package com.agro360.service.mapper.tiers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.tiers.ITiersCategoryDao;
import com.agro360.dto.tiers.CategoryDto;
import com.agro360.dto.tiers.TiersCategoryDto;
import com.agro360.dto.tiers.TiersCategoryPk;
import com.agro360.dto.tiers.TiersDto;
import com.agro360.service.bean.tiers.TiersCategoryHierarchieBean;
import com.agro360.service.mapper.common.AbstractMapper;

@Component
public class TiersCategoryHierarchieMapper extends AbstractMapper {

	private static final String ROOT_CATEGORY_NOT_FOUND = "Catégorie ROOT introuvable";
	
	@Autowired
	private ITiersCategoryDao tiersCategoryDao;

	public TiersCategoryHierarchieBean mapToBean(CategoryDto dto) {
		TiersCategoryHierarchieBean bean = new TiersCategoryHierarchieBean();
		bean.getCategoryCode().setValue(dto.getCategoryCode());
		bean.getDescription().setValue(dto.getDescription());

		return bean;
	}
	
	public TiersCategoryDto mapToDto(TiersDto tiers, TiersCategoryHierarchieBean bean) {
		TiersCategoryDto dto = new TiersCategoryDto();
		dto.setTiersCode(tiers.getTiersCode());
		dto.setCategory(new CategoryDto());
		dto.getCategory().setCategoryCode(bean.getCategoryCode().getValue());
		if( tiersCategoryDao.existsById(new TiersCategoryPk(dto)) ) {
			dto = tiersCategoryDao.getById(new TiersCategoryPk(dto));
		}
		
		return dto;
	}

	public TiersCategoryHierarchieBean mapToBean(CategoryDto category, Map<CategoryDto, Set<CategoryDto>> hierarchie) {
		TiersCategoryHierarchieBean bean = mapToBean(category);
		Set<CategoryDto> children = hierarchie.get(category);
		bean.getSelected().setValue(children.isEmpty());
		for (CategoryDto child : children) {
			TiersCategoryHierarchieBean childBean = mapToBean(child, hierarchie);
			bean.getChildren().add(childBean);
		}
		return bean;
	}
	
	private void reduceToSelectedTiersCategories(List<TiersCategoryDto> selectedTiersCategories, TiersDto tiers, TiersCategoryHierarchieBean bean) {
		if( Boolean.TRUE.equals(bean.getSelected().getValue()) ) {
			selectedTiersCategories.add(mapToDto(tiers, bean));
		}
		for (TiersCategoryHierarchieBean childBean : bean.getChildren()) {
			reduceToSelectedTiersCategories(selectedTiersCategories, tiers, childBean);
		}
	}

	public List<TiersCategoryDto> reduceToSelectedTiersCategories(TiersDto tiers, TiersCategoryHierarchieBean hierarchie) {
		List<TiersCategoryDto> selectedTiersCategories = new ArrayList<>();
		for (TiersCategoryHierarchieBean childBean : hierarchie.getChildren()) {
			reduceToSelectedTiersCategories(selectedTiersCategories, tiers, childBean);
		}
		return selectedTiersCategories;
	}

	private CategoryDto getRootCategory(Collection<CategoryDto> categories) {
		return categories.stream().filter(e -> "ROOT".equals(e.getCategoryCode())).findFirst()
				.orElseThrow(() -> new RuntimeException(ROOT_CATEGORY_NOT_FOUND));
	}

	public TiersCategoryHierarchieBean mapToBean(List<TiersCategoryDto> tiersCategories) {
		Map<CategoryDto, Set<CategoryDto>> hierarchie = mapToParentChildCategories(tiersCategories);
		CategoryDto root = getRootCategory(hierarchie.keySet());
		return mapToBean(root, hierarchie);
	}
	

	private void mapToParentChildCategories(Map<CategoryDto, Set<CategoryDto>> parentChildMap, CategoryDto category) {
		CategoryDto parent = category.getParent();
		if (parent != null) {
			if (!parentChildMap.containsKey(parent)) {
				parentChildMap.put(parent, new HashSet<>());
			}
			parentChildMap.get(parent).add(category);
			mapToParentChildCategories(parentChildMap, parent);
		}
	}

	private Map<CategoryDto, Set<CategoryDto>> mapToParentChildCategories(List<TiersCategoryDto> tiersCategories) {
		Map<CategoryDto, Set<CategoryDto>> parentChildMap = new HashMap<>();
		for (TiersCategoryDto dto : tiersCategories) {
			mapToParentChildCategories(parentChildMap, dto.getCategory());
		}
		return parentChildMap;
	}
}
