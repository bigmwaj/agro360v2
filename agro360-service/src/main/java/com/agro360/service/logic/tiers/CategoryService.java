package com.agro360.service.logic.tiers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.tiers.ICategoryDao;
import com.agro360.dto.tiers.CategoryDto;
import com.agro360.service.bean.tiers.CategoryHierarchieBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.tiers.CategoryHierarchieMapper;
import com.agro360.service.utils.Message;
import com.agro360.vd.common.EditActionEnumVd;

@Service
public class CategoryService extends AbstractService<CategoryDto, String, ICategoryDao> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	@Autowired
	private ICategoryDao dao;

	@Autowired
	private CategoryHierarchieMapper binding;

	@Override
	public ICategoryDao getDao() {
		return dao;
	}

	private List<Message> _save(CategoryHierarchieBean bean) {
		CategoryDto dto = binding.mapToDto(bean);
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
			// Nothing to do!
		}
		return messages;
	}

	public List<Message> save(CategoryHierarchieBean bean) {
		List<Message> messages = _save(bean);
		List<CategoryHierarchieBean> children = bean.getChildren();
		if (children != null && !children.isEmpty()) {
			for (CategoryHierarchieBean b : bean.getChildren()) {
				messages.addAll(save(b));
				if (b.getAction().equals(EditActionEnumVd.DELETE)) {
					continue;
				}
			}
		}
		return messages;
	}

	public CategoryHierarchieBean loadRootCategory(Optional<Integer> deep) {
		CategoryDto root = dao.getById("ROOT");
		return binding.mapToBean(root);
	}

	public List<CategoryHierarchieBean> loadChildrenCategory(String categoryCode) {
		Example<CategoryDto> example = Example.of(new CategoryDto());
		example.getProbe().setParent(new CategoryDto());
		example.getProbe().getParent().setCategoryCode(categoryCode);
		return dao.findAll(example).stream().map(binding::mapToBean).toList();
	}

}
