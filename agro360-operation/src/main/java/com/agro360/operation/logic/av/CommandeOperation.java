package com.agro360.operation.logic.av;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.CommandeSearchBean;
import com.agro360.bo.mapper.av.CommandeMapper;
import com.agro360.bo.mapper.core.PartnerMapper;
import com.agro360.bo.mapper.finance.CompteMapper;
import com.agro360.bo.mapper.stock.MagasinMapper;
import com.agro360.dao.av.ICommandeDao;
import com.agro360.dao.common.IDao;
import com.agro360.dao.core.ICodeGeneratorDao;
import com.agro360.dao.core.IPartnerDao;
import com.agro360.dao.finance.ICompteDao;
import com.agro360.dao.stock.IMagasinDao;
import com.agro360.dto.av.CommandeDto;
import com.agro360.dto.core.PartnerDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;

@Service("av/CommandeService")
public class CommandeOperation extends AbstractOperation<CommandeDto, String> {

	@Autowired
	private ICommandeDao dao;
	
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private CommandeMapper mapper;

	@Autowired
	private IPartnerDao partnerDao;
	
	@Autowired
	private ICompteDao compteDao;
	
	@Autowired
	private IMagasinDao magasinDao;
	
	@Autowired
	private ICodeGeneratorDao codeGeneratorDao;

	@Autowired
	private PartnerMapper partnerMapper;

	@Autowired
	private MagasinMapper magasinMapper;
	
	@Autowired
	CompteMapper compteMapper;
	
	public String generateCommandeCode() {
		return codeGeneratorDao.generateCommandeCode();
	}

	@Override
	protected IDao<CommandeDto, String> getDao() {
		return dao;
	}
	
	@Override
	protected String getRulePath() {
		return "achat/commande";
	}
	
	public CommandeSearchBean mapToSearchBean() {
		var bean = new CommandeSearchBean();
		return bean;
	}

	public CommandeBean map(CommandeDto dto) {
		var bean = new CommandeBean();

		bean.getCommandeCode().setValue(dto.getCommandeCode());
		bean.getDate().setValue(dto.getDate());
		bean.getStatus().setValue(dto.getStatus());
		bean.getDescription().setValue(dto.getDescription());
		bean.getType().setValue(dto.getType());
		bean.getPaiementComptant().setValue(dto.getPaiementComptant());
		
		if (null != dto.getPartner()) {
			bean.setPartner(partnerMapper.map(dto.getPartner()));
		}

		if (dto.getMagasin() != null) {
			bean.setMagasin(magasinMapper.map(dto.getMagasin()));
		}
		
		if( dto.getCompte() != null ) {
			bean.setCompte(compteMapper.map(dto.getCompte()));
		}

		return bean;
	}

	public List<CommandeBean> findCommandesByCriteria(ClientContext ctx, CommandeSearchBean searchBean) {
		var criteriaBuilder = entityManager.getCriteriaBuilder();
		
		var rootQuery = criteriaBuilder.createQuery(CommandeDto.class);
		var from = rootQuery.from(CommandeDto.class);
		
		List<Predicate> wherePredicates = new ArrayList<>();
		
		var code = searchBean.getCommandeCode().getValue();
		if( code != null && !code.isBlank() ) {
			code = '%' + code.toUpperCase() + '%';
			wherePredicates.add(criteriaBuilder.like(from.get("CommandeCode"), code));	
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
        return query.getResultList().stream().map(mapper::map).collect(Collectors.toList());
		
	}

	@RuleNamespace("av/commande/create")
	public void createCommande(ClientContext ctx, CommandeBean bean) {
		var dto = new CommandeDto();
		
		var partnerCode = bean.getPartner().getPartnerCode().getValue();
		var partner = partnerDao.getReferenceById(partnerCode);
		dto.setPartner(partner);
		
		var magasinCode = bean.getMagasin().getMagasinCode().getValue();
		var magasin = magasinDao.getReferenceById(magasinCode);
		dto.setMagasin(magasin);
		
		var compteCode = bean.getCompte().getCompteCode().getValue();
		if( compteCode != null ) {
			var compte = compteDao.getReferenceById(compteCode);
			dto.setCompte(compte);
		}
		
		setDtoValue(dto::setCommandeCode, bean.getCommandeCode());
		setDtoValue(dto::setType, bean.getType());
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setDate, bean.getDate());
		setDtoValue(dto::setDescription, bean.getDescription());
		setDtoValue(dto::setPaiementComptant, bean.getPaiementComptant());
		
		dto = super.save(dto);	
	}

	@RuleNamespace("av/commande/update")
	public void updateCommande(ClientContext ctx, CommandeBean bean) {
		var dto = dao.getReferenceById(bean.getCommandeCode().getValue());

		var compteCode = bean.getCompte().getCompteCode().getValue();
		if( compteCode != null && dto.getCompte() != null 
				&& !compteCode.equals(dto.getCompte().getCompteCode())) {
			var compte = compteDao.getReferenceById(compteCode);
			dto.setCompte(compte);
		}
		setDtoChangedValue(dto::setDate, bean.getDate());
		setDtoChangedValue(dto::setDescription, bean.getDescription());
		setDtoChangedValue(dto::setPaiementComptant, bean.getPaiementComptant());
		
		getLogger().debug("Le montant du paiement comptant est {}", dto.getPaiementComptant());
		super.save(dto);		
	}

	@RuleNamespace("av/commande/delete")
	public void deleteCommande(ClientContext ctx, CommandeBean bean) {
		var dto = dao.getReferenceById(bean.getCommandeCode().getValue());
		dao.delete(dto);
	}

	public CommandeBean findCommandeByCode(ClientContext ctx, String commandeCode) {
		var dto = dao.getReferenceById(commandeCode);
		return mapper.map(dto);	
	}
	
	private void changeCommandeStatus(ClientContext ctx, CommandeBean bean) {
		var dto = dao.getReferenceById(bean.getCommandeCode().getValue());
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setStatusDate, bean.getStatusDate());
		dto = super.save(dto);		
	}

	@RuleNamespace("av/commande/demander-approbation")
	public void demanderApprobationCommande(ClientContext ctx, CommandeBean bean) {
		changeCommandeStatus(ctx, bean);	
	}
	
	@RuleNamespace("av/commande/annuler")
	public void annulerCommande(ClientContext ctx, CommandeBean bean) {
		changeCommandeStatus(ctx, bean);	
	}
	
	@RuleNamespace("av/commande/rejeter")
	public void rejeterCommande(ClientContext ctx, CommandeBean bean) {
		changeCommandeStatus(ctx, bean);	
	}
	
	@RuleNamespace("av/commande/receptionner")
	public void receptionnerCommande(ClientContext ctx, CommandeBean bean) {
		changeCommandeStatus(ctx, bean);	
	}

	@RuleNamespace("av/commande/approuver")
	public void approuverCommande(ClientContext ctx, CommandeBean bean) {
		changeCommandeStatus(ctx, bean);	
	}

}
