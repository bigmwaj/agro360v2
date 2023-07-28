package com.agro360.service.logic.stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IUniteDao;
import com.agro360.dto.stock.UniteDto;
import com.agro360.service.bean.stock.UniteBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.stock.UniteMapper;
import com.agro360.service.utils.Message;

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
	
	public List<UniteBean> search() {
		return dao.findAll().stream().map(mapper::mapToBean).collect(Collectors.toList());
	}

	public List<Message> save(UniteBean bean) {
		if( bean.getAction() == null ) {
			return Collections.singletonList(Message.error("Aucune action sélectionnée"));
		}
		
		UniteDto dto = mapper.mapToDto(bean);
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

	public UniteBean findById(String id) {
		return dao.findById(id).map(mapper::mapToBean).orElseThrow();
	}
}
