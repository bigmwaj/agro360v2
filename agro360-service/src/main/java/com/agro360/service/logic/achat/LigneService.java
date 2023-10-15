package com.agro360.service.logic.achat;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.achat.ILigneDao;
import com.agro360.dao.common.IDao;
import com.agro360.dto.achat.BonCommandeDto;
import com.agro360.dto.achat.LigneDto;
import com.agro360.service.bean.achat.BonCommandeBean;
import com.agro360.service.bean.achat.LigneBean;
import com.agro360.service.exception.ServiceLogicException;
import com.agro360.service.logic.common.AbstractLigneService;
import com.agro360.service.mapper.achat.LigneMapper;
import com.agro360.service.message.Message;

@Service(value = "achat/LigneService")
public class LigneService extends AbstractLigneService<LigneDto> {
	
	private static final String ACTION_REQUIRED = "L'action sur le bon de commande est requise";
	
	private static final String INCORRECT_ACTION = "L'action %s sur le bon de commande est incorrecte";
	
	private static final String DTO_NOT_FOUND = "La ligne %d du bon de commande %s n'a pas été trouvée";

	private static final String CREATE_SUCCESS = "La ligne %d du bon de commande %s a créée avec succès!";

	private static final String UPDATE_SUCCESS = "La ligne %d du bon de commande %s a créée modifiée avec succès!";

	private static final String DELETE_SUCCESS = "La ligne %d du bon de commande %s a créée supprimée avec succès!";

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
		return "achat/bon-commande/ligne";
	}

	private List<Message> deleteLigne(BonCommandeBean bonCommandeBean, List<LigneDto> existingLignes) {
		List<Message> messages = new ArrayList<>();
		dao.deleteAll(existingLignes);
		return messages;
	}
	
	private void mustExist(LigneDto dto) {
		//TODO 
	}

	private List<Message> synchLignes(BonCommandeBean bonCommandeBean, LigneBean bean, List<LigneDto> existingLignes) {
		List<Message> messages = new ArrayList<>();
		var dto = mapper.mapToDto(bonCommandeBean, bean);
		var num = dto.getNumero();
		var bc = dto.getBonCommande().getBonCommandeCode();

		switch (bean.getAction()) {
		case CREATE:
			save(dto);
			messages.add(Message.success(String.format(CREATE_SUCCESS, num, bc)));
			break;
			
		case UPDATE:
			mustExist(dto);
			save(dto);
			messages.add(Message.success(String.format(UPDATE_SUCCESS, num, bc)));
			break;

		case DELETE:
			mustExist(dto);
			if (existingLignes.contains(dto)) {
				delete(dto);
				messages.add(Message.success(String.format(DELETE_SUCCESS, num, bc)));
			}else {
				messages.add(Message.warn(String.format(DTO_NOT_FOUND, num, bc)));
			}
			break;
		default:
		}

		return messages;
	}

	List<Message> synchLignes(BonCommandeBean bonCommandeBean) {
		
		var action = bonCommandeBean.getAction();
		if( Objects.isNull(action) ){
			throw new ServiceLogicException(Message.error(ACTION_REQUIRED));
		}
		
		List<Message> messages = new ArrayList<>();
		var existingLignes = findLignes(bonCommandeBean);
		
		switch (action) {
		case CREATE:
		case UPDATE:
			var lignes = bonCommandeBean.getLignes();
			for (var bean : lignes) {
				messages.addAll(synchLignes(bonCommandeBean, bean, existingLignes));
			}
			break;

		case DELETE:
			messages.addAll(deleteLigne(bonCommandeBean, existingLignes));
			break;
		default:
			throw new ServiceLogicException(Message.error(String.format(INCORRECT_ACTION, action)));
		}

		return messages;
	}

	private List<LigneDto> findLignes(BonCommandeBean bonCommandeBean) {
		var ex = Example.of(new LigneDto());
		ex.getProbe().setBonCommande(new BonCommandeDto());
		ex.getProbe().getBonCommande().setBonCommandeCode(bonCommandeBean.getBonCommandeCode().getValue());

		return dao.findAll(ex);
	}

	public LigneBean initFormBean(String bonCommandeCode, Optional<Long> copyFrom, 
			Optional<String> articleCode) {
		
		Function<Long, Example<LigneDto>> exBldr = id -> {			
			var ex = Example.of(new LigneDto());
			
			if( Objects.nonNull(bonCommandeCode)) {
				ex.getProbe().setBonCommande(new BonCommandeDto());
				ex.getProbe().getBonCommande().setBonCommandeCode(bonCommandeCode);
			}
			
			ex.getProbe().setLigneId(id);				
			
			return ex;
		};
		
		var dto = copyFrom
				.map(exBldr)
				.map(dao::findOne)
				.flatMap(e -> e)
				.orElseGet(LigneDto::new);
		
		var bean = mapper.mapToBean(dto);
		bean.initForCreateForm();

		initArticle(bean, articleCode);
		return applyInitRules(bean, "init-create-form");
	}
}
