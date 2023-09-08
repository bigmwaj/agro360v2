package com.agro360.service.logic.stock;

import static com.agro360.service.mapper.stock.MagasinMapper.OPTION_MAP_CASIER_KEY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IMagasinDao;
import com.agro360.dto.stock.MagasinDto;
import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.bean.stock.MagasinBean;
import com.agro360.service.bean.stock.MagasinSearchBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.stock.MagasinMapper;
import com.agro360.service.message.Message;
import com.agro360.vd.common.EditActionEnumVd;


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
	
	public List<MagasinBean> search(MagasinSearchBean searchBean) {
		return dao.findAll().stream().map(mapper::mapToBean).collect(Collectors.toList());
	}

	public Map<String, Object> save(MagasinBean bean) {
		var id = bean.getMagasinCode().getValue();
		var dto = mapper.mapToDto(bean);
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
			messages = Collections.singletonList(Message.warn("Aucune action à effectuer"));
		}
		return Map.of(ID_MODEL_KEY , id, MESSAGES_MODEL_KEY, messages);
	}

	public MagasinSearchBean initSearchFormBean() {
		return mapper.mapToSearchBean();
	}
	
	public MagasinBean initEditFormBean(String MagasinCode) {
		var dto = dao.findById(MagasinCode).orElseThrow();
		var bean = mapper.mapToBean(dto,  Map.of(OPTION_MAP_CASIER_KEY, true));
		bean.setAction(EditActionEnumVd.UPDATE);
		return bean;
	}
	
	public MagasinBean initDeleteFormBean(String MagasinCode) {
		var bean = dao.findById(MagasinCode).map(mapper::mapToBean).orElseThrow();
		bean.setAction(EditActionEnumVd.DELETE);
		return bean;
	}

	public MagasinBean initCreateFormBean(Optional<String> copyFrom) {
		var dto = copyFrom.map(dao::findById).flatMap(e -> e).orElseGet(MagasinDto::new);
		var bean = mapper.mapToBean(dto, Map.of(OPTION_MAP_CASIER_KEY, true));
		bean.getMagasinCode().setValue(null);
		
		AbstractBean.setActionToCreate.accept(bean);
		bean.getCasiers().stream().forEach(AbstractBean.setActionToCreate);
		
		return bean;
	}
}
