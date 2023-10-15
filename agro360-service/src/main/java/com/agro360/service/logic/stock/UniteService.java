package com.agro360.service.logic.stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IUniteDao;
import com.agro360.dto.stock.UniteDto;
import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.bean.stock.UniteBean;
import com.agro360.service.bean.stock.UniteSearchBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.stock.UniteMapper;
import com.agro360.service.message.Message;
import com.agro360.vd.common.EditActionEnumVd;

@Service
public class UniteService extends AbstractService<UniteDto, String> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	@Autowired
	IUniteDao dao;

	@Autowired
	UniteMapper mapper;

	@Override
	protected IDao<UniteDto, String> getDao() {
		return dao;
	}
	
	@Override
	protected String getRulePath() {
		return "stock/unite";
	}
	
	public List<UniteBean> search(UniteSearchBean searchBean) {
		var example = Example.of(new UniteDto());
		if( searchBean.getUniteCode().getValue() != null ) {
			example.getProbe().setUniteCode(searchBean.getUniteCode().getValue());
		}
		return dao.findAll(example).stream()
				.map(mapper::mapToBean)
				.map(this::applyInitEditRules)
				.collect(Collectors.toList());
	}

	public List<Message> save(List<UniteBean> beans) {
		return beans.stream().map(this::save).flatMap(List::stream).collect(Collectors.toList());
	}
	
	private List<Message> save(UniteBean bean) {
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
	
	public UniteSearchBean initSearchFormBean() {
		var bean = mapper.mapToSearchBean();
		return applyInitRules(bean, "init-search-form");
	}

	public UniteBean initCreateFormBean(Optional<String> copyFrom) {
		var dto = copyFrom.map(dao::findById).flatMap(e -> e).orElseGet(UniteDto::new);
		var bean = mapper.mapToBean(dto);
		bean.getUniteCode().setValue(null);
		
		AbstractBean.setActionToCreate.accept(bean);
		
		return applyInitRules(bean, "init-create-form");
	}
}
