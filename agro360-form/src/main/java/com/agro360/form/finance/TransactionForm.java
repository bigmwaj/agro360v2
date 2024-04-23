package com.agro360.form.finance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.core.PartnerSearchBean;
import com.agro360.bo.bean.finance.CompteBean;
import com.agro360.bo.bean.finance.CompteSearchBean;
import com.agro360.bo.bean.finance.RubriqueBean;
import com.agro360.bo.bean.finance.RubriqueSearchBean;
import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.bo.bean.finance.TransactionSearchBean;
import com.agro360.bo.bean.finance.TransfertBean;
import com.agro360.bo.mapper.FinanceMapper;
import com.agro360.bo.utils.Constants;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.core.PartnerOperation;
import com.agro360.operation.logic.finance.CompteOperation;
import com.agro360.operation.logic.finance.RubriqueOperation;
import com.agro360.operation.logic.finance.TransactionOperation;
import com.agro360.operation.metadata.BeanMetadataConfig;
import com.agro360.vd.common.ClientOperationEnumVd;
import com.agro360.vd.finance.TransactionStatusEnumVd;
import com.agro360.vd.finance.TransactionTypeEnumVd;

@Component
public class TransactionForm {

	@Autowired
	TransactionOperation operation;	
	
	@Autowired
	PartnerOperation partnerOperation;
	
	@Autowired
	RubriqueOperation rubriqueOperation;
	
	@Autowired
	CompteOperation compteOperation;
	
	@Qualifier("finance/transaction")
	@Autowired
	private BeanMetadataConfig metadataConfig;
	
	@Qualifier("finance/transaction/transfert")
	@Autowired
	private BeanMetadataConfig metadataConfig2;
	
	public TransactionBean initCreateFormBean(ClientContext ctx, TransactionTypeEnumVd type, Optional<String> copyFrom) {
		var bean = copyFrom.map(e -> operation.findTransactionByCode(ctx, e))
				.orElse(new TransactionBean());
		
		bean.setAction(ClientOperationEnumVd.CREATE);
		bean.getTransactionCode().setValue(operation.generateTransactionCode());
		bean.getDate().setValue(LocalDate.now());
		bean.getStatus().setValue(TransactionStatusEnumVd.ENCOURS);
		bean.getType().setValue(type);
		
		initPartnerOption(ctx, bean.getPartner().getPartnerCode()::setValueOptions);
		
		initRubriqueOption(ctx, type, bean.getRubrique().getRubriqueCode()::setValueOptions);
		
		initCompteOption(ctx, bean.getCompte().getCompteCode()::setValueOptions);
		
		metadataConfig.applyMetadata(ctx, bean);
		return bean;
	}
	
	public TransactionBean initEditFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findTransactionByCode(ctx, partnerCode);
		
		bean.setAction(ClientOperationEnumVd.UPDATE);
		var type = bean.getType().getValue();
		
		initPartnerOption(ctx, bean.getPartner().getPartnerCode()::setValueOptions);
		initRubriqueOption(ctx, type, bean.getRubrique().getRubriqueCode()::setValueOptions);
		initCompteOption(ctx, bean.getCompte().getCompteCode()::setValueOptions);
		
		metadataConfig.applyMetadata(ctx, bean);
		
		return bean;
	}

	public TransactionBean initDeleteFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findTransactionByCode(ctx, partnerCode);
		
		bean.setAction(ClientOperationEnumVd.DELETE);
		metadataConfig.applyMetadata(ctx, bean);
		return bean;
	}

	public TransactionBean initChangeStatusFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findTransactionByCode(ctx, partnerCode);
		
		bean.setAction(ClientOperationEnumVd.CHANGE_STATUS);
		
		bean.getStatusDate().setValue(LocalDateTime.now());
		bean.getStatusDate().setRequired(true);
		
		metadataConfig.applyMetadata(ctx, bean);
		return bean;
	}

	public TransactionSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = FinanceMapper.buildTransactionSearchBean();
		return bean;
	}
	
	public TransfertBean initTransfertFormBean(ClientContext ctx) {
		var bean = new TransfertBean();
		
		bean.setAction(ClientOperationEnumVd.CREATE);
		
		bean.getDate().setValue(LocalDate.now());
		
		initPartnerOption(ctx, bean.getPartner().getPartnerCode()::setValueOptions);
		
		initCompteOption(ctx, bean.getCompteSource().getCompteCode()::setValueOptions);
		
		initCompteOption(ctx, bean.getCompteCible().getCompteCode()::setValueOptions);
		
		metadataConfig2.applyMetadata(ctx, bean);
		
		return bean;
	}
	
	private void initPartnerOption(ClientContext ctx, Consumer<Map<Object, String>> valueOptionsSetter) {
		Function<PartnerBean, Object> codeFn = e -> e.getPartnerCode().getValue();
		Function<PartnerBean, String> libelleFn = Constants.PARTNER_BEAN2STR;		
		
		var options = partnerOperation.findPartnersByCriteria(ctx, new PartnerSearchBean())
				.stream().collect(Collectors.toMap(codeFn, libelleFn));

		valueOptionsSetter.accept(options);
	}
	
	private void initRubriqueOption(ClientContext ctx, TransactionTypeEnumVd type, Consumer<Map<Object, String>> valueOptionsSetter) {
		
		Function<RubriqueBean, Object> codeFn = e -> e.getRubriqueCode().getValue();
		Function<RubriqueBean, String> libelleFn = e -> e.getNom().getValue();
		var criteria = new RubriqueSearchBean();
		criteria.getType().setValue(type);
		
		var options = rubriqueOperation.findRubriquesByCriteria(ctx, criteria)
				.stream().collect(Collectors.toMap(codeFn, libelleFn));
		valueOptionsSetter.accept(options);
	}
	
	private void initCompteOption(ClientContext ctx, Consumer<Map<Object, String>> valueOptionsSetter) {
		Function<CompteBean, Object> codeFn = e -> e.getCompteCode().getValue();
		Function<CompteBean, String> libelleFn = e -> e.getDescription().getValue();
		
		var options = compteOperation.findComptesByCriteria(ctx, new CompteSearchBean())
				.stream().collect(Collectors.toMap(codeFn, libelleFn));

		valueOptionsSetter.accept(options);
	}

}
