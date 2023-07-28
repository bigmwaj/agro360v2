package com.agro360.service.logic.tiers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.tiers.ICategoryDao;
import com.agro360.dao.tiers.ITiersCategoryDao;
import com.agro360.dto.tiers.CategoryDto;
import com.agro360.dto.tiers.TiersCategoryDto;
import com.agro360.dto.tiers.TiersCategoryPk;
import com.agro360.dto.tiers.TiersDto;
import com.agro360.service.bean.tiers.TiersBean;
import com.agro360.service.bean.tiers.TiersCategoryBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.tiers.TiersCategoryMapper;
import com.agro360.service.utils.Message;

@Service
class TiersCategoryService extends AbstractService<TiersCategoryDto, TiersCategoryPk>{

	private static final String ROOT_CATEGORY_NOT_FOUND_MSG = "La catégorie n'a pas été trouvée!";
	
	@Autowired
	private ITiersCategoryDao dao;

	@Autowired
	private TiersCategoryMapper mapper;

	@Autowired
	private ICategoryDao categoryDao;
	
	@Override
	protected IDao<TiersCategoryDto, TiersCategoryPk> getDao() {
		return dao;
	}
	
	public List<TiersCategoryDto> findAll() {
		return dao.findAll();
	}

	public Optional<TiersCategoryDto> findById(TiersCategoryPk id) {
		return dao.findById(id);
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
		
		var example = Example.of(new TiersCategoryDto());
		example.getProbe().setTiersCode(tiers.getTiersCode());
		
		var tiersCategories = dao.findAll(example);
		
		Map<CategoryDto, List<CategoryDto>> children = new HashMap<>();
		children.put(root, new ArrayList<>());

		for (TiersCategoryDto tiersCategory : tiersCategories) {
			CategoryDto category = tiersCategory.getCategory();
			addAsChild(children, category);
		}

		return children;
	}
	
	public List<TiersCategoryBean> findChildrenCategoryAndMapToTiersCategory(String parentCategoryCode) {
		var ex = Example.of(new CategoryDto());
		ex.getProbe().setParent(new CategoryDto());
		ex.getProbe().getParent().setCategoryCode(parentCategoryCode);	
		return categoryDao.findAll(ex).stream().map(TiersCategoryDto::new).map(mapper::mapToBean).toList();
	}
	
	protected List<TiersCategoryDto> findTiersCategories(TiersBean tiersBean){
		var example = Example.of(new TiersCategoryDto());
		example.getProbe().setTiersCode(tiersBean.getTiersCode().getValue());
		return dao.findAll(example);
	}
	
	private List<Message> delete(TiersBean tiersBean, List<TiersCategoryDto> tiersCategories){
		List<Message> messages = new ArrayList<>();
		messages.add(Message.info(String.format("Suppression des catégories assignées au tiers %s ...", 
				tiersBean.getName().getValue())));
		
		if( tiersCategories.isEmpty()) {
			messages.add(Message.info(String.format("Aucune catégorie assignée au tiers %s!", 
					tiersBean.getTiersName().getValue())));
		}else {
			dao.deleteAll(tiersCategories);
			messages.add(Message.success(String.format("%d catégories assignée au tiers %s ont été supprimées!", 
					tiersCategories.size(), 
					tiersBean.getName().getValue())));
		}
		
		return messages;
	}
	
	public List<Message> synchTiersCategories(TiersBean tiersBean, TiersCategoryBean bean) {
		List<Message> messages = new ArrayList<>();
		
		var tiersCategories = findTiersCategories(tiersBean);
		switch (tiersBean.getAction()) {
		case DELETE:
			messages.addAll(delete(tiersBean, tiersCategories));
			break;
		case CREATE:
		case UPDATE:
			var selectedTiersCategories = mapper.reduceToSelectedTiersCategories(tiersBean, bean);
		
			for (TiersCategoryDto tiersCategory : selectedTiersCategories) {
				if( !tiersCategories.contains(tiersCategory) ) {
					save(tiersCategory);
				}
			}
	
			for (TiersCategoryDto tiersCategory : tiersCategories) {
				if( !selectedTiersCategories.contains(tiersCategory) ) {
					delete(tiersCategory);
				}
			}	
			break;
		default:
			break;
		}
		
		return messages;
	}

}
