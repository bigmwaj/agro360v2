package com.agro360.operation.logic.av;

import java.math.BigDecimal;
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
		var code = getNullOrUpperCase(searchBean::getCommandeCode);
		var type = searchBean.getType().getValue();
		var debut = searchBean.getDateDebut().getValue();
		var fin = searchBean.getDateFin().getValue();
		var partner = getNullOrUpperCase(searchBean::getPartner);
		
		var status = searchBean.getStatusIn().getValue();
		if( status != null && status.isEmpty() ) {
			status = null;
		}
				
		var length = dao.countCommandesByCriteria(code, type, debut, fin, status, partner);
        searchBean.setLength(length);
        return dao.findCommandesByCriteria(searchBean.getOffset(), searchBean.getLimit(), 
        		code, type, debut, fin, status, partner)
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
		dto.setCumulPaiement(BigDecimal.ZERO);
		
		setDtoValue(dto::setCommandeCode, bean.getCommandeCode());
		setDtoValue(dto::setType, bean.getType());
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setDate, bean.getDate());
		setDtoValue(dto::setDescription, bean.getDescription());
		setDtoValue(dto::setPrixTotal, bean.getPrixTotal());
		setDtoValue(dto::setPrixTotalHT, bean.getPrixTotalHT());
		setDtoValue(dto::setTaxe, bean.getTaxe());
		setDtoValue(dto::setRemise, bean.getRemise());
		
		super.save(ctx, dto);	

		var msgTpl = "Commande %s créée avec succès";
		ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
	}

	@RuleNamespace("av/commande/update")
	public void updateCommande(ClientContext ctx, CommandeBean bean) {
		var dto = dao.getReferenceById(bean.getCommandeCode().getValue());

		setDtoChangedValue(dto::setDate, bean.getDate());
		setDtoChangedValue(dto::setDescription, bean.getDescription());
		setDtoChangedValue(dto::setCumulPaiement, bean.getCumulPaiement());
		setDtoChangedValue(dto::setPrixTotal, bean.getPrixTotal());
		setDtoChangedValue(dto::setPrixTotalHT, bean.getPrixTotalHT());
		setDtoChangedValue(dto::setTaxe, bean.getTaxe());
		setDtoChangedValue(dto::setRemise, bean.getRemise());
		
		super.save(ctx, dto);	
		
		var msgTpl = "Commande %s modifiée avec succès";
		ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
	}

	@RuleNamespace("av/commande/delete")
	public void deleteCommande(ClientContext ctx, CommandeBean bean) {
		var dto = dao.getReferenceById(bean.getCommandeCode().getValue());
		dao.delete(dto);
		
		var msgTpl = "Commande %s suprimée avec succès";
		ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
	}

	public CommandeBean findCommandeByCode(ClientContext ctx, String commandeCode) {
		var dto = dao.getReferenceById(commandeCode);
		return AchatVenteMapper.map(dto);	
	}
	
	private void changeCommandeStatus(ClientContext ctx, CommandeBean bean) {
		var dto = dao.getReferenceById(bean.getCommandeCode().getValue());
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setStatusDate, bean.getStatusDate());
		dto = super.save(ctx, dto);		
	}

	@RuleNamespace("av/commande/demander-approbation")
	public void demanderApprobationCommande(ClientContext ctx, CommandeBean bean) {
		changeCommandeStatus(ctx, bean);	
		var msgTpl = "Demande d'approbation de la commande %s envoyée avec succès";
		ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
	}
	
	@RuleNamespace("av/commande/terminer")
	public void terminerCommande(ClientContext ctx, CommandeBean bean) {
		changeCommandeStatus(ctx, bean);	
		var msgTpl = "Commande %s terminée avec succès";
		ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
	}
	
	@RuleNamespace("av/commande/demander-annulation")
	public void demanderAnnulationCommande(ClientContext ctx, CommandeBean bean) {
		changeCommandeStatus(ctx, bean);	
		var msgTpl = "Demande d'annulation de la commande %s envoyée avec succès";
		ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
	}
	
	@RuleNamespace("av/commande/annuler")
	public void annulerCommande(ClientContext ctx, CommandeBean bean) {
		changeCommandeStatus(ctx, bean);	
		var msgTpl = "Commande %s annulée avec succès";
		ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
	}
	
	@RuleNamespace("av/commande/cloturer")
	public void cloturerCommande(ClientContext ctx, CommandeBean bean) {
		changeCommandeStatus(ctx, bean);	
		var msgTpl = "Commande %s clôturée avec succès";
		ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
	}
	
	@RuleNamespace("av/commande/part-receptionner")
	public void receptionnerPartCommande(ClientContext ctx, CommandeBean bean) {
		changeCommandeStatus(ctx, bean);
		var msgTpl = "Commande %s partiellement receptionnée avec succès";
		ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
	}
	
	@RuleNamespace("av/commande/init-paiement")
	public void initPaiementCommande(ClientContext ctx, CommandeBean bean) {
		changeCommandeStatus(ctx, bean);
		var msgTpl = "Initialisation du paiement de la commande %s effectuée avec succès";
		ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
	}
	
	@RuleNamespace("av/commande/receptionner")
	public void receptionnerCommande(ClientContext ctx, CommandeBean bean) {
		changeCommandeStatus(ctx, bean);
		var msgTpl = "Commande %s totalement receptionnée avec succès";
		ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
	}

	@RuleNamespace("av/commande/approuver")
	public void approuverCommande(ClientContext ctx, CommandeBean bean) {
		changeCommandeStatus(ctx, bean);	
		var msgTpl = "Commande %s approuvée avec succès";
		ctx.success(String.format(msgTpl, bean.getCommandeCode().getValue()));
	}

}
