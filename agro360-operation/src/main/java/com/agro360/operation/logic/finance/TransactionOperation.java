package com.agro360.operation.logic.finance;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.finance.EtatRecetteDepenseBean;
import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.bo.bean.finance.TransactionSearchBean;
import com.agro360.bo.bean.finance.TransfertBean;
import com.agro360.bo.mapper.FinanceMapper;
import com.agro360.dao.common.IDao;
import com.agro360.dao.core.ICodeGeneratorDao;
import com.agro360.dao.core.IPartnerDao;
import com.agro360.dao.finance.ICompteDao;
import com.agro360.dao.finance.IRubriqueDao;
import com.agro360.dao.finance.ITransactionDao;
import com.agro360.dto.finance.TransactionDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;
import com.agro360.vd.finance.TransactionStatusEnumVd;
import com.agro360.vd.finance.TransactionTypeEnumVd;

@Service
public class TransactionOperation extends AbstractOperation<TransactionDto, String> {

	@Autowired
	private ITransactionDao dao;

	@Autowired
	private IPartnerDao partnerDao;
	
	@Autowired
	private IRubriqueDao rubriqueDao;
	
	@Autowired
	private ICompteDao compteDao;
	
	@Autowired
	private ICodeGeneratorDao codeGeneratorDao;
	
	public String generateTransactionCode() {
		return codeGeneratorDao.generateTransactionCode();
	}

	@Override
	protected IDao<TransactionDto, String> getDao() {
		return dao;
	}

	@RuleNamespace("finance/transaction/create")
	public void createTransaction(ClientContext ctx, TransactionBean bean) {
		var dto = new TransactionDto();
		
		var compteCode = bean.getCompte().getCompteCode().getValue();
		var compte = compteDao.getReferenceById(compteCode);
		dto.setCompte(compte);
		
		var rubriqueCode = bean.getRubrique().getRubriqueCode().getValue();
		var rubrique = rubriqueDao.getReferenceById(rubriqueCode);
		dto.setRubrique(rubrique);
		
		var partnerCode = bean.getPartner().getPartnerCode().getValue();
		var partner = partnerDao.getReferenceById(partnerCode);
		dto.setPartner(partner);
		
		setDtoValue(dto::setTransactionCode, bean.getTransactionCode());
		setDtoValue(dto::setType, bean.getType());
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setNote, bean.getNote());
		setDtoValue(dto::setDate, bean.getDate());
		setDtoValue(dto::setMontant, bean.getMontant());
		
