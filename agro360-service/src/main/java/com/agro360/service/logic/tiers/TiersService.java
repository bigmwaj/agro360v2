package com.agro360.service.logic.tiers;

import static com.agro360.service.mapper.tiers.TiersMapper.OPTION_MAP_TIERS_CATEGORY_KEY;

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
import com.agro360.service.message.Message;
import com.agro360.vd.common.EditActionEnumVd;
import com.agro360.vd.tiers.TiersStatusEnumVd;

@Service
public class TiersService extends AbstractService<TiersDto, String> {

	private static final String CREATE_SUCCESS = "Tiers %s de type %s créé avec succès";

	private static final String UPDATE_SUCCESS = "Tiers %s de type %s modifié avec succès";

	private static final String DELETE_SUCCESS = "Tiers %s de type %s supprimé avec succès";

	private static final String CHANGE_STS_SUCCESS = "Statut du tiers %s de type %s modifié avec succès";

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
		var probe = new TiersDto();
		var matcher = ExampleMatcher.matchingAll();

		if (bean.getTiersCode() != null) {
			probe.setTiersCode(bean.getTiersCode().getValue());
			matcher = matcher.withMatcher("tiersCode", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		}

		if (bean.getTiersName() != null) {
//			probe.setName(bean.getTiersName().getValue());
			probe.setFirstName(bean.getTiersName().getValue());
//			probe.setLastName(bean.getTiersName().getValue());

//			matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
			matcher = matcher.withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
//			matcher = matcher.withMatcher("lastName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		}

		if (bean.getEmail() != null) {
			probe.setEmail(bean.getEmail().getValue());
			matcher = matcher.withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		}

		if (bean.getPhone() != null) {
			probe.setPhone(bean.getPhone().getValue());
			matcher = matcher.withMatcher("phone", ExampleMatcher.GenericPropertyMatchers.endsWith());
		}

		if (bean.getStatus() != null) {
			probe.setStatus((TiersStatusEnumVd) bean.getStatus().getValue());
		}

		if (bean.getTiersType() != null) {
			probe.setTiersType(bean.getTiersType().getValue());
		}
		var example = Example.of(probe, matcher);
		return dao.findAll(example).stream().map(mapper::mapToBean).collect(Collectors.toList());
	}

	public List<Message> save(TiersBean bean) {
		var dto = mapper.mapToDto(bean);
		List<Message> messages = new ArrayList<>();
		
		switch (bean.getAction()) {
		case CREATE:
		case UPDATE:
			save(dto);
			if( bean.getAction() == EditActionEnumVd.CREATE) {
				messages.add(Message.success(String.format(CREATE_SUCCESS, bean.getTiersName().getValue(), bean.getTiersType().getValue())));
			}else {
				messages.add(Message.success(String.format(UPDATE_SUCCESS, bean.getTiersName().getValue(), bean.getTiersType().getValue())));
			}
			messages.addAll(tiersCategoryService.synchTiersCategories(bean, bean.getCategoriesHierarchie()));
			break;

		case CHANGE_STATUS:
			save(dto);
			messages.add(Message.success(String.format(CHANGE_STS_SUCCESS, bean.getTiersName().getValue(), bean.getTiersType().getValue())));
			break;

		case DELETE:
			messages.addAll(tiersCategoryService.synchTiersCategories(bean, bean.getCategoriesHierarchie()));
			delete(dto);
			messages.add(Message.success(String.format(DELETE_SUCCESS, bean.getTiersName().getValue(), bean.getTiersType().getValue())));
			break;
		default:
			return Collections.singletonList(Message.warn("Aucune action à effectuer"));
		}

		return messages;
	}

	public TiersBean findById(String id) {
		return dao.findById(id).map(e -> mapper.mapToBean(e, Map.of(OPTION_MAP_TIERS_CATEGORY_KEY, true)))
				.orElseThrow();
	}

}
