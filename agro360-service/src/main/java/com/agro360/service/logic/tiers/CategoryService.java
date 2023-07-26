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
import com.agro360.service.utils.Message;
import com.agro360.vd.common.EditActionEnumVd;

@Service
public class CategoryService extends AbstractService<CategoryDto, String> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	@Autowired
	private ICategoryDao dao;

	@Autowired
	private CategoryMapper mapper;

	@Override
	protected IDao<CategoryDto, String> getDao() {
		return dao;
	}

	protected List<Message> _save(CategoryBean bean) {
		var dto = mapper.mapToDto(bean);
		List<Message> messages = new ArrayList<>();

		switch (bean.getAction()) {
		case CREATE:
			dao.save(dto);
			messages.add(Message.success(CREATE_SUCCESS));
			break;

		case UPDATE:
			dao.save(dto);
			messages.add(Message.success(UPDATE_SUCCESS));
			break;

		case DELETE:
			dao.delete(dto);
			messages.add(Message.success(DELETE_SUCCESS));
			break;

		default:
			return Collections.singletonList(Message.warn("Aucune action à effectuer"));
		}
		return messages;
	}

	public List<Message> save(CategoryBean bean) {
		var messages = _save(bean);
		var children = bean.getChildren();
		if (children != null && !children.isEmpty()) {
			for (CategoryBean b : bean.getChildren()) {
				messages.addAll(save(b));
				if (b.getAction().equals(EditActionEnumVd.DELETE)) {
					continue;
				}
			}
		}
		return messages;
	}

	public CategoryBean findRootCategory(Optional<Integer> deep) {
		var root = dao.getById("ROOT");
		var options = deep.map(Object.class::cast).map(e -> Map.of(OPTION_HIRARCHIE_DEEP_KEY, e)).orElse(Collections.emptyMap());
		return mapper.mapToBean(root, options);
	}

	public List<CategoryBean> findChildrenCategory(String parentCategoryCode) {
		var example = Example.of(new CategoryDto());
		example.getProbe().setParent(new CategoryDto());
		example.getProbe().getParent().setCategoryCode(parentCategoryCode);
		return dao.findAll(example).stream().map(mapper::mapToBean).toList();
	}

}
