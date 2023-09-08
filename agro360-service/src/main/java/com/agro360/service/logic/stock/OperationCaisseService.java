package com.agro360.service.logic.stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IOperationCaisseDao;
import com.agro360.dto.stock.CaisseDto;
import com.agro360.dto.stock.OperationCaisseDto;
import com.agro360.dto.tiers.TiersDto;
import com.agro360.service.bean.stock.CaisseBean;
import com.agro360.service.bean.stock.CaisseIdBean;
import com.agro360.service.bean.stock.OperationCaisseBean;
import com.agro360.service.logic.common.AbstractLigneService;
import com.agro360.service.mapper.stock.CaisseMapper;
import com.agro360.service.mapper.stock.OperationCaisseMapper;
import com.agro360.service.message.Message;
import com.agro360.vd.stock.TypeOperationEnumVd;

@Service()
public class OperationCaisseService extends AbstractLigneService<OperationCaisseDto> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	@Autowired
	private IOperationCaisseDao dao;

	@Autowired
	private OperationCaisseMapper mapper;
	
	@Autowired
	private CaisseMapper caisseMapper;
	
	@Override
	protected IDao<OperationCaisseDto, Long> getDao() {
		return dao;
	}

	private List<Message> deleteOperationCaisse(CaisseBean caisseBean, List<OperationCaisseDto> existingOperationCaisses) {
		List<Message> messages = new ArrayList<>();
		dao.deleteAll(existingOperationCaisses);
		return messages;
	}

	private List<Message> synchOperationCaisses(CaisseBean caisseBean, OperationCaisseBean bean, List<OperationCaisseDto> existingOperationCaisses) {
		var dto = mapper.mapToDto(caisseBean, bean);
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
			if (existingOperationCaisses.contains(dto)) {
				delete(dto);
				messages.add(Message.success(DELETE_SUCCESS));
			}else {
				messages.add(Message.warn(String.format("La ligne %d n'existe pas!", bean.getLigneId().getValue())));
			}
			break;
		default:
		}

		return messages;
	}

	public List<Message> synchOperationCaisses(CaisseBean caisseBean) {
		if( caisseBean.getAction() == null ) {
			return Collections.emptyList();
		}
		
		List<Message> messages = new ArrayList<>();
		var existingOperationCaisses = findOperationCaisses(caisseBean);
		
		switch (caisseBean.getAction()) {
		case CREATE:
		case UPDATE:
			var casiers = caisseBean.getOperations();
			for (OperationCaisseBean bean : casiers) {
				messages.addAll(synchOperationCaisses(caisseBean, bean, existingOperationCaisses));
			}
			break;

		case DELETE:
			messages.addAll(deleteOperationCaisse(caisseBean, existingOperationCaisses));
			break;
		default:
		}

		return messages;
	}

	private List<OperationCaisseDto> findOperationCaisses(CaisseBean caisseBean) {
		var ex = Example.of(new OperationCaisseDto());
		ex.getProbe().setCaisse(new CaisseDto());
		ex.getProbe().getCaisse().setJournee(caisseBean.getJournee().getValue());
		ex.getProbe().getCaisse().setAgent(new TiersDto());
		ex.getProbe().getCaisse().getAgent().setTiersCode(caisseBean.getAgent().getTiersCode().getValue());

		return dao.findAll(ex);
	}


	public OperationCaisseBean initCreateFormBean(CaisseIdBean idBean, 
			Optional<Long> copyFrom, 
			Optional<String> articleCode,
			Optional<TypeOperationEnumVd> typeOperation) {
		
		Function<Long, Example<OperationCaisseDto>> query = id -> {			
			var ex = Example.of(new OperationCaisseDto());
			ex.getProbe().setCaisse(caisseMapper.mapToDto(idBean));			
			ex.getProbe().setLigneId(id);
			return ex;
		};
		
		var dto = copyFrom.map(query)
				.map(dao::findOne)
				.flatMap(e -> e)
				.orElseGet(OperationCaisseDto::new);
		var bean = mapper.mapToBean(dto);
		bean.initForCreateForm();

		if( typeOperation.isPresent() ) {
			bean.getTypeOperation().setValue(typeOperation.get());
		}
		
		initArticle(bean, articleCode);
		
		return bean;
	}
}
