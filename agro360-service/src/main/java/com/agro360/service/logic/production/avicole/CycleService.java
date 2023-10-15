package com.agro360.service.logic.production.avicole;

import static com.agro360.service.mapper.production.avicole.CycleMapper.OPTION_MAP_METADATA_KEY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.production.avicole.ICycleDao;
import com.agro360.dto.production.avicole.CycleDto;
import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.bean.production.avicole.CycleBean;
import com.agro360.service.bean.production.avicole.CycleSearchBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.production.avicole.CycleMapper;
import com.agro360.service.message.Message;
import com.agro360.vd.common.EditActionEnumVd;

@Service
public class CycleService extends AbstractService<CycleDto, String> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	private static final String DTO_NOT_FOUND = "Aucun n'article de code %s n'a été trouvé";

	@Autowired
	private ICycleDao dao;

	@Autowired
	private CycleMapper mapper;

	@Override
	protected IDao<CycleDto, String> getDao() {
		return dao;
	}
	
	@Override
	protected String getRulePath() {
		return "production/avicole/cycle";
	}
	
	public CycleSearchBean initSearchFormBean() {
		var bean = mapper.mapToSearchBean();
		return applyInitRules(bean, "init-search-form");
	}
	
	public CycleBean initEditFormBean(String articleCode) {
		var dto = dao.findById(articleCode).orElseThrow(dtoNotFoundEx(articleCode));
		var bean = mapper.mapToBean(dto, Map.of(OPTION_MAP_METADATA_KEY, true));
		return applyInitRules(bean, "init-edit-form");
	}
	
	public CycleBean initDeleteFormBean(String articleCode) {
		var bean = dao.findById(articleCode).map(mapper::mapToBean).orElseThrow(dtoNotFoundEx(articleCode));
		bean.setAction(EditActionEnumVd.DELETE);
		return applyInitRules(bean, "init-delete-form");
	}

	public CycleBean initCreateFormBean(Optional<String> copyFrom) {
		var dto = copyFrom.map(dao::findById).flatMap(e -> e).orElseGet(CycleDto::new);
		var bean = mapper.mapToBean(dto);
		bean.getCycleCode().setValue(null);
		
		AbstractBean.setActionToCreate.accept(bean);
		
		return applyInitRules(bean, "init-create-form");
	}

	public List<CycleBean> search(CycleSearchBean searchBean) {
		var example = Example.of(new CycleDto());
		if( searchBean.getCycleCode().getValue() != null ) {
			example.getProbe().setCycleCode(searchBean.getCycleCode().getValue());
		}
		return dao.findAll(example).stream().map(mapper::mapToBean).collect(Collectors.toList());
	}

	public Map<String, Object> save(CycleBean bean) {

		var id = bean.getCycleCode().getValue();
		var dto = mapper.mapToDto(bean);
		List<Message> messages = new ArrayList<>();

		switch (bean.getAction()) {
		case CREATE:
			save(dto);
			messages.add(Message.success(CREATE_SUCCESS));
			break;

		case UPDATE:
			save(dto);
			messages.add(Message.success(UPDATE_SUCCESS));
			break;

		case DELETE:
			delete(dto);
			messages.add(Message.success(DELETE_SUCCESS));
			break;
			
		default:
			messages = Collections.singletonList(Message.warn("Aucune action à effectuer"));
		}
		return Map.of(ID_MODEL_KEY , id, MESSAGES_MODEL_KEY, messages);
	}
	
	private Supplier<RuntimeException> dtoNotFoundEx(String articleCode){
		return () -> new RuntimeException(String.format(DTO_NOT_FOUND, articleCode));	
	}
}
