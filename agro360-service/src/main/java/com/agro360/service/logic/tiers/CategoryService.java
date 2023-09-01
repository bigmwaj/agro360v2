package com.agro360.service.logic.tiers;

import static com.agro360.service.mapper.tiers.CategoryMapper.OPTION_HIRARCHIE_DEEP_KEY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.tiers.ICategoryDao;
import com.agro360.dto.tiers.CategoryDto;
import com.agro360.service.bean.tiers.CategoryBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.tiers.CategoryMapper;
import com.agro360.service.message.Message;

@Service
public class CategoryService extends AbstractService<CategoryDto, String> {

	private static final String CREATE_SUCCESS = "La catégorie %s de parent %s créée avec succès";

	private static final String UPDATE_SUCCESS = "La catégorie %s de parent %s modifiée avec succès";

	private static final String DELETE_SUCCESS = "La catégorie %s de parent %s supprimée avec succès";

	@Autowired
	private ICategoryDao dao;

	@Autowired
	private CategoryMapper mapper;

	@Override
	protected IDao<CategoryDto, String> getDao() {
		return dao;
	}

	private void save(CategoryDto parent, CategoryBean bean, List<Message> messages) {
		var dto = mapper.mapToDto(bean);
		dto.setParent(parent);

		switch (bean.getAction()) {
		case CREATE:
			save(dto);
			messages.add(Message.success(String.format(CREATE_SUCCESS, dto.getCategoryCode(), parent.getCategoryCode())));
			break;

		case UPDATE:
			save(dto);
			messages.add(Message.success(String.format(UPDATE_SUCCESS, dto.getCategoryCode(), parent.getCategoryCode())));
			break;

		case DELETE:
			delete(dto);
			messages.add(Message.success(String.format(DELETE_SUCCESS, dto.getCategoryCode(), parent.getCategoryCode())));
			return;

		default:
			messages.add(Message.warn("Aucune action à effectuer"));
			return;
		}
		
		var children = bean.getChildren();
		if (children != null && !children.isEmpty()) {
			for (CategoryBean childBean : children) {
				save(dto, childBean, messages);
			}
		}		
	}
	
	public List<Message> save(CategoryBean rootBean) {
		var rootCategory = mapper.mapToDto(rootBean);
		List<Message> messages = new ArrayList<>();
		
		var children = rootBean.getChildren();
		if (children != null && !children.isEmpty()) {
			for (CategoryBean childBean : children) {
				save(rootCategory, childBean, messages);
			}
		}		
		return messages;
	}

	private Map<String, Object> mapDeep(Integer deep) {
		return Map.of(OPTION_HIRARCHIE_DEEP_KEY, deep);
	}

	public CategoryBean findRootCategory(Optional<Integer> deep) {
		var root = dao.getReferenceById("ROOT");
		var options = deep.map(this::mapDeep).orElse(Collections.emptyMap());
		return mapper.mapToBean(root, options);
	}

	public List<CategoryBean> findChildrenCategory(String parentCategoryCode) {
		var example = Example.of(new CategoryDto());
		example.getProbe().setParent(new CategoryDto());
		example.getProbe().getParent().setCategoryCode(parentCategoryCode);
		return dao.findAll(example).stream().map(mapper::mapToBean).toList();
	}

}
