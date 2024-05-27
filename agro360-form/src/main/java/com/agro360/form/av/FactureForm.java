package com.agro360.form.av;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.FactureBean;
import com.agro360.bo.bean.av.FactureSearchBean;
import com.agro360.bo.bean.av.PaiementParamBean;
import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.core.PartnerSearchBean;
import com.agro360.bo.bean.finance.CompteSearchBean;
import com.agro360.bo.mapper.AchatVenteMapper;
import com.agro360.form.common.MetadataBeanName;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.FactureOperation;
import com.agro360.operation.logic.core.PartnerOperation;
import com.agro360.operation.logic.finance.CompteOperation;
import com.agro360.vd.av.FactureStatusEnumVd;
import com.agro360.vd.av.FactureTypeEnumVd;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class FactureForm {

	@Autowired
	private FactureOperation operation;
	
	@Autowired
	private PartnerOperation partnerOperation;
	@Autowired
	private CompteOperation compteOperation;	

	@MetadataBeanName("av/facture")
	public FactureBean initCreateFormBean(ClientContext ctx, FactureTypeEnumVd type, Optional<String> copyFrom) {
		var bean = copyFrom.map(e -> operation.findFactureByCode(ctx, e))
				.orElse(new FactureBean());
		bean.setAction(ClientOperationEnumVd.CREATE);
		bean.getType().setValue(type);
		bean.getStatus().setValue(FactureStatusEnumVd.BRLN);
		bean.getFactureCode().setValue(operation.generateFactureCode());
		bean.getDate().setValue(LocalDate.now());
		bean.getCumulPaiement().setValue(BigDecimal.ZERO);
		initPartnerOption(ctx, bean.getPartner().getPartnerCode()::setValueOptions);
		return bean;
	}
	
	@MetadataBeanName("av/facture")
	public FactureBean initEditFormBean(ClientContext ctx, String factureCode) {
		var bean = operation.findFactureByCode(ctx, factureCode);
		
		bean.setAction(ClientOperationEnumVd.UPDATE);
		initPartnerOption(ctx, bean.getPartner().getPartnerCode()::setValueOptions, bean.getPartner());
		return bean;
	}

	@MetadataBeanName("av/facture-init-paiement")
	public List<PaiementParamBean> initPaiementsFormBean(ClientContext ctx, String factureCode) {
		return compteOperation.findComptesByCriteria(ctx, new CompteSearchBean())
			.stream().map(PaiementParamBean::new)
			.collect(Collectors.toList());
	}

	@MetadataBeanName("av/facture")
	public FactureBean initDeleteFormBean(ClientContext ctx, String factureCode) {
		var bean = operation.findFactureByCode(ctx, factureCode);
		bean.setAction(ClientOperationEnumVd.DELETE);
		return bean;
	}

	@MetadataBeanName("av/facture")
	public FactureBean initChangeStatusFormBean(ClientContext ctx, String factureCode) {
		var bean = operation.findFactureByCode(ctx, factureCode);
		
		bean.setAction(ClientOperationEnumVd.CHANGE_STATUS);
		bean.getStatusDate().setValue(LocalDateTime.now());
		return bean;
	}

	@MetadataBeanName("av/facture-search")
	public FactureSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = AchatVenteMapper.buildFactureSearchBean();
		bean.getDateDebut().setValue(LocalDate.now());
		return bean;
	}
	
	@MetadataBeanName("av/facture-search-result")
	public List<FactureBean> initSearchResultBeans(ClientContext ctx, List<FactureBean> beans) {
		return beans;
	}

	Function<PartnerBean, Object> partnerCodeFn = e -> e.getPartnerCode().getValue();
	
	Function<PartnerBean, String> partnerLibelleFn = e -> e.getPartnerName().getValue();
	
	private void initPartnerOption(ClientContext ctx, Consumer<Map<Object, String>> valueOptionsSetter) {
		var options = partnerOperation.findPartnersByCriteria(ctx, new PartnerSearchBean())
				.stream().collect(Collectors.toMap(partnerCodeFn, partnerLibelleFn));
		valueOptionsSetter.accept(options);
	}
	
	private void initPartnerOption(ClientContext ctx, Consumer<Map<Object, String>> valueOptionsSetter, PartnerBean bean) {
		var options = Collections.singleton(bean)
				.stream().collect(Collectors.toMap(partnerCodeFn, partnerLibelleFn));
		valueOptionsSetter.accept(options);
	}
	

}
