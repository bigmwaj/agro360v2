package com.agro360.form.av;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.CommandeSearchBean;
import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.bean.av.PaiementBean;
import com.agro360.bo.bean.finance.CompteSearchBean;
import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.bean.stock.MagasinSearchBean;
import com.agro360.bo.mapper.AchatVenteMapper;
import com.agro360.form.common.AbstractForm;
import com.agro360.form.common.MetadataBeanName;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.CommandeOperation;
import com.agro360.operation.logic.av.LigneOperation;
import com.agro360.operation.logic.finance.CompteOperation;
import com.agro360.operation.logic.stock.MagasinOperation;
import com.agro360.vd.av.CommandeStatusEnumVd;
import com.agro360.vd.av.CommandeTypeEnumVd;
import com.agro360.vd.av.LigneTypeEnumVd;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class CommandeForm extends AbstractForm{

	@Autowired
	private CommandeOperation operation;	
	
	@Autowired
	private LigneOperation ligneOperation;	
	
	@Autowired
	private MagasinOperation magasinOperation;	
	
	@Autowired
	private CompteOperation compteOperation;	
	
	@Autowired
	private LigneForm ligneForm;

	@MetadataBeanName("av/commande")
	public CommandeBean initCreateFormBean(ClientContext ctx, CommandeTypeEnumVd type, Optional<String> copyFrom) {
		var bean = copyFrom.map(e -> operation.findCommandeByCode(ctx, e))
				.orElse(new CommandeBean());
		bean.setAction(ClientOperationEnumVd.CREATE);
		bean.getCommandeCode().setValue(operation.generateCommandeCode());
		bean.getStatus().setValue(CommandeStatusEnumVd.BRLN);
		bean.getType().setValue(type);
		bean.getDate().setValue(LocalDate.now());
		bean.getPartner().getPartnerCode().setValue(ctx.getDefaultPartner());
		bean.getMagasin().getMagasinCode().setValue(ctx.getDefaultMagasin());
		
		var lignes = copyFrom.map(e -> ligneOperation.findLignesCommande(ctx, e))
				.orElse(Collections.emptyList());
		for (var  ligne : lignes) {
			ligne.getLigneId().setValue(null);
			ligne.setAction(ClientOperationEnumVd.CREATE);
			bean.getLignes().add(ligne);
		}
		initMagasinOption(ctx, bean.getMagasin().getMagasinCode()::setValueOptions);
		
		initLignes(ctx, bean.getLignes());
		
		return bean;
	}
	
	@MetadataBeanName("av/commande")
	public CommandeBean initEditFormBean(ClientContext ctx, String commandeCode) {
		var bean = operation.findCommandeByCode(ctx, commandeCode);
		
		bean.setAction(ClientOperationEnumVd.UPDATE);
		
		var lignes = ligneOperation.findLignesCommande(ctx, commandeCode);

		for (var  ligne : lignes) {
			ligne.setAction(ClientOperationEnumVd.UPDATE);
			bean.getLignes().add(ligne);
		}
		
		initLignes(ctx, bean.getLignes());

		initMagasinOption(ctx, bean.getMagasin().getMagasinCode()::setValueOptions);

		return bean;
	}
	
	private void initLignes(ClientContext ctx, List<LigneBean> beans) {
		Predicate<LigneBean> isStandard;
		isStandard = e -> LigneTypeEnumVd.ARTC.equals(e.getType().getValue()) 
				|| LigneTypeEnumVd.SERV.equals(e.getType().getValue());
		
		beans.stream().filter(isStandard).forEach(e -> ligneForm.initArticle(ctx, e));
		
	}

	public CommandeBean initDeleteFormBean(ClientContext ctx, String commandeCode) {
		var bean = operation.findCommandeByCode(ctx, commandeCode);
		
		bean.setAction(ClientOperationEnumVd.DELETE);
		return bean;
	}

	@MetadataBeanName("av/commande")
	public CommandeBean initChangeStatusFormBean(ClientContext ctx, String commandeCode) {
		var bean = operation.findCommandeByCode(ctx, commandeCode);
		bean.setAction(ClientOperationEnumVd.CHANGE_STATUS);
		bean.getStatusDate().setValue(LocalDateTime.now());
		return bean;
	}
	
	@MetadataBeanName("av/commande-init-paiement")
	public List<PaiementBean> initPaiementsFormBean(ClientContext ctx, String commandeCode) {
		return compteOperation.findComptesByCriteria(ctx, new CompteSearchBean())
			.stream().map(PaiementBean::new)
			.collect(Collectors.toList());
	}

	@MetadataBeanName("av/commande-search")
	public CommandeSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = AchatVenteMapper.buildCommandeSearchBean();		
		return bean;
	}
	
	@MetadataBeanName("av/commande-search-result")
	public List<CommandeBean> initSearchResultBeans(ClientContext ctx, List<CommandeBean> beans) {
		return beans;
	}
	
	private void initMagasinOption(ClientContext ctx, Consumer<Map<Object, String>> valueOptionsSetter) {
		Function<MagasinBean, Object> codeFn = e -> e.getMagasinCode().getValue();
		Function<MagasinBean, String> libelleFn = e -> e.getDescription().getValue();
		
		var options = magasinOperation.findMagasinsByCriteria(ctx, new MagasinSearchBean())
				.stream().collect(Collectors.toMap(codeFn, libelleFn));
		
		valueOptionsSetter.accept(options);
	}
}
