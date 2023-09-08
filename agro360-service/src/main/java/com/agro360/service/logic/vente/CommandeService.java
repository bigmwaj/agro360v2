package com.agro360.service.logic.vente;

import static com.agro360.service.mapper.vente.CommandeMapper.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.vente.ICommandeDao;
import com.agro360.dto.vente.CommandeDto;
import com.agro360.service.bean.vente.CommandeBean;
import com.agro360.service.bean.vente.CommandeSearchBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.vente.CommandeMapper;
import com.agro360.service.message.Message;
import com.agro360.vd.common.EditActionEnumVd;

@Service
public class CommandeService extends AbstractService<CommandeDto, String> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";
	
	private static final String DTO_NOT_FOUND = "Aucune commande de numéro %s n'a été trouvée";

	@Autowired
	private ICommandeDao dao;

	@Autowired
	private LigneService ligneService;

	@Autowired
	private CommandeMapper mapper;

	@Override
	protected IDao<CommandeDto, String> getDao() {
		return dao;
	}

	public List<CommandeBean> search(CommandeSearchBean searchBean) {
		var probe = new CommandeDto();
		var matcher = ExampleMatcher.matchingAll();
		
		if (searchBean.getCommandeCode() != null) {
			probe.setCommandeCode(searchBean.getCommandeCode().getValue());
			matcher = matcher.withMatcher("commandeCode", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		}

		var example = Example.of(probe, matcher);
		return dao.findAll(example).stream().map(mapper::mapToBean).collect(Collectors.toList());
	}

	public Map<String, Object> save(CommandeBean bean) {
		var id = bean.getCommandeCode().getValue();
		var dto = mapper.mapToDto(bean);
		List<Message> messages = new ArrayList<>();

		switch (bean.getAction()) {
		case CREATE:
			save(dto);
			messages.add(Message.success(CREATE_SUCCESS));
			messages.addAll(ligneService.synchLignes(bean));
			break;

		case UPDATE:
			save(dto);
			messages.add(Message.success(UPDATE_SUCCESS));
			messages.addAll(ligneService.synchLignes(bean));
			break;

		case DELETE:
			messages.addAll(ligneService.synchLignes(bean));
			delete(dto);
			messages.add(Message.success(DELETE_SUCCESS));
			break;
		default:
			messages = Collections.singletonList(Message.warn("Aucune action à effectuer"));
		}
		return Map.of(ID_MODEL_KEY , id, MESSAGES_MODEL_KEY, messages);
	}

	public CommandeBean initUpdateFormBean(String commandeCode) {
		var dto = dao.findById(commandeCode).orElseThrow(dtoNotFoundEx(commandeCode));
		var bean = mapper.mapToBean(dto, Map.of(OPTION_MAP_LIGNE_KEY, true, OPTION_MAP_PLUS_KEY, true));
		return bean;
	}
	
	public CommandeBean initDeleteFormBean(String commandeCode) {
		var bean = dao.findById(commandeCode).map(mapper::mapToBean)
				.orElseThrow(dtoNotFoundEx(commandeCode));
		bean.setAction(EditActionEnumVd.DELETE);
		return bean;
	}
	
	public CommandeBean initChangeStatusFormBean(String commandeCode) {
		var bean = dao.findById(commandeCode).map(mapper::mapToBean)
				.orElseThrow(dtoNotFoundEx(commandeCode));
		bean.setAction(EditActionEnumVd.CHANGE_STATUS);
		return bean;
	}

	public CommandeBean initCreateFormBean(Optional<String> copyFrom) {
		var dto = copyFrom.map(dao::findById).flatMap(e -> e).orElseGet(CommandeDto::new);
		var bean = mapper.mapToBean(dto, Map.of(OPTION_MAP_LIGNE_KEY, true, OPTION_MAP_PLUS_KEY, true));
		bean.initForCreateForm();
		return bean;
	}

	public CommandeSearchBean initSearchFormBean() {
		return mapper.mapToSearchBean();
	}
	
	private Supplier<RuntimeException> dtoNotFoundEx(String commandeCode){
		return () -> new RuntimeException(String.format(DTO_NOT_FOUND, commandeCode));	
	}
}
