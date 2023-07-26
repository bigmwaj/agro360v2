package com.agro360.service.logic.stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IMagasinDao;
import com.agro360.dto.stock.MagasinDto;
import com.agro360.service.bean.stock.MagasinBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.stock.MagasinMapper;
import com.agro360.service.utils.Message;

@Service
public class MagasinService extends AbstractService<MagasinDto, String> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	@Autowired
	IMagasinDao dao;

	@Autowired
	CasierService casierService;

	@Autowired
	MagasinMapper mapper;

	@Override
	protected IDao<MagasinDto, String> getDao() {
		return dao;
	}
	
	public List<MagasinBean> search() {
		return dao.findAll().stream().map(mapper::mapToBean).collect(Collectors.toList());
	}

	public List<Message> save(MagasinBean bean) {
		MagasinDto dto = mapper.mapToDto(bean);
		List<Message> messages = new ArrayList<>();

		switch (bean.getAction()) {
		case CREATE:
			save(dto);
			messages.add(Message.success(CREATE_SUCCESS));
			messages.addAll(casierService.synchCasiers(dto, bean));
			break;

		case UPDATE:
			save(dto);
			messages.add(Message.success(UPDATE_SUCCESS));
			messages.addAll(casierService.synchCasiers(dto, bean));
			break;

		case DELETE:
			messages.addAll(casierService.synchCasiers(dto, bean));
			delete(dto);
			messages.add(Message.success(DELETE_SUCCESS));
			break;
		default:
			return Collections.singletonList(Message.warn("Aucune action à effectuer"));
		}
		return messages;
	}

	public MagasinBean findById(String id) {
		return dao.findById(id).map(e -> mapper.mapToBean(e, Map.of(MagasinMapper.OPTION_MAP_CASIER_KEY, true))).orElseThrow();
	}
}
