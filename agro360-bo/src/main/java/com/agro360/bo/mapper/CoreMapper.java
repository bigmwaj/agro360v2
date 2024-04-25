package com.agro360.bo.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.core.PartnerCategoryBean;
import com.agro360.bo.bean.core.PartnerSearchBean;
import com.agro360.bo.bean.core.UserAccountBean;
import com.agro360.bo.utils.Constants;
import com.agro360.dto.core.CategoryDto;
import com.agro360.dto.core.PartnerCategoryDto;
import com.agro360.dto.core.PartnerDto;
import com.agro360.dto.core.UserAccountDto;
import com.agro360.vd.core.PartnerTypeEnumVd;
import com.agro360.vd.core.UserAccountStatusEnumVd;

public class CoreMapper {

	public static PartnerSearchBean buildPartnerSearchBean() {
		var bean = new PartnerSearchBean();
		return bean;
	}

	public static PartnerBean map(PartnerDto dto) {
		PartnerBean bean = new PartnerBean();
		bean.getPartnerCode().setValue(dto.getPartnerCode());

		bean.getAddress().setValue(dto.getAddress());
		bean.getCity().setValue(dto.getCity());
		bean.getCountry().setValue(dto.getCountry());
		bean.getEmail().setValue(dto.getEmail());
		bean.getPhone().setValue(dto.getPhone());
		bean.getFirstName().setValue(dto.getFirstName());
		bean.getLastName().setValue(dto.getLastName());

		bean.getStatus().setValue(dto.getStatus());

		bean.getName().setValue(dto.getName());

		bean.getType().setValue(dto.getType());

		bean.getTitle().setValue(dto.getTitle());

		if (PartnerTypeEnumVd.COMPANY.equals(dto.getType())) {
			bean.getPartnerName().setValue(dto.getName());
		} else {
			bean.getPartnerName().setValue(Constants.FULL_NAME_FN.apply(dto.getLastName(), dto.getFirstName()));
		}

		return bean;
	}

	public static UserAccountBean map(UserAccountDto dto) {
		UserAccountBean bean = new UserAccountBean();
		bean.getPartnerCode().setValue(dto.getPartnerCode());

		bean.getStatus().setValue(dto.getStatus());
		bean.getStatus().setValueOptions(UserAccountStatusEnumVd.getAsMap());

		bean.getPassword().setValue(dto.getPassword());

		return bean;
	}

	public static final String ROOT_CATEGORY_ID = "ROOT";

	public static final String ROOT_CATEGORY_NOT_FOUND_MSG = "Catégorie ROOT introuvable!";

	public static final Supplier<RuntimeException> ROOT_CATEGORY_NOT_FOUND_EXP = () -> new RuntimeException(ROOT_CATEGORY_NOT_FOUND_MSG);

	public static PartnerCategoryBean map(PartnerCategoryDto dto) {
		var bean = new PartnerCategoryBean();
		bean.getCategoryCode().setValue(dto.getCategory().getCategoryCode());
		bean.getDescription().setValue(dto.getCategory().getDescription());

		return bean;
	}

	public static PartnerCategoryBean map(CategoryDto dto) {
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

	private static void reduceToSelectedPartnerCategories(List<PartnerCategoryDto> selectedPartnerCategories, PartnerBean partnerBean,
			PartnerCategoryBean bean) {
//		if (Boolean.TRUE.equals(bean.getSelected().getValue())) {
//			selectedPartnerCategories.add(mapToDto(partnerBean, bean));
//		}
//		for (PartnerCategoryBean childBean : bean.getChildren()) {
//			reduceToSelectedPartnerCategories(selectedPartnerCategories, partnerBean, childBean);
//		}
	}

	public static List<PartnerCategoryDto> reduceToSelectedPartnerCategories(PartnerBean partnerBean, PartnerCategoryBean hierarchie) {
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

	public static PartnerCategoryBean mapToPartnerCategoryHierarchieBean(PartnerDto partnerDto) {
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