		super.save(ctx, dto);	
		var msgTpl = "Transaction %s créée avec succès";
		ctx.success(String.format(msgTpl, bean.getTransactionCode().getValue()));
	}

	@RuleNamespace("finance/transaction/update")
	public void updateTransaction(ClientContext ctx, TransactionBean bean) {
		var dto = dao.getReferenceById(bean.getTransactionCode().getValue());

		setDtoChangedValue(dto::setNote, bean.getNote());
		
		super.save(ctx, dto);

		var msgTpl = "Transaction %s modifiée avec succès";
		ctx.success(String.format(msgTpl, bean.getTransactionCode().getValue()));
	}
	
	private void changeTransactionStatus(ClientContext ctx, TransactionBean bean) {
		var dto = dao.getReferenceById(bean.getTransactionCode().getValue());
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setStatusDate, bean.getStatusDate());
		dto = super.save(ctx, dto);		
	}

	@RuleNamespace("finance/transaction/cloturer")
	public void clotureTransaction(ClientContext ctx, TransactionBean bean) {
		changeTransactionStatus(ctx, bean);	
		
		var msgTpl = "Transaction %s cloturée avec succès";
		ctx.success(String.format(msgTpl, bean.getTransactionCode().getValue()));
	}

	@RuleNamespace("finance/transaction/annuler")
	public void annulerTransaction(ClientContext ctx, TransactionBean bean) {
		changeTransactionStatus(ctx, bean);	
		
		var msgTpl = "Transaction %s annulée avec succès";
		ctx.success(String.format(msgTpl, bean.getTransactionCode().getValue()));
	}

	@RuleNamespace("finance/transaction/approuver")
	public void approuverTransaction(ClientContext ctx, TransactionBean bean) {
		changeTransactionStatus(ctx, bean);	
		
		var msgTpl = "Transaction %s approuvée avec succès";
		ctx.success(String.format(msgTpl, bean.getTransactionCode().getValue()));
	}

	public TransactionBean findTransactionByCode(ClientContext ctx, String transactionCode) {
		var dto = dao.getReferenceById(transactionCode);
		return FinanceMapper.map(dto);	
	}
	
	public List<TransactionBean> findTransactionsByCriteria(ClientContext ctx, TransactionSearchBean searchBean) {
		
		var code = searchBean.getTransactionCode().getValue();
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
		
		var rubrique = searchBean.getRubrique().getValue();
		var status = searchBean.getStatusIn().getValue();
		if( status != null && status.isEmpty() ) {
			status = null;
		}
		var length = dao.countTransactionsByCriteria(code, type, debut, fin, status, partner, compte, rubrique);
        searchBean.setLength(length);
        return dao.findTransactionsByCriteria(searchBean.getOffset(), searchBean.getLimit(), 
        		code, type, debut, fin, status, partner, compte, rubrique)
        		.stream().map(FinanceMapper::map)
        		.collect(Collectors.toList());
	}

	@RuleNamespace("finance/transaction/transfert")
	public List<TransactionBean> transfert(ClientContext ctx, TransfertBean bean) {
		List<TransactionBean> result = new ArrayList<>();
		result.add(transfertOut(ctx, bean));
		result.add(transfertIn(ctx, bean));
		
		return result;
	}
	
	private TransactionDto initTransfertDto(ClientContext ctx, TransfertBean bean, TransactionTypeEnumVd type) {
		var dto = new TransactionDto();
		
		var compteCode = bean.getCompteSource().getCompteCode().getValue();
		var compte = compteDao.getReferenceById(compteCode);
		dto.setCompte(compte);
		
		var sens = TransactionTypeEnumVd.RETRAIT.equals(type) ? "OUT": "IN";
		
		var rubriqueCode = "TRANSFERT/" + sens;
		var rubrique = rubriqueDao.getReferenceById(rubriqueCode);
		dto.setRubrique(rubrique);
		
		var PartnerCode = bean.getPartner().getPartnerCode().getValue();
		var Partner = partnerDao.getReferenceById(PartnerCode);
		dto.setPartner(Partner);
		
		dto.setStatus(TransactionStatusEnumVd.APPROUVEE);
		dto.setType(type);
		
		setDtoValue(dto::setNote, bean.getNote());
		setDtoValue(dto::setDate, bean.getDate());
		setDtoValue(dto::setMontant, bean.getMontant());
		
		return dto;	
	}
	
	private TransactionBean transfertOut(ClientContext ctx, TransfertBean bean) {
		var dto = initTransfertDto(ctx, bean, TransactionTypeEnumVd.RETRAIT);
		
		var compteCode = bean.getCompteSource().getCompteCode().getValue();
		var compte = compteDao.getReferenceById(compteCode);
		dto.setCompte(compte);
		
		dto.setTransactionCode(generateTransactionCode());
		
		dto = super.save(ctx, dto);	
		
		return FinanceMapper.map(dto);
	}

	private TransactionBean transfertIn(ClientContext ctx, TransfertBean bean) {
		var dto = initTransfertDto(ctx, bean, TransactionTypeEnumVd.DEPOT);
		
		var compteCode = bean.getCompteCible().getCompteCode().getValue();
		var compte = compteDao.getReferenceById(compteCode);
		dto.setCompte(compte);
		
		dto.setTransactionCode(generateTransactionCode());
		
		dto = super.save(ctx, dto);	
		
		return FinanceMapper.map(dto);
	}

	public List<EtatRecetteDepenseBean> genererEtatRecetteDepense(ClientContext ctx, LocalDate semaine) {
		var end = semaine.plusDays(14-semaine.getDayOfWeek().getValue());
		var start = end.minusDays(28);
		
		var etats = new HashMap<LocalDate, EtatRecetteDepenseBean>(4);
		for (var i = 0; i < 4; i++) {
			var curr = start.plusDays(7 * i);
			etats.put(curr, new EtatRecetteDepenseBean(curr));			
		}
		
		var depenses =  dao.calculerCumul(TransactionTypeEnumVd.DEPENSE.name(), start, end);
		var recettes =  dao.calculerCumul(TransactionTypeEnumVd.RECETTE.name(), start, end);
		
		for (var etat : depenses) {
			Date s = (Date) etat.get("semaine");
			BigDecimal m = (BigDecimal) etat.get("montant");
			var bean = etats.get(s.toLocalDate());
			bean.getDepense().setValue(m);
		}
//		
		for (var etat : recettes) {
			Date s = (Date) etat.get("semaine");
			BigDecimal m = (BigDecimal) etat.get("montant");
			var bean = etats.get(s.toLocalDate());
			bean.getRecette().setValue(m);
		}
		
		Comparator<EtatRecetteDepenseBean> comparator;
		comparator = (a, b) -> a.getSemaine().getValue().compareTo(b.getSemaine().getValue());
		return etats.values().stream().sorted(comparator).collect(Collectors.toList());
	}
}
