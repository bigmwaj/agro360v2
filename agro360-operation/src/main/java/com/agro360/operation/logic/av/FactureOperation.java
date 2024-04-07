package com.agro360.operation.logic.av;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.av.FactureBean;
import com.agro360.bo.bean.av.FactureSearchBean;
import com.agro360.bo.mapper.av.FactureMapper;
import com.agro360.dao.av.ICommandeDao;
import com.agro360.dao.av.IFactureDao;
import com.agro360.dao.common.IDao;
import com.agro360.dao.core.ICodeGeneratorDao;
import com.agro360.dao.core.IPartnerDao;
import com.agro360.dto.av.FactureDto;
import com.agro360.dto.core.PartnerDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;

@Service("av/FactureService")
public class FactureOperation extends AbstractOperation<FactureDto, String> {

	@Autowired
	private IFactureDao dao;
	
	@Autowired
	private IPartnerDao partnerDao;
	
	@Autowired
	private ICommandeDao commandeDao;
	
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private FactureMapper mapper;
	
	@Autowired
	private ICodeGeneratorDao codeGeneratorDao;
	
	public String generateFactureCode() {
		return codeGeneratorDao.generateFactureCode();
	}
	
	@Override
	protected IDao<FactureDto, String> getDao() {
		return dao;
	}
	
	@Override
	protected String getRulePath() {
		return "achat/commande";
	}

	public List<FactureBean> findFacturesByCriteria(ClientContext ctx, FactureSearchBean searchBean) {
		var criteriaBuilder = entityManager.getCriteriaBuilder();
		
		var rootQuery = criteriaBuilder.createQuery(FactureDto.class);
		var from = rootQuery.from(FactureDto.class);
		
		List<Predicate> wherePredicates = new ArrayList<>();
		
		var code = searchBean.getFactureCode().getValue();
		if( code != null && !code.isBlank() ) {
			code = '%' + code.toUpperCase() + '%';
			wherePredicates.add(criteriaBuilder.like(from.get("FactureCode"), code));	
		}
		var status = searchBean.getStatusIn().getValue();
		if( status != null && !status.isEmpty() ) {
			wherePredicates.add(from.get("status").in(status));
		}

		var debut = searchBean.getDateDebut().getValue();
		if( debut != null ) {
			wherePredicates.add(criteriaBuilder.greaterThanOrEqualTo(from.get("date"), debut));	
		}

		var fin = searchBean.getDateFin().getValue();
		if( fin != null ) {
			wherePredicates.add(criteriaBuilder.lessThanOrEqualTo(from.get("date"), fin));	
		}

		var partner = searchBean.getPartner().getValue();
		if( partner != null && !partner.isBlank() ) {			
			partner = '%' + partner.toUpperCase() + '%';
			
			var subQuery = criteriaBuilder.createQuery(PartnerDto.class);
			var subQueryFrom = subQuery.from(PartnerDto.class);
			
			
			
			subQuery.select(subQueryFrom)
			  .distinct(true)
			  .where(criteriaBuilder.like(subQueryFrom.get("name"), partner));
			
			//wherePredicates.add(criteriaBuilder.in(from.get("partner")).value(subQuery.));
		}
		
		if( !wherePredicates.isEmpty() ) {
			rootQuery.where(wherePredicates.toArray(new Predicate[0]));
		}
		
		var query = entityManager.createQuery(rootQuery);
		System.out.println("FactureService.search() La requette est " + query);
        return query.getResultList().stream().map(mapper::map).collect(Collectors.toList());
		
	}

	@RuleNamespace("av/facture/create")
	public void createFacture(ClientContext ctx, FactureBean bean) {
		var dto = new FactureDto();
		
		var partnerCode = bean.getPartner().getPartnerCode().getValue();
		var partner = partnerDao.getReferenceById(partnerCode);
		dto.setPartner(partner);
		
		var commandeCode = bean.getCommande().getCommandeCode().getValue();
		if( commandeCode != null ) {
			var commande = commandeDao.getReferenceById(commandeCode);
			dto.setCommande(commande);
		}
		
		setDtoValue(dto::setFactureCode, bean.getFactureCode());
		setDtoValue(dto::setType, bean.getType());
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setDate, bean.getDate());
		setDtoValue(dto::setDescription, bean.getDescription());
		setDtoValue(dto::setMontant, bean.getMontant());
		
		super.save(dto);	
	}

	@RuleNamespace("av/facture/update")
	public void updateFacture(ClientContext ctx, FactureBean bean) {
		var dto = dao.getReferenceById(bean.getFactureCode().getValue());
		
		setDtoChangedValue(dto::setDate, bean.getDate());
		setDtoChangedValue(dto::setDescription, bean.getDescription());
		setDtoChangedValue(dto::setMontant, bean.getMontant());
	}

	@RuleNamespace("av/facture/delete")
	public void deleteFacture(ClientContext ctx, FactureBean bean) {
		var dto = dao.getReferenceById(bean.getFactureCode().getValue());
		dao.delete(dto);
	}

	public FactureBean findFactureByCode(ClientContext ctx, String commandeCode) {
		var dto = dao.getReferenceById(commandeCode);
		return mapper.map(dto);	
	}
	
	private void changeFactureStatus(ClientContext ctx, FactureBean bean) {
		var dto = dao.getReferenceById(bean.getFactureCode().getValue());
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setStatusDate, bean.getStatusDate());
		dto = super.save(dto);		
	}

	@RuleNamespace("av/facture/demander-approbation")
	public void demanderApprobationFacture(ClientContext ctx, FactureBean bean) {
		changeFactureStatus(ctx, bean);	
	}
	
	@RuleNamespace("av/facture/annuler")
	public void annulerFacture(ClientContext ctx, FactureBean bean) {
		changeFactureStatus(ctx, bean);	
	}
	
	@RuleNamespace("av/facture/rejeter")
	public void rejeterFacture(ClientContext ctx, FactureBean bean) {
		changeFactureStatus(ctx, bean);	
	}
	
	@RuleNamespace("av/facture/receptionner")
	public void cloturerFacture(ClientContext ctx, FactureBean bean) {
		changeFactureStatus(ctx, bean);	
	}

	@RuleNamespace("av/facture/approuver")
	public void approuverFacture(ClientContext ctx, FactureBean bean) {
		changeFactureStatus(ctx, bean);	
	}

}
