package com.agro360.service.logic.stock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.ICaisseDao;
import com.agro360.dto.stock.CaisseDto;
import com.agro360.dto.stock.CaissePk;
import com.agro360.service.bean.stock.CaisseBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.stock.CaisseMapper;
import com.agro360.service.utils.Message;

@Service
public class CaisseService extends AbstractService<CaisseDto, CaissePk> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	@Autowired
	ICaisseDao dao;

	@Autowired
	OperationCaisseService operationCaisseService;

	@Autowired
	CaisseMapper mapper;

	@Override
	protected IDao<CaisseDto, CaissePk> getDao() {
		return dao;
	}

	public List<CaisseBean> search() {
		return dao.findAll().stream().map(mapper::mapToBean).collect(Collectors.toList());
	}

	public List<Message> save(CaisseBean bean) {
		if( bean.getAction() == null ) {
			return Collections.singletonList(Message.error("Aucune action sélectionnée"));
		}
		
		CaisseDto dto = mapper.mapToDto(bean);
		List<Message> messages = new ArrayList<>();

		switch (bean.getAction()) {
		case CREATE:
			save(dto);
			messages.add(Message.success(CREATE_SUCCESS));
			messages.addAll(operationCaisseService.synchOperationCaisses(bean));
			break;

		case UPDATE:
			save(dto);
			messages.add(Message.success(UPDATE_SUCCESS));
			messages.addAll(operationCaisseService.synchOperationCaisses(bean));
			break;

		case DELETE:
			messages.addAll(operationCaisseService.synchOperationCaisses(bean));
			delete(dto);
			messages.add(Message.success(DELETE_SUCCESS));
			break;
		default:
			return Collections.singletonList(Message.warn("Aucune action à effectuer"));
		}
		return messages;
	}

	public CaisseBean findById(String magasinCode, String tiersCode, LocalDate journee) {
		return dao.findById(new CaissePk(magasinCode, tiersCode, journee))
				.map(e -> mapper.mapToBean(e, Map.of(CaisseMapper.OPTION_MAP_OPERATION_KEY, true)))
				.orElseThrow();
	}

}
