package com.agro360.service.logic.achat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.achat.ILigneDao;
import com.agro360.dao.achat.IReceptionDao;
import com.agro360.dao.common.IDao;
import com.agro360.dto.achat.BonCommandeDto;
import com.agro360.dto.achat.LigneDto;
import com.agro360.dto.achat.ReceptionDto;
import com.agro360.service.bean.achat.ReceptionBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.achat.ReceptionMapper;
import com.agro360.service.utils.Message;

@Service
public class ReceptionService extends AbstractService<ReceptionDto, Long> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	private static final String CHANGE_STS_SUCCESS = "Statut enregistrement modifié avec succès!";

	@Autowired
	IReceptionDao dao;
	
	@Autowired
	ILigneDao ligneDao;

	@Autowired
	LigneService ligneService;

	@Autowired
	ReceptionMapper mapper;

	@Override
	protected IDao<ReceptionDto, Long> getDao() {
		return dao;
	}

	public List<ReceptionBean> search(String bonCommandeCode) {
		var ex = Example.of(new ReceptionDto());
		ex.getProbe().setLigne(new LigneDto());
		ex.getProbe().getLigne().setBonCommande(new BonCommandeDto());
		ex.getProbe().getLigne().getBonCommande().setBonCommandeCode(bonCommandeCode);
		return dao.findAll(ex).stream().map(mapper::mapToBean).collect(Collectors.toList());
	}

	private List<Message> save(ReceptionBean bean) {
		if (bean.getAction() == null) {
			return Collections.singletonList(Message.error("Aucune action sélectionnée"));
		}

		ReceptionDto dto = mapper.mapToDto(bean);
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

		case CHANGE_STATUS:
			save(dto);
			messages.add(Message.success(CHANGE_STS_SUCCESS));
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

	public List<Message> save(List<ReceptionBean> beans) {
		return beans.stream().map(this::save).flatMap(List::stream).toList();
	}
	
	private List<LigneDto> findLignesBonCommande(String bonCommandeCode){
		var ex = Example.of(new LigneDto());
		ex.getProbe().setBonCommande(new BonCommandeDto());
		ex.getProbe().getBonCommande().setBonCommandeCode(bonCommandeCode);
		
		return ligneDao.findAll(ex);
		
	}
	
	public List<ReceptionBean> getReceptionInputForm(String bonCommandeCode){
		var lignes = findLignesBonCommande(bonCommandeCode);
		return lignes.stream().map(mapper::mapToBean).toList();
	}

}
