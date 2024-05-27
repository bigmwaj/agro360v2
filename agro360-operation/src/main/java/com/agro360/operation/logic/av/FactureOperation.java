package com.agro360.operation.logic.av;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.av.EtatDetteBean;
import com.agro360.bo.bean.av.FactureBean;
import com.agro360.bo.bean.av.FactureSearchBean;
import com.agro360.bo.mapper.AchatVenteMapper;
import com.agro360.dao.av.ICommandeDao;
import com.agro360.dao.av.IFactureDao;
import com.agro360.dao.common.IDao;
import com.agro360.dao.core.ICodeGeneratorDao;
import com.agro360.dao.core.IPartnerDao;
import com.agro360.dto.av.FactureDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;
import com.agro360.vd.av.FactureTypeEnumVd;

@Service("av/FactureService")
public class FactureOperation extends AbstractOperation<FactureDto, String> {

	@Autowired
	private IFactureDao dao;
	
	@Autowired
	private IPartnerDao partnerDao;
	
	@Autowired
	private ICommandeDao commandeDao;
	
	@Autowired
	private ICodeGeneratorDao codeGeneratorDao;
	
	public String generateFactureCode() {
		return codeGeneratorDao.generateFactureCode();
	}
	
	@Override
	protected IDao<FactureDto, String> getDao() {
		return dao;
	}

	public List<FactureBean> findFacturesByCriteria(ClientContext ctx, FactureSearchBean searchBean) {
		var code = searchBean.getNullOrUpperCase(searchBean::getFactureCode);
		var type = searchBean.getType().getValue();
		var debut = searchBean.getDateDebut().getValue();
		var fin = searchBean.getDateFin().getValue();
		var partner = searchBean.getNullOrUpperCase(searchBean::getPartner);
		
		var status = searchBean.getStatusIn().getValue();
		if( status != null && status.isEmpty() ) {
			status = null;
		}
				
		var length = dao.countFacturesByCriteria(code, type, debut, fin, status, partner);
        searchBean.setLength(length);
        return dao.findFacturesByCriteria(searchBean.getOffset(), searchBean.getLimit(), 
        		code, type, debut, fin, status, partner)
        		.stream().map(AchatVenteMapper::map)
        		.collect(Collectors.toList());
		
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
		setDtoValue(dto::setPrixTotal, bean.getPrixTotal());
		setDtoValue(dto::setPrixTotalHT, bean.getPrixTotalHT());
		setDtoValue(dto::setTaxe, bean.getTaxe());
		setDtoValue(dto::setCumulPaiement, bean.getCumulPaiement());
		setDtoValue(dto::setRemise, bean.getRemise());
		
		super.save(ctx, dto);	
	}

	@RuleNamespace("av/facture/update")
	public void updateFacture(ClientContext ctx, FactureBean bean) {
		var dto = dao.getReferenceById(bean.getFactureCode().getValue());
		
		setDtoChangedValue(dto::setDate, bean.getDate());
		setDtoChangedValue(dto::setDescription, bean.getDescription());
		setDtoChangedValue(dto::setPrixTotal, bean.getPrixTotal());
		setDtoChangedValue(dto::setPrixTotalHT, bean.getPrixTotalHT());
		setDtoChangedValue(dto::setTaxe, bean.getTaxe());
		setDtoChangedValue(dto::setCumulPaiement, bean.getCumulPaiement());
		setDtoChangedValue(dto::setRemise, bean.getRemise());
	}

	@RuleNamespace("av/facture/delete")
	public void deleteFacture(ClientContext ctx, FactureBean bean) {
		var dto = dao.getReferenceById(bean.getFactureCode().getValue());
		dao.delete(dto);
	}

	public FactureBean findFactureByCode(ClientContext ctx, String factureCode) {
		var dto = dao.getReferenceById(factureCode);
		return AchatVenteMapper.map(dto);	
	}
	
	private void changeFactureStatus(ClientContext ctx, FactureBean bean) {
		var dto = dao.getReferenceById(bean.getFactureCode().getValue());
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setStatusDate, bean.getStatusDate());
		dto = super.save(ctx, dto);		
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
	
	@RuleNamespace("av/facture/solder")
	public void solderFacture(ClientContext ctx, FactureBean bean) {
		changeFactureStatus(ctx, bean);	
	}

	public List<FactureBean> findFacturesByCommandeCode(ClientContext ctx, String commandeCode) {
		return dao.findAllByCommandeCommandeCode(commandeCode).stream()
				.map(AchatVenteMapper::map)
				.collect(Collectors.toList());
	}

	@RuleNamespace("av/facture/init-paiement")
	public void initPaiementFacture(ClientContext ctx, FactureBean bean) {
		changeFactureStatus(ctx, bean);
		var msgTpl = "Initialisation du paiement de la facture %s effectuée avec succès";
		ctx.success(String.format(msgTpl, bean.getFactureCode().getValue()));
	}
	
	public List<EtatDetteBean> genererEtatDette(ClientContext ctx) {
		Function<FactureTypeEnumVd, EtatDetteBean> calculer;
		calculer = t -> new EtatDetteBean(t, dao.calculerDettes(t));
		return Arrays.stream(FactureTypeEnumVd.values())
				.map(calculer)
				.collect(Collectors.toList());
	}

}
