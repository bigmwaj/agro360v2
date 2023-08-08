package com.agro360.service.logic.stock;

import static com.agro360.service.mapper.stock.MagasinMapper.OPTION_MAP_CASIER_KEY;

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
import com.agro360.service.message.Message;

@Service
public class MagasinService extends AbstractService<MagasinDto, String> {

	private static final String CREATE_SUCCESS = "Succès création du magasin %s!";

	private static final String UPDATE_SUCCESS = "Succès modification du magasin %s!";

	private static final String DELETE_SUCCESS = "Succès suppression du magasin %s!";

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
		if( bean.getAction() == null ) {
			return Collections.singletonList(Message.error("Aucune action sélectionnée"));
		}
		
		MagasinDto dto = mapper.mapToDto(bean);
		List<Message> messages = new ArrayList<>();

		switch (bean.getAction()) {
		case CREATE:
			save(dto);
			messages.add(Message.success(String.format(CREATE_SUCCESS, dto.getMagasinCode())));
			messages.addAll(casierService.synchCasiers(dto, bean));
			break;

		case UPDATE:
			save(dto);
			messages.add(Message.success(String.format(UPDATE_SUCCESS, dto.getMagasinCode())));
			messages.addAll(casierService.synchCasiers(dto, bean));
			break;

		case DELETE:
			messages.addAll(casierService.synchCasiers(dto, bean));
			delete(dto);
			messages.add(Message.success(String.format(DELETE_SUCCESS, dto.getMagasinCode())));
			break;
		default:
			return Collections.singletonList(Message.warn("Aucune action à effectuer"));
		}
		return messages;
	}

	public MagasinBean findById(String id) {
		return dao.findById(id).map(e -> mapper.mapToBean(e, Map.of(OPTION_MAP_CASIER_KEY, true))).orElseThrow();
	}
}
