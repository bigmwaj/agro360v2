package com.agro360.form.finance;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.agro360.bo.mapper.finance.TransactionMapper;
import com.agro360.bo.utils.Constants;
import com.agro360.dto.finance.TransactionDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.core.PartnerOperation;
import com.agro360.operation.logic.finance.CompteOperation;
import com.agro360.operation.logic.finance.RubriqueOperation;
import com.agro360.operation.logic.finance.TransactionOperation;
import com.agro360.vd.common.EditActionEnumVd;
import com.agro360.vd.finance.TransactionStatusEnumVd;

@Component
public class TransactionForm {

	@Autowired
	TransactionMapper mapper;

	@Autowired
	TransactionOperation operation;	
	
	@Autowired
	PartnerOperation partnerService;
	
	@Autowired
	RubriqueOperation rubriqueService;
	
	@Autowired
	CompteOperation compteService;
	
	public TransactionBean initCreateFormBean(ClientContext ctx, Optional<String> copyFrom) {
		var bean = copyFrom.map(e -> operation.findTransactionByCode(ctx, e))
				.orElse(mapper.map(new TransactionDto()));
		
		bean.setAction(EditActionEnumVd.CREATE);
		
		bean.getStatus().setValue(TransactionStatusEnumVd.ENCOURS);
		bean.getStatus().setEditable(false);
		
		bean.getTransactionCode().setRequired(true);
		bean.getTransactionCode().setValue(operation.generateTransactionCode());
		
		bean.getType().setRequired(true);
		
		bean.getDate().setValue(LocalDate.now());
		bean.getDate().setRequired(true);
		
		bean.getMontant().setRequired(true);
		
		initPartnerOption(ctx, bean.getPartner().getPartnerCode()::setValueOptions);
		bean.getPartner().getPartnerCode().setRequired(true);
		
		initRubriqueOption(ctx, bean.getRubrique().getRubriqueCode()::setValueOptions);
		bean.getRubrique().getRubriqueCode().setRequired(true);
		
		initCompteOption(ctx, bean.getCompte().getCompteCode()::setValueOptions);
		bean.getCompte().getCompteCode().setRequired(true);
		return bean;
	}
	
	public TransactionBean initEditFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findTransactionByCode(ctx, partnerCode);
		
		bean.setAction(EditActionEnumVd.UPDATE);
		
		bean.getTransactionCode().setRequired(true);
		bean.getTransactionCode().setEditable(false);
		
		bean.getType().setRequired(true);
		bean.getType().setEditable(false);
		
		bean.getDate().setRequired(true);
		
		bean.getMontant().setRequired(true);
		
		bean.getStatus().setEditable(false);
		
		initPartnerOption(ctx, bean.getPartner().getPartnerCode()::setValueOptions);
		bean.getPartner().getPartnerCode().setEditable(false);
		
		initRubriqueOption(ctx, bean.getRubrique().getRubriqueCode()::setValueOptions);
		bean.getRubrique().getRubriqueCode().setEditable(false);
		
		initCompteOption(ctx, bean.getCompte().getCompteCode()::setValueOptions);
		bean.getCompte().getCompteCode().setEditable(false);
		
		return bean;
	}

	public TransactionBean initDeleteFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findTransactionByCode(ctx, partnerCode);
		
		bean.setAction(EditActionEnumVd.DELETE);
		
		bean.getTransactionCode().setRequired(true);
		return bean;
	}

	public TransactionBean initChangeStatusFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findTransactionByCode(ctx, partnerCode);
		
		bean.setAction(EditActionEnumVd.CHANGE_STATUS);
		
		bean.getTransactionCode().setRequired(true);
		bean.getTransactionCode().setEditable(false);
		
		bean.getStatus().setRequired(true);
		
		bean.getType().setEditable(false);
		
		bean.getStatusDate().setValue(LocalDateTime.now());
		bean.getStatusDate().setRequired(true);
		return bean;
	}

	public TransactionSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = mapper.mapToSearchBean();
		return bean;
	}
	
	public TransfertBean initTransfertFormBean(ClientContext ctx) {
		var bean = new TransfertBean();
		
		bean.setAction(EditActionEnumVd.CREATE);
		
		bean.getDate().setValue(LocalDate.now());
		bean.getDate().setRequired(true);
		
		bean.getMontant().setRequired(true);
		
		initPartnerOption(ctx, bean.getPartner().getPartnerCode()::setValueOptions);
		bean.getPartner().getPartnerCode().setRequired(true);
		
		initCompteOption(ctx, bean.getCompteSource().getCompteCode()::setValueOptions);
		bean.getCompteSource().getCompteCode().setRequired(true);
		
		initCompteOption(ctx, bean.getCompteCible().getCompteCode()::setValueOptions);
		bean.getCompteCible().getCompteCode().setRequired(true);
		
		return bean;
	}
	
	private void initPartnerOption(ClientContext ctx, Consumer<Map<Object, String>> valueOptionsSetter) {
		Function<PartnerBean, Object> codeFn = e -> e.getPartnerCode().getValue();
		Function<PartnerBean, String> libelleFn = Constants.PARTNER_BEAN2STR;		
		
		var options = partnerService.findPartnerByCriteria(ctx, new PartnerSearchBean())
				.stream().collect(Collectors.toMap(codeFn, libelleFn));

		valueOptionsSetter.accept(options);
	}
	
	private void initRubriqueOption(ClientContext ctx, Consumer<Map<Object, String>> valueOptionsSetter) {
		
		Function<RubriqueBean, Object> codeFn = e -> e.getRubriqueCode().getValue();
		Function<RubriqueBean, String> libelleFn = e -> e.getNom().getValue();
		
		var options = rubriqueService.findRubriquesByCriteria(ctx, new RubriqueSearchBean())
				.stream().collect(Collectors.toMap(codeFn, libelleFn));
		valueOptionsSetter.accept(options);
	}
	
	private void initCompteOption(ClientContext ctx, Consumer<Map<Object, String>> valueOptionsSetter) {
		Function<CompteBean, Object> codeFn = e -> e.getCompteCode().getValue();
		Function<CompteBean, String> libelleFn = e -> e.getDescription().getValue();
		
		var options = compteService.findComptesByCriteria(ctx, new CompteSearchBean())
				.stream().collect(Collectors.toMap(codeFn, libelleFn));

		valueOptionsSetter.accept(options);
	}

}
