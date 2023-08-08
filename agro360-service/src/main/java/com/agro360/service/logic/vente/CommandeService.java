package com.agro360.service.logic.vente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.vente.ICommandeDao;
import com.agro360.dto.vente.CommandeDto;
import com.agro360.service.bean.vente.CommandeBean;
import com.agro360.service.bean.vente.CommandeSearchBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.vente.CommandeMapper;
import com.agro360.service.message.Message;

@Service
public class CommandeService extends AbstractService<CommandeDto, String> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	@Autowired
	ICommandeDao dao;

	@Autowired
	LigneService ligneService;

	@Autowired
	CommandeMapper mapper;

	@Override
	protected IDao<CommandeDto, String> getDao() {
		return dao;
	}

	public List<CommandeBean> search(CommandeSearchBean searchBean) {
		return dao.findAll().stream().map(mapper::mapToBean).collect(Collectors.toList());
	}

	public List<Message> save(CommandeBean bean) {
		if( bean.getAction() == null ) {
			return Collections.singletonList(Message.error("Aucune action sélectionnée"));
		}
		
		CommandeDto dto = mapper.mapToDto(bean);
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
			return Collections.singletonList(Message.warn("Aucune action à effectuer"));
		}
		return messages;
	}

	public CommandeBean findById(String id) {
		return dao.findById(id)
				.map(e -> mapper.mapToBean(e, Map.of(CommandeMapper.OPTION_MAP_LIGNE_KEY, true)))
				.orElseThrow();
	}

}
