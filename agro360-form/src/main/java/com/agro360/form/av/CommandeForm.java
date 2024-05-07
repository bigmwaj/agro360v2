package com.agro360.form.av;

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

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.CommandeSearchBean;
import com.agro360.bo.bean.av.PaiementBean;
import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.core.PartnerSearchBean;
import com.agro360.bo.bean.finance.CompteSearchBean;
import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.bean.stock.MagasinSearchBean;
import com.agro360.bo.mapper.AchatVenteMapper;
import com.agro360.form.common.AbstractForm;
import com.agro360.form.common.MetadataBeanName;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.CommandeOperation;
import com.agro360.operation.logic.av.LigneOperation;
import com.agro360.operation.logic.core.PartnerOperation;
import com.agro360.operation.logic.finance.CompteOperation;
import com.agro360.operation.logic.stock.MagasinOperation;
import com.agro360.vd.av.CommandeStatusEnumVd;
import com.agro360.vd.av.CommandeTypeEnumVd;
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
	private PartnerOperation partnerOperation ;	

	@MetadataBeanName("av/commande")
	public CommandeBean initCreateFormBean(ClientContext ctx, CommandeTypeEnumVd type, Optional<String> copyFrom) {
		var bean = copyFrom.map(e -> operation.findCommandeByCode(ctx, e))
				.orElse(new CommandeBean());
		bean.setAction(ClientOperationEnumVd.CREATE);
		bean.getCommandeCode().setValue(operation.generateCommandeCode());
		bean.getStatus().setValue(CommandeStatusEnumVd.BRLN);
		bean.getType().setValue(type);
		bean.getDate().setValue(LocalDate.now());
		
		var lignes = copyFrom.map(e -> ligneOperation.findLignesCommande(ctx, e))
				.orElse(Collections.emptyList());
		for (var  ligne : lignes) {
			ligne.getLigneId().setValue(null);
			ligne.setAction(ClientOperationEnumVd.CREATE);
			bean.getLignes().add(ligne);
		}
		initPartnerOption(ctx, bean.getPartner().getPartnerCode()::setValueOptions);
		initMagasinOption(ctx, bean.getMagasin().getMagasinCode()::setValueOptions);
		
		return bean;
	}
	
	@MetadataBeanName("av/commande")
	public CommandeBean initEditFormBean(ClientContext ctx, String commandeCode) {
		var bean = operation.findCommandeByCode(ctx, commandeCode);
		
		bean.setAction(ClientOperationEnumVd.UPDATE);
		
		var lignes = ligneOperation.findLignesCommande(ctx, commandeCode);

		for (var  ligne : lignes) {
			/*
			 * Pour se rassurer que chaque fois qu'on va enregistrer une commande, 
			 * on enregistre les lignes aussi même si l'utilisateur n'a pas modifié.
			 * Ceci garantie qu'on recalcule au besoin les prix de la ligne
			 */
			ligne.setAction(ClientOperationEnumVd.UPDATE);
			bean.getLignes().add(ligne);
		}

		initPartnerOption(ctx, bean.getPartner().getPartnerCode()::setValueOptions);
		initMagasinOption(ctx, bean.getMagasin().getMagasinCode()::setValueOptions);

		return bean;
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
	
	private void initPartnerOption(ClientContext ctx, Consumer<Map<Object, String>> valueOptionsSetter) {
		Function<PartnerBean, Object> codeFn = e -> e.getPartnerCode().getValue();
		Function<PartnerBean, String> libelleFn = e -> e.getPartnerName().getValue();
		
		var options = partnerOperation.findPartnersByCriteria(ctx, new PartnerSearchBean())
				.stream().collect(Collectors.toMap(codeFn, libelleFn));
		
		valueOptionsSetter.accept(options);
	}
}
