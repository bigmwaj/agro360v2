package com.agro360.service.logic.achat;

import static com.agro360.service.mapper.achat.BonCommandeMapper.*;
import static com.agro360.service.mapper.stock.CaisseMapper.OPTION_MAP_PLUS_KEY;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.dao.achat.IBonCommandeDao;
import com.agro360.dao.common.IDao;
import com.agro360.dto.achat.BonCommandeDto;
import com.agro360.dto.tiers.TiersDto;
import com.agro360.service.bean.achat.BonCommandeBean;
import com.agro360.service.bean.achat.BonCommandeSearchBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.achat.BonCommandeMapper;
import com.agro360.service.message.Message;
import com.agro360.vd.common.EditActionEnumVd;

@Service
public class BonCommandeService extends AbstractService<BonCommandeDto, String> {

	private static final String DTO_NOT_FOUND = "Aucun bon de commande de numéro %s n'a été trouvé";

	private static final String CREATE_SUCCESS = "Le bon de commande %s a créé avec succès!";

	private static final String UPDATE_SUCCESS = "Le bon de commande %s a modifié avec succès!";

	private static final String DELETE_SUCCESS = "Le bon de commande %s a supprimé avec succès!";

	@Autowired
	private IBonCommandeDao dao;
	
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private LigneService ligneService;

	@Autowired
	private BonCommandeMapper mapper;

	@Override
	protected IDao<BonCommandeDto, String> getDao() {
		return dao;
	}
	
	@Override
	protected String getRulePath() {
		return "achat/bon-commande";
	}

	public List<BonCommandeBean> search(BonCommandeSearchBean searchBean) {
		var criteriaBuilder = entityManager.getCriteriaBuilder();
		
		var rootQuery = criteriaBuilder.createQuery(BonCommandeDto.class);
		var from = rootQuery.from(BonCommandeDto.class);
		
		List<Predicate> wherePredicates = new ArrayList<>();
		
		var code = searchBean.getBonCommandeCode().getValue();
		if( code != null && !code.isBlank() ) {
			code = '%' + code.toUpperCase() + '%';
			wherePredicates.add(criteriaBuilder.like(from.get("bonCommandeCode"), code));	
		}
		var status = searchBean.getStatusIn().getValue();
		if( status != null && !status.isEmpty() ) {
			wherePredicates.add(from.get("status").in(status));
		}

		var debut = searchBean.getDateBonCommandeDebut().getValue();
		if( debut != null ) {
			wherePredicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("dateBonCommande"), debut));	
		}

		var fin = searchBean.getDateBonCommandeFin().getValue();
		if( fin != null ) {
			wherePredicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("dateBonCommande"), fin));	
		}

		var fournisseur = searchBean.getFournisseur().getValue();
		if( fournisseur != null && !fournisseur.isBlank() ) {			
			fournisseur = '%' + fournisseur.toUpperCase() + '%';
			
			var subQuery = criteriaBuilder.createQuery(TiersDto.class);
			var subQueryFrom = subQuery.from(TiersDto.class);
			
			
			
			subQuery.select(subQueryFrom)
			  .distinct(true)
			  .where(criteriaBuilder.like(subQueryFrom.get("name"), fournisseur));
			
			//wherePredicates.add(criteriaBuilder.in(from.get("fournisseur")).value(subQuery.));
		}
		
		if( !wherePredicates.isEmpty() ) {
			rootQuery.where(wherePredicates.toArray(new Predicate[0]));
		}
		
		var query = entityManager.createQuery(rootQuery);
		System.out.println("BonCommandeService.search() La requette est " + query);
        return query.getResultList().stream().map(mapper::mapToBean).collect(Collectors.toList());
		
	}

	public Map<String, Object> save(BonCommandeBean bean) {
		var id = bean.getBonCommandeCode().getValue();
		var dto = mapper.mapToDto(bean);
		List<Message> messages = new ArrayList<>();

		switch (bean.getAction()) {
		case CREATE:
			save(dto);
			messages.add(Message.success(String.format(CREATE_SUCCESS, dto.getBonCommandeCode())));
			messages.addAll(ligneService.synchLignes(bean));
			break;

		case UPDATE:
			save(dto);
			messages.add(Message.success(String.format(UPDATE_SUCCESS, dto.getBonCommandeCode())));
			messages.addAll(ligneService.synchLignes(bean));
			break;

		case DELETE:
			messages.addAll(ligneService.synchLignes(bean));
			delete(dto);
			messages.add(Message.success(String.format(DELETE_SUCCESS, dto.getBonCommandeCode())));
			break;
		default:
			messages = Collections.singletonList(Message.warn("Aucune action à effectuer"));
		}
		
		return Map.of(ID_MODEL_KEY , id, MESSAGES_MODEL_KEY, messages);
	}

	public BonCommandeBean initEditFormBean(String bonCommandeCode) {
		var dto = dao.findById(bonCommandeCode).orElseThrow(dtoNotFoundEx(bonCommandeCode));
		var bean = mapper.mapToBean(dto, Map.of(OPTION_MAP_LIGNE_KEY, true, OPTION_MAP_PLUS_KEY, true ));
		return applyRules(bean, "init-edit-form");
	}
	
	public BonCommandeBean initDeleteFormBean(String bonCommandeCode) {
		var bean = dao.findById(bonCommandeCode).map(mapper::mapToBean)
				.orElseThrow(dtoNotFoundEx(bonCommandeCode));
		bean.setAction(EditActionEnumVd.DELETE);
		return applyRules(bean, "init-delete-form");
	}
	
	public BonCommandeBean initChangeStatusFormBean(String bonCommandeCode) {
		var bean = dao.findById(bonCommandeCode).map(mapper::mapToBean)
				.orElseThrow(dtoNotFoundEx(bonCommandeCode));
		bean.setAction(EditActionEnumVd.CHANGE_STATUS);
		bean.getStatusDate().setValue(LocalDateTime.now());
		return applyRules(bean, "init-delete-form");
	}

	public BonCommandeBean initCreateFormBean(Optional<String> copyFrom) {
		var dto = copyFrom.map(dao::findById).flatMap(e -> e).orElseGet(BonCommandeDto::new);
		var bean = mapper.mapToBean(dto, Map.of(OPTION_MAP_LIGNE_KEY, true, OPTION_MAP_PLUS_KEY, true));
		bean.initForCreateForm();
		return applyRules(bean, "init-create-form");
	}

	public BonCommandeSearchBean initSearchFormBean() {
		return applyRules(mapper.mapToSearchBean(), "init-search-form");
	}
	
	private Supplier<RuntimeException> dtoNotFoundEx(String bonCommandeCode){
		return () -> new RuntimeException(String.format(DTO_NOT_FOUND, bonCommandeCode));	
	}

}
