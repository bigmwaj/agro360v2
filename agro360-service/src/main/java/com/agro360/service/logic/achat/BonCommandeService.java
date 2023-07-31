package com.agro360.service.logic.achat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.achat.IBonCommandeDao;
import com.agro360.dto.achat.BonCommandeDto;
import com.agro360.service.bean.achat.BonCommandeBean;
import com.agro360.service.bean.achat.BonCommandeSearchBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.achat.BonCommandeMapper;
import com.agro360.service.utils.Message;

@Service
public class BonCommandeService extends AbstractService<BonCommandeDto, String> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	@Autowired
	IBonCommandeDao dao;

	@Autowired
	LigneService ligneService;

	@Autowired
	BonCommandeMapper mapper;

	@Override
	protected IDao<BonCommandeDto, String> getDao() {
		return dao;
	}

	public List<BonCommandeBean> search(BonCommandeSearchBean searchBean) {
		return dao.findAll().stream().map(mapper::mapToBean).collect(Collectors.toList());
	}

	public List<Message> save(BonCommandeBean bean) {
		if( bean.getAction() == null ) {
			return Collections.singletonList(Message.error("Aucune action sélectionnée"));
		}
		
		BonCommandeDto dto = mapper.mapToDto(bean);
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

	public BonCommandeBean findById(String id) {
		return dao.findById(id)
				.map(e -> mapper.mapToBean(e, Map.of(BonCommandeMapper.OPTION_MAP_LIGNE_KEY, true)))
				.orElseThrow();
	}

}
