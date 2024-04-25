package com.agro360.operation.logic.av;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.CommandeSearchBean;
import com.agro360.bo.mapper.AchatVenteMapper;
import com.agro360.dao.av.ICommandeDao;
import com.agro360.dao.common.IDao;
import com.agro360.dao.core.ICodeGeneratorDao;
import com.agro360.dao.core.IPartnerDao;
import com.agro360.dao.finance.ICompteDao;
import com.agro360.dao.stock.IMagasinDao;
import com.agro360.dto.av.CommandeDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

@Service
public class CommandeOperation extends AbstractOperation<CommandeDto, String> {

	@Autowired
	private ICommandeDao dao;

	@Autowired
	private IPartnerDao partnerDao;
	
	@Autowired
	private ICompteDao compteDao;
	
	@Autowired
	private IMagasinDao magasinDao;
	
	@Autowired
	private ICodeGeneratorDao codeGeneratorDao;

	public String generateCommandeCode() {
		return codeGeneratorDao.generateCommandeCode();
	}

	@Override
	protected IDao<CommandeDto, String> getDao() {
		return dao;
	}
	
	public List<CommandeBean> findCommandesByCriteria(ClientContext ctx, CommandeSearchBean searchBean) {
		var code = searchBean.getCommandeCode().getValue();
		if( code != null ) {
			code = code.toUpperCase();
		}
		
		var type = searchBean.getType().getValue();
		var debut = searchBean.getDateDebut().getValue();
		var fin = searchBean.getDateFin().getValue();
		var compte = searchBean.getCompte().getValue();
		
		var partner = searchBean.getPartner().getValue();
		if( partner != null ) {
			partner = partner.toUpperCase();
		}
		
		var ville = searchBean.getVille().getValue();
		if( ville != null ) {
			ville = ville.toUpperCase();
		}
		
		var status = searchBean.getStatusIn().getValue();
		if( status != null && status.isEmpty() ) {
			status = null;
		}
		
        return dao.findCommandesByCriteria(code, type, debut, fin, status, partner, compte, ville)
        		.stream().map(AchatVenteMapper::map)
        		.collect(Collectors.toList());
		
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
		setDtoValue(dto::setRemiseType, bean.getRemiseType());
		setDtoValue(dto::setRemiseTaux, bean.getRemiseTaux());
		setDtoValue(dto::setRemiseMontant, bean.getRemiseMontant());
		setDtoValue(dto::setRemiseRaison, bean.getRemiseRaison());
		setDtoValue(dto::setPrixTotal, bean.getPrixTotal());
		setDtoValue(dto::setPrixTotalHT, bean.getPrixTotalHT());
		setDtoValue(dto::setPrixTotalTTC, bean.getPrixTotalTTC());
		setDtoValue(dto::setTaxe, bean.getTaxe());
		setDtoValue(dto::setRemise, bean.getRemise());
		
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
		setDtoChangedValue(dto::setRemiseType, bean.getRemiseType());
		setDtoChangedValue(dto::setRemiseTaux, bean.getRemiseTaux());
		setDtoChangedValue(dto::setRemiseMontant, bean.getRemiseMontant());
		setDtoChangedValue(dto::setRemiseRaison, bean.getRemiseRaison());		

		setDtoChangedValue(dto::setPrixTotal, bean.getPrixTotal());
		setDtoChangedValue(dto::setPrixTotalHT, bean.getPrixTotalHT());
		setDtoChangedValue(dto::setPrixTotalTTC, bean.getPrixTotalTTC());
		setDtoChangedValue(dto::setTaxe, bean.getTaxe());
		setDtoChangedValue(dto::setRemise, bean.getRemise());
		
		super.save(dto);		
	}

	@RuleNamespace("av/commande/delete")
	public void deleteCommande(ClientContext ctx, CommandeBean bean) {
		var dto = dao.getReferenceById(bean.getCommandeCode().getValue());
		dao.delete(dto);
	}

	public CommandeBean findCommandeByCode(ClientContext ctx, String commandeCode) {
		var dto = dao.getReferenceById(commandeCode);
		return AchatVenteMapper.map(dto);	
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
