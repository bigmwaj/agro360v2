package com.agro360.service.logic.vente;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.vente.ILigneDao;
import com.agro360.dto.vente.CommandeDto;
import com.agro360.dto.vente.LigneDto;
import com.agro360.service.bean.vente.CommandeBean;
import com.agro360.service.bean.vente.LigneBean;
import com.agro360.service.logic.common.AbstractLigneService;
import com.agro360.service.mapper.vente.LigneMapper;
import com.agro360.service.message.Message;
import com.agro360.vd.common.EditActionEnumVd;

@Service(value = "vente/LigneService")
public class LigneService extends AbstractLigneService<LigneDto> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	@Autowired
	private ILigneDao dao;

	@Autowired
	private LigneMapper mapper;

	@Override
	protected IDao<LigneDto, Long> getDao() {
		return dao;
	}
	
	@Override
	protected String getRulePath() {
		return "vente/commande/ligne";
	}

	private List<Message> deleteLigne(CommandeBean commandeBean, List<LigneDto> existingLignes) {
		List<Message> messages = new ArrayList<>();
		dao.deleteAll(existingLignes);
		return messages;
	}

	private List<Message> synchLignes(CommandeBean commandeBean, LigneBean bean, List<LigneDto> existingLignes) {
		LigneDto dto = mapper.mapToDto(commandeBean, bean);
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

	public List<Message> synchLignes(CommandeBean commandeBean) {
		if( commandeBean.getAction() == null ) {
			return Collections.emptyList();
		}
		
		List<Message> messages = new ArrayList<>();
		List<LigneDto> existingLignes = findLignes(commandeBean);

		
		switch (commandeBean.getAction()) {
		case CREATE:
		case UPDATE:
			List<LigneBean> casiers = commandeBean.getLignes();
			for (LigneBean bean : casiers) {
				messages.addAll(synchLignes(commandeBean, bean, existingLignes));
			}
			break;

		case DELETE:
			messages.addAll(deleteLigne(commandeBean, existingLignes));
			break;
		default:
		}

		return messages;
	}

	private List<LigneDto> findLignes(CommandeBean commandeBean) {
		Example<LigneDto> ex = Example.of(new LigneDto());
		ex.getProbe().setCommande(new CommandeDto());
		ex.getProbe().getCommande().setCommandeCode(commandeBean.getCommandeCode().getValue());

		return dao.findAll(ex);
	}

	
	public LigneBean initCreateFormBean(String commandeCode, Optional<Long> copyFrom, 
			Optional<String> articleCode) {
		
		Function<Long, Example<LigneDto>> exBldr = ligneId -> {			
			var ex = Example.of(new LigneDto());
			ex.getProbe().setCommande(new CommandeDto());
			ex.getProbe().getCommande().setCommandeCode(commandeCode);
			ex.getProbe().setLigneId(ligneId);
			
			return ex;
		};
		
		var dto = copyFrom
				.map(exBldr)
				.map(dao::findOne)
				.flatMap(e -> e)
				.orElseGet(LigneDto::new);
		
		var bean = mapper.mapToBean(dto);
		bean.setAction(EditActionEnumVd.CREATE);
		bean.getLigneId().setValue(null);
		

		initArticle(bean, articleCode);
		
		return applyInitRules(bean, "init-create-form");
	}
}
