package com.agro360.operation.logic.finance;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

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
		
		var PartnerCode = bean.getPartner().getPartnerCode().getValue();
		var Partner = partnerDao.getReferenceById(PartnerCode);
		dto.setPartner(Partner);
		
		setDtoValue(dto::setTransactionCode, bean.getTransactionCode());
		setDtoValue(dto::setType, bean.getType());
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setNote, bean.getNote());
		setDtoValue(dto::setDate, bean.getDate());
		setDtoValue(dto::setMontant, bean.getMontant());
		
		dto = super.save(dto);	
	}

	@RuleNamespace("finance/transaction/update")
	public void updateTransaction(ClientContext ctx, TransactionBean bean) {
		var dto = dao.getReferenceById(bean.getTransactionCode().getValue());

		setDtoChangedValue(dto::setNote, bean.getNote());
		setDtoChangedValue(dto::setMontant, bean.getMontant());
		setDtoChangedValue(dto::setDate, bean.getDate());
		
		dto = super.save(dto);		
	}
	
	@RuleNamespace("finance/transaction/delete")
	public void deleteTransaction(ClientContext ctx, TransactionBean bean) {
		var dto = dao.getReferenceById(bean.getTransactionCode().getValue());
		dao.delete(dto);
	}
	
	private void changeTransactionStatus(ClientContext ctx, TransactionBean bean) {
		var dto = dao.getReferenceById(bean.getTransactionCode().getValue());
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setStatusDate, bean.getStatusDate());
		dto = super.save(dto);		
	}

	@RuleNamespace("finance/transaction/cloturer")
	public void clotureTransaction(ClientContext ctx, TransactionBean bean) {
		changeTransactionStatus(ctx, bean);	
	}

	@RuleNamespace("finance/transaction/annuler")
	public void annulerTransaction(ClientContext ctx, TransactionBean bean) {
		changeTransactionStatus(ctx, bean);	
	}

	@RuleNamespace("finance/transaction/approuver")
	public void approuverTransaction(ClientContext ctx, TransactionBean bean) {
		changeTransactionStatus(ctx, bean);	
	}

	public TransactionBean findTransactionByCode(ClientContext ctx, String transactionCode) {
		var dto = dao.getReferenceById(transactionCode);
		return FinanceMapper.map(dto);	
	}
	
	public List<TransactionBean> findTransactionsByCriteria(ClientContext ctx, TransactionSearchBean searchBean) {
		var example = Example.of(new TransactionDto());
		if( searchBean.getTransactionCode().getValue() != null ) {
			example.getProbe().setTransactionCode(searchBean.getTransactionCode().getValue());
		}
		if( searchBean.getType().getValue() != null ) {
			example.getProbe().setType(searchBean.getType().getValue());
		}
		return dao.findAll(example).stream().map(FinanceMapper::map).collect(Collectors.toList());
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
		
		dto = super.save(dto);	
		
		return FinanceMapper.map(dto);
	}

	private TransactionBean transfertIn(ClientContext ctx, TransfertBean bean) {
		var dto = initTransfertDto(ctx, bean, TransactionTypeEnumVd.DEPOT);
		
		var compteCode = bean.getCompteCible().getCompteCode().getValue();
		var compte = compteDao.getReferenceById(compteCode);
		dto.setCompte(compte);
		
		dto.setTransactionCode(generateTransactionCode());
		
		dto = super.save(dto);	
		
		return FinanceMapper.map(dto);
	}
}
