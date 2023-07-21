package com.agro360.service.logic.tiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.tiers.ICategoryDao;
import com.agro360.dao.tiers.ITiersCategoryDao;
import com.agro360.dto.tiers.CategoryDto;
import com.agro360.dto.tiers.TiersCategoryDto;
import com.agro360.dto.tiers.TiersCategoryPk;
import com.agro360.dto.tiers.TiersDto;
import com.agro360.service.logic.common.AbstractService;

@Service
public class TiersCategoryService extends AbstractService<TiersCategoryDto, TiersCategoryPk, ITiersCategoryDao>{

	private static final String ROOT_CATEGORY_NOT_FOUND_MSG = "La catégorie n'a pas été trouvée!";
	
	@Autowired
	private ITiersCategoryDao tiersCategoryDao;

	@Autowired
	private ICategoryDao categoryDao;
	
	@Override
	public ITiersCategoryDao getDao() {
		return tiersCategoryDao;
	}
	
	public List<TiersCategoryDto> findAll() {
		return tiersCategoryDao.findAll();
	}

	public Optional<TiersCategoryDto> findById(TiersCategoryPk id) {
		return tiersCategoryDao.findById(id);
	}

	private void addAsChild(Map<CategoryDto, List<CategoryDto>> children, CategoryDto category) {
		if (category.getParent() != null) {
			if (!children.containsKey(category.getParent())) {
				children.put(category.getParent(), new ArrayList<>());
				children.get(category.getParent()).add(category);
			} else {
				addAsChild(children, category.getParent());
			}
		}
	}

	public Map<CategoryDto, List<CategoryDto>> findTiersCategoriesHierarchie(TiersDto tiers) {
		CategoryDto root = categoryDao.findById("ROOT")
				.orElseThrow(()->new RuntimeException(ROOT_CATEGORY_NOT_FOUND_MSG));
		
		Example<TiersCategoryDto> example = Example.of(new TiersCategoryDto());
		example.getProbe().setTiersCode(tiers.getTiersCode());
		
		List<TiersCategoryDto> tiersCategories = tiersCategoryDao.findAll(example);
		
		Map<CategoryDto, List<CategoryDto>> children = new HashMap<>();
		children.put(root, new ArrayList<>());

		for (TiersCategoryDto tiersCategory : tiersCategories) {
			CategoryDto category = tiersCategory.getCategory();
			addAsChild(children, category);
		}

		return children;
	}

	public void delete(TiersCategoryDto tiersCategory) {
		tiersCategoryDao.delete(tiersCategory);
	}

}
