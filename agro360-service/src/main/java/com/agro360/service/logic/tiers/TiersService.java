package com.agro360.service.logic.tiers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.tiers.ITiersDao;
import com.agro360.dto.tiers.TiersDto;
import com.agro360.service.bean.tiers.TiersBean;
import com.agro360.service.bean.tiers.TiersSearchBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.tiers.TiersMapper;
import com.agro360.service.utils.Message;
import com.agro360.vd.tiers.TiersStatusEnumVd;

@Service
public class TiersService extends AbstractService<TiersDto, String> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	private static final String CHANGE_STS_SUCCESS = "Statut enregistrement modifié avec succès!";

	@Autowired
	private ITiersDao dao;

	@Autowired
	private TiersMapper mapper;

	@Autowired
	private TiersCategoryService tiersCategoryService;

	@Override
	protected IDao<TiersDto, String> getDao() {
		return dao;
	}

	public List<TiersBean> search(TiersSearchBean bean) {
		var example = Example.of(new TiersDto());

		if (bean.getTiersCode() != null) {
			example.getProbe().setTiersCode(bean.getTiersCode().getValue());
		}

		if (bean.getTiersName() != null) {
			example.getProbe().setName(bean.getTiersName().getValue());
			example.getProbe().setFirstName(bean.getTiersName().getValue());
			example.getProbe().setLastName(bean.getTiersName().getValue());
			example.getMatcher().withIgnoreCase().withMatcher("name",
					ExampleMatcher.GenericPropertyMatchers.contains());
			example.getMatcher().withIgnoreCase().withMatcher("firstName",
					ExampleMatcher.GenericPropertyMatchers.contains());
			example.getMatcher().withIgnoreCase().withMatcher("lastName",
					ExampleMatcher.GenericPropertyMatchers.contains());
		}

		if (bean.getEmail() != null) {
			example.getProbe().setEmail(bean.getEmail().getValue());
			example.getMatcher().withIgnoreCase().withMatcher("email",
					ExampleMatcher.GenericPropertyMatchers.contains());
		}

		if (bean.getPhone() != null) {
			example.getProbe().setPhone(bean.getPhone().getValue());
			example.getMatcher().withMatcher("phone", ExampleMatcher.GenericPropertyMatchers.endsWith());
		}

		if (bean.getStatus() != null) {
			example.getProbe().setStatus((TiersStatusEnumVd) bean.getStatus().getValue());
		}

		if (bean.getTiersType() != null) {
			example.getProbe().setTiersType(bean.getTiersType().getValue());
		}
		return dao.findAll(example).stream().map(mapper::mapToBean).collect(Collectors.toList());
	}

	public List<Message> save(TiersBean bean) {
		var dto = mapper.mapToDto(bean);
		List<Message> messages = new ArrayList<>();

		switch (bean.getAction()) {
		case CREATE:
			save(dto);
			messages.add(Message.success(CREATE_SUCCESS));
			messages.addAll(tiersCategoryService.synchTiersCategories(bean, bean.getCategoriesHierarchie()));
			break;

		case UPDATE:
			save(dto);
			messages.add(Message.success(UPDATE_SUCCESS));
			messages.addAll(tiersCategoryService.synchTiersCategories(bean, bean.getCategoriesHierarchie()));
			break;

		case CHANGE_STATUS:
			save(dto);
			messages.add(Message.success(CHANGE_STS_SUCCESS));
			break;

		case DELETE:
			messages.addAll(tiersCategoryService.synchTiersCategories(bean, bean.getCategoriesHierarchie()));
			delete(dto);
			messages.add(Message.success(DELETE_SUCCESS));
			break;
		default:
			return Collections.singletonList(Message.warn("Aucune action à effectuer"));
		}

		return messages;
	}

	public TiersBean findTiersByCode(String id) {
		return dao.findById(id).map(e -> mapper.mapToBean(e, Map.of(TiersMapper.OPTION_MAP_TIERS_CATEGORY_KEY, true)))
				.orElseThrow();
	}

}
