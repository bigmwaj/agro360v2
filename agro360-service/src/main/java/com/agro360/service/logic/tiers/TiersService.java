package com.agro360.service.logic.tiers;

import static com.agro360.service.mapper.tiers.TiersMapper.OPTION_MAP_TIERS_CATEGORY_KEY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
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
import com.agro360.service.utils.InitBeanResult;
import com.agro360.service.utils.ValidateInput;
import com.agro360.vd.common.EditActionEnumVd;
import com.agro360.vd.tiers.TiersStatusEnumVd;

@Service
public class TiersService extends AbstractService<TiersDto, String> {

	private static final String CREATE_SUCCESS = "Tiers %s de type %s créé avec succès";

	private static final String UPDATE_SUCCESS = "Tiers %s de type %s modifié avec succès";

	private static final String DELETE_SUCCESS = "Tiers %s de type %s supprimé avec succès";

	private static final String CHANGE_STS_SUCCESS = "Statut du tiers %s de type %s modifié avec succès";
	
	private static final String DTO_NOT_FOUND = "Aucun de code %s n'a été trouvé";

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
	
	@Override
	protected String getRulePath() {
		return "tiers/tiers";
	}

	@InitBeanResult(namespace = "tiers.init.edit")
	public TiersBean initEditFormBean(String tiersCode) {
		var dto = dao.findById(tiersCode).orElseThrow(dtoNotFoundEx(tiersCode));
		var bean = mapper.mapToBean(dto, Map.of(OPTION_MAP_TIERS_CATEGORY_KEY, true));
		return applyInitRules(bean, "init-edit-form");
	}

	@InitBeanResult(namespace = "tiers.init.delete")
	public TiersBean initDeleteFormBean(String tiersCode) {
		var bean = dao.findById(tiersCode).map(mapper::mapToBean)
				.orElseThrow(dtoNotFoundEx(tiersCode));
		bean.setAction(EditActionEnumVd.DELETE);
		return applyInitRules(bean, "init-delete-form");
	}

	@InitBeanResult(namespace = "tiers.init.status-change")
	public TiersBean initChangeStatusFormBean(String tiersCode) {
		var bean = dao.findById(tiersCode).map(mapper::mapToBean)
				.orElseThrow(dtoNotFoundEx(tiersCode));
		bean.setAction(EditActionEnumVd.CHANGE_STATUS);
		return applyInitRules(bean, "init-change-status-form");
	}

	@InitBeanResult(namespace = "tiers.init.edit")
	public TiersBean initCreateFormBean(Optional<String> copyFrom) {
		var dto = copyFrom.map(dao::findById).flatMap(e -> e).orElseGet(TiersDto::new);
		var bean = mapper.mapToBean(dto, Map.of(OPTION_MAP_TIERS_CATEGORY_KEY, true));
		bean.initForCreateForm();
		return applyInitRules(bean, "init-create-form");
	}

	@InitBeanResult(namespace = "tiers.init.search-form")
	public TiersSearchBean initSearchFormBean() {
		var bean = mapper.mapToSearchBean();
		return  applyInitRules(bean, "init-search-form");
	}

	@InitBeanResult(namespace = "tiers.init.search-result")
	public List<TiersBean> search(TiersSearchBean searchBean) {
		var probe = new TiersDto();
		var matcher = ExampleMatcher.matchingAll();

		if (searchBean.getTiersCode() != null) {
			probe.setTiersCode(searchBean.getTiersCode().getValue());
			matcher = matcher.withMatcher("tiersCode", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		}

		if (searchBean.getTiersName() != null) {
//			probe.setName(bean.getTiersName().getValue());
			probe.setFirstName(searchBean.getTiersName().getValue());
//			probe.setLastName(bean.getTiersName().getValue());

//			matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
			matcher = matcher.withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
//			matcher = matcher.withMatcher("lastName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		}

		if (searchBean.getEmail() != null) {
			probe.setEmail(searchBean.getEmail().getValue());
			matcher = matcher.withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		}

		if (searchBean.getPhone() != null) {
			probe.setPhone(searchBean.getPhone().getValue());
			matcher = matcher.withMatcher("phone", ExampleMatcher.GenericPropertyMatchers.endsWith());
		}

		if (searchBean.getStatus() != null) {
			probe.setStatus((TiersStatusEnumVd) searchBean.getStatus().getValue());
		}

		if (searchBean.getTiersType() != null) {
			probe.setTiersType(searchBean.getTiersType().getValue());
		}
		var example = Example.of(probe, matcher);
		return dao.findAll(example).stream().map(mapper::mapToBean).collect(Collectors.toList());
	}

	@ValidateInput(namespace = "tiers.validate", initNamespace = "tiers.init.edit")
	public Map<String, Object> save(TiersBean bean) {
		var id = bean.getTiersCode().getValue();
		var dto = mapper.mapToDto(bean);
		List<Message> messages = new ArrayList<>();

		switch (bean.getAction()) {
		case CREATE:
		case UPDATE:
			save(dto);
			if (bean.getAction() == EditActionEnumVd.CREATE) {
				messages.add(Message.success(
						String.format(CREATE_SUCCESS, bean.getTiersName().getValue(), bean.getTiersType().getValue())));
			} else {
				messages.add(Message.success(
						String.format(UPDATE_SUCCESS, bean.getTiersName().getValue(), bean.getTiersType().getValue())));
			}
			messages.addAll(tiersCategoryService.synchTiersCategories(bean, bean.getCategoriesHierarchie()));
			break;

		case CHANGE_STATUS:
			save(dto);
			messages.add(Message.success(
					String.format(CHANGE_STS_SUCCESS, bean.getTiersName().getValue(), bean.getTiersType().getValue())));
			break;

		case DELETE:
			messages.addAll(tiersCategoryService.synchTiersCategories(bean, bean.getCategoriesHierarchie()));
			delete(dto);
			messages.add(Message.success(
					String.format(DELETE_SUCCESS, bean.getTiersName().getValue(), bean.getTiersType().getValue())));
			break;
		default:
			messages = Collections.singletonList(Message.warn("Aucune action à effectuer"));
		}

		return Map.of(ID_MODEL_KEY , id, MESSAGES_MODEL_KEY, messages);
	}
	
	private Supplier<RuntimeException> dtoNotFoundEx(String tiersCode){
		return () -> new RuntimeException(String.format(DTO_NOT_FOUND, tiersCode));	
	}

}
