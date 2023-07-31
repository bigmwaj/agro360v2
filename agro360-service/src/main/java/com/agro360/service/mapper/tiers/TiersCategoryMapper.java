package com.agro360.service.mapper.tiers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.agro360.dao.tiers.ICategoryDao;
import com.agro360.dao.tiers.ITiersCategoryDao;
import com.agro360.dto.tiers.CategoryDto;
import com.agro360.dto.tiers.TiersCategoryDto;
import com.agro360.dto.tiers.TiersCategoryPk;
import com.agro360.dto.tiers.TiersDto;
import com.agro360.service.bean.tiers.TiersBean;
import com.agro360.service.bean.tiers.TiersCategoryBean;
import com.agro360.service.mapper.common.AbstractMapper;

@Component
public class TiersCategoryMapper extends AbstractMapper {

	private static final String ROOT_CATEGORY_ID = "ROOT";

	private static final String ROOT_CATEGORY_NOT_FOUND_MSG = "Catégorie ROOT introuvable!";

	private static final Supplier<RuntimeException> ROOT_CATEGORY_NOT_FOUND_EXP = () -> new RuntimeException(ROOT_CATEGORY_NOT_FOUND_MSG);

	@Autowired
	private ITiersCategoryDao tiersCategoryDao;

	@Autowired
	private ICategoryDao categoryDao;

	public TiersCategoryBean mapToBean(TiersCategoryDto dto) {
		var bean = new TiersCategoryBean();
		bean.getCategoryCode().setValue(dto.getCategory().getCategoryCode());
		bean.getDescription().setValue(dto.getCategory().getDescription());

		return bean;
	}

	public TiersCategoryDto mapToDto(TiersBean tiersBean, TiersCategoryBean bean) {
		TiersCategoryDto dto = new TiersCategoryDto();
		dto.setTiersCode(tiersBean.getTiersCode().getValue());
		dto.setCategory(new CategoryDto());
		dto.getCategory().setCategoryCode(bean.getCategoryCode().getValue());
		if (tiersCategoryDao.existsById(new TiersCategoryPk(dto))) {
			dto = tiersCategoryDao.getById(new TiersCategoryPk(dto));
		}

		return dto;
	}

	private TiersCategoryBean addToBeansHierarchie(CategoryDto category) {
		return addToBeansHierarchie(category, Map.of(category, Collections.emptySet()));
	}

	private TiersCategoryBean addToBeansHierarchie(CategoryDto category, Map<CategoryDto, Set<CategoryDto>> hierarchie) {
		TiersCategoryBean bean = mapToBean(new TiersCategoryDto(category));
		Set<CategoryDto> children = hierarchie.get(category);
		bean.getSelected().setValue(children.isEmpty() && !ROOT_CATEGORY_ID.equals(bean.getCategoryCode().getValue()));
		
		for (CategoryDto child : children) {
			TiersCategoryBean childBean = addToBeansHierarchie(child, hierarchie);
			bean.getChildren().add(childBean);
		}
		return bean;
	}

	private void reduceToSelectedTiersCategories(List<TiersCategoryDto> selectedTiersCategories, TiersBean tiersBean,
			TiersCategoryBean bean) {
		if (Boolean.TRUE.equals(bean.getSelected().getValue())) {
			selectedTiersCategories.add(mapToDto(tiersBean, bean));
		}
		for (TiersCategoryBean childBean : bean.getChildren()) {
			reduceToSelectedTiersCategories(selectedTiersCategories, tiersBean, childBean);
		}
	}

	public List<TiersCategoryDto> reduceToSelectedTiersCategories(TiersBean tiersBean, TiersCategoryBean hierarchie) {
		List<TiersCategoryDto> selectedTiersCategories = new ArrayList<>();
		for (TiersCategoryBean childBean : hierarchie.getChildren()) {
			reduceToSelectedTiersCategories(selectedTiersCategories, tiersBean, childBean);
		}
		return selectedTiersCategories;
	}

	private CategoryDto getRootCategory(Collection<CategoryDto> categories) {
		return categories.stream().filter(e -> ROOT_CATEGORY_ID.equals(e.getCategoryCode())).findFirst()
				.orElseThrow(ROOT_CATEGORY_NOT_FOUND_EXP);
	}

	private List<TiersCategoryDto> getTiersCategories(TiersDto tiers) {
		if (null == tiers || null == tiers.getTiersCode() || tiers.getTiersCode().isBlank()) {
			return Collections.emptyList();
		}

		var ex = Example.of(new TiersCategoryDto());
		ex.getProbe().setTiersCode(tiers.getTiersCode());

		return tiersCategoryDao.findAll(ex);
	}

	public TiersCategoryBean mapToTiersCategoryHierarchieBean(TiersDto tiersDto) {
		var tiersCategories = getTiersCategories(tiersDto);
		if (tiersCategories.isEmpty()) {
			return categoryDao.findById(ROOT_CATEGORY_ID).map(this::addToBeansHierarchie).orElseThrow(ROOT_CATEGORY_NOT_FOUND_EXP);
		} else {
			var hierarchie = mapToParentChildCategories(tiersCategories);
			var root = getRootCategory(hierarchie.keySet());
			return addToBeansHierarchie(root, hierarchie);
		}
	}

	private void mapToParentChildCategories(Map<CategoryDto, Set<CategoryDto>> parentChildMap, CategoryDto category) {
		var parent = category.getParent();
		if (parent != null) {
			if (!parentChildMap.containsKey(parent)) {
				parentChildMap.put(parent, new HashSet<>());
			}
			parentChildMap.get(parent).add(category);
			mapToParentChildCategories(parentChildMap, parent);
		}
		if (!parentChildMap.containsKey(category)) {
			parentChildMap.put(category, new HashSet<>());
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
