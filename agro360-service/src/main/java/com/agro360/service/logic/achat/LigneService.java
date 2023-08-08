package com.agro360.service.logic.achat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.achat.ILigneDao;
import com.agro360.dto.achat.BonCommandeDto;
import com.agro360.dto.achat.LigneDto;
import com.agro360.service.bean.achat.BonCommandeBean;
import com.agro360.service.bean.achat.LigneBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.achat.LigneMapper;
import com.agro360.service.message.Message;

@Service(value = "achat/LigneService")
public class LigneService extends AbstractService<LigneDto, Long> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	@Autowired
	ILigneDao dao;

	@Autowired
	LigneMapper mapper;

	@Override
	protected IDao<LigneDto, Long> getDao() {
		return dao;
	}

	private List<Message> deleteLigne(BonCommandeBean bonCommandeBean, List<LigneDto> existingLignes) {
		List<Message> messages = new ArrayList<>();
		dao.deleteAll(existingLignes);
		return messages;
	}

	private List<Message> synchLignes(BonCommandeBean bonCommandeBean, LigneBean bean, List<LigneDto> existingLignes) {
		LigneDto dto = mapper.mapToDto(bonCommandeBean, bean);
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
			if (existingLignes.contains(dto)) {
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

	public List<Message> synchLignes(BonCommandeBean bonCommandeBean) {
		if( bonCommandeBean.getAction() == null ) {
			return Collections.emptyList();
		}
		
		List<Message> messages = new ArrayList<>();
		List<LigneDto> existingLignes = findLignes(bonCommandeBean);

		
		switch (bonCommandeBean.getAction()) {
		case CREATE:
		case UPDATE:
			List<LigneBean> casiers = bonCommandeBean.getLignes();
			for (LigneBean bean : casiers) {
				messages.addAll(synchLignes(bonCommandeBean, bean, existingLignes));
			}
			break;

		case DELETE:
			messages.addAll(deleteLigne(bonCommandeBean, existingLignes));
			break;
		default:
		}

		return messages;
	}

	private List<LigneDto> findLignes(BonCommandeBean bonCommandeBean) {
		Example<LigneDto> ex = Example.of(new LigneDto());
		ex.getProbe().setBonCommande(new BonCommandeDto());
		ex.getProbe().getBonCommande().setBonCommandeCode(bonCommandeBean.getBonCommandeCode().getValue());

		return dao.findAll(ex);
	}
}
