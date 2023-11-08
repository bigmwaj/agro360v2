package com.agro360.service.logic.stock;

import static com.agro360.service.mapper.stock.CaisseMapper.OPTION_MAP_OPERATION_KEY;
import static com.agro360.service.mapper.stock.CaisseMapper.OPTION_MAP_PLUS_KEY;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.ICaisseDao;
import com.agro360.dto.stock.CaisseDto;
import com.agro360.dto.stock.CaissePk;
import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.bean.stock.CaisseBean;
import com.agro360.service.bean.stock.CaisseIdBean;
import com.agro360.service.bean.stock.CaisseSearchBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.stock.CaisseMapper;
import com.agro360.service.message.Message;
import com.agro360.vd.common.EditActionEnumVd;
import com.agro360.vd.stock.StatusCaisseEnumVd;

@Service
public class CaisseService extends AbstractService<CaisseDto, CaissePk> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String STATUS_CHANGE_SUCCESS = "Statut enregistrement changé avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	private static final String DTO_NOT_FOUND = "Aucune caisse du magasin %s, de l'agent %s et de la journée n'a été trouvée";

	@Autowired
	private ICaisseDao dao;

	@Autowired
	private OperationCaisseService operationCaisseService;

	@Autowired
	private CaisseMapper mapper;

	@Override
	protected IDao<CaisseDto, CaissePk> getDao() {
		return dao;
	}
	
	@Override
	protected String getRulePath() {
		return "stock/caisse";
	}

	public List<CaisseBean> search(CaisseSearchBean searchBean) {
		return dao.findAll().stream().map(mapper::mapToBean)
				.map(this::applyInitEditRules)
				.collect(Collectors.toList());
	}

	public Map<String, Object> save(CaisseBean bean) {
		applyValidationRules(bean);
		var dto = mapper.mapToDto(bean);
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

		case CHANGE_STATUS:
			save(dto);
			messages.add(Message.success(STATUS_CHANGE_SUCCESS));
			break;

		case DELETE:
			messages.addAll(operationCaisseService.synchOperationCaisses(bean));
			delete(dto);
			messages.add(Message.success(DELETE_SUCCESS));
			break;
		default:
			messages.add(Message.error("Aucune action à effectuer"));
		}
		
		var magasin = bean.getMagasin().getMagasinCode().getValue();
		var agent =bean.getAgent().getTiersCode().getValue();
		var journee = bean.getJournee().getValue();
		var id = new CaisseIdBean(magasin, agent, journee);
		
		return Map.of(ID_MODEL_KEY, id, MESSAGES_MODEL_KEY, messages);
	}
	
	public CaisseSearchBean initSearchFormBean() {
		var bean = mapper.mapToSearchBean();
		//applyInitRules(bean, "init-search-form");
		return bean;
	}
	
	public CaisseBean initEditFormBean(CaisseIdBean idBean) {
		var dto = dao.findById(mapper.mapToId(idBean)).orElseThrow(dtoNotFoundEx(idBean));
		var bean = mapper.mapToBean(dto, Map.of(OPTION_MAP_OPERATION_KEY, true, OPTION_MAP_PLUS_KEY, true));
		applyInitRules(bean, "init-edit-form");
		return bean;
	}
	
	public CaisseBean initDeleteFormBean(CaisseIdBean idBean) {
		var bean = dao.findById(mapper.mapToId(idBean))
				.map(mapper::mapToBean)
				.orElseThrow(dtoNotFoundEx(idBean));
		bean.setAction(EditActionEnumVd.DELETE);
		applyInitRules(bean, "init-delete-form");
		return bean;
	}
	
	public CaisseBean initChangeStatusFormBean(CaisseIdBean idBean) {
		var bean = dao.findById(mapper.mapToId(idBean))
				.map(mapper::mapToBean)
				.orElseThrow(dtoNotFoundEx(idBean));
		
		bean.setAction(EditActionEnumVd.CHANGE_STATUS);
		bean.getStatusDate().setValue(LocalDateTime.now().withNano(0));
		applyInitRules(bean, "init-change-status-form");
		return bean;
	}

	public CaisseBean initCreateFormBean(Optional<CaisseIdBean> idBean) {
		var dto = idBean.map(mapper::mapToId)
				.map(dao::findById)
				.flatMap(e -> e)
				.orElseGet(CaisseDto::new);
		
		var bean = mapper.mapToBean(dto);		
		bean.getJournee().setValue(LocalDate.now());
		bean.getStatus().setValue(StatusCaisseEnumVd.ENPREPAR);
		bean.getStatusDate().setValue(LocalDateTime.now());
		
		AbstractBean.setActionToCreate.accept(bean);		
		applyInitRules(bean, "init-create-form");
		return bean;
	}
	
	private Supplier<RuntimeException> dtoNotFoundEx(CaisseIdBean idBean){
		return () -> new RuntimeException(String.format(DTO_NOT_FOUND, 
				idBean.getMagasin(), 
				idBean.getAgent(), 
				idBean.getJournee()));	
	}

}
