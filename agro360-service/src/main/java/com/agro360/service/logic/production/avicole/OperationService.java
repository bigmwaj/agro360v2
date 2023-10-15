package com.agro360.service.logic.production.avicole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.production.avicole.IOperationDao;
import com.agro360.dto.production.avicole.CycleDto;
import com.agro360.dto.production.avicole.OperationDto;
import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.bean.production.avicole.OperationBean;
import com.agro360.service.bean.production.avicole.OperationSearchBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.production.avicole.OperationMapper;
import com.agro360.service.message.Message;
import com.agro360.vd.common.EditActionEnumVd;

@Service
public class OperationService extends AbstractService<OperationDto, Long> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	@Autowired
	private IOperationDao dao;

	@Autowired
	private OperationMapper mapper;

	@Override
	protected IDao<OperationDto, Long> getDao() {
		return dao;
	}
	
	@Override
	protected String getRulePath() {
		return "production/avicole/operation";
	}
	
	public OperationSearchBean initSearchFormBean() {
		var bean = mapper.mapToSearchBean();
		return applyInitSearchRules(bean);
	}
	
	public List<OperationBean> search(OperationSearchBean searchBean) {
		var example = Example.of(new OperationDto());
		if( searchBean.getCycleCode().getValue() != null ) {
			example.getProbe().setCycle(new CycleDto());
			example.getProbe().getCycle().setCycleCode(searchBean.getCycleCode().getValue());
		}
		return dao.findAll(example).stream()
				.map(mapper::mapToBean)
				.map(this::applyInitEditRules)
				.collect(Collectors.toList());
	}

	public List<Message> save(List<OperationBean> beans) {
		return beans.stream().map(this::save)
				.flatMap(List::stream)
				.collect(Collectors.toList());
	}
	
	private List<Message> save(OperationBean bean) {
		if( bean.getAction() == null ) {
			return Collections.singletonList(Message.error("Aucune action sélectionnée"));
		}
		
		if(  EditActionEnumVd.SYNC.equals(bean.getAction())) {
			return Collections.emptyList();
		}
		
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
			return Collections.singletonList(Message.warn("Aucune action à effectuer"));
		}

		return messages;
	}

	public OperationBean initCreateFormBean(Optional<Long> copyFrom) {
		var dto = copyFrom.map(dao::findById).flatMap(e -> e).orElseGet(OperationDto::new);
		var bean = mapper.mapToBean(dto);
		bean.getLigneId().setValue(null);
		
		AbstractBean.setActionToCreate.accept(bean);
		
		return applyInitCreateRules(bean);
	}
}
