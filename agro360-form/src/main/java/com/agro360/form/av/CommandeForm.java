package com.agro360.form.av;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.CommandeSearchBean;
import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.core.PartnerSearchBean;
import com.agro360.bo.bean.finance.CompteBean;
import com.agro360.bo.bean.finance.CompteSearchBean;
import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.bean.stock.MagasinSearchBean;
import com.agro360.bo.mapper.AchatVenteMapper;
import com.agro360.form.common.AbstractForm;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.CommandeOperation;
import com.agro360.operation.logic.av.LigneOperation;
import com.agro360.operation.logic.core.PartnerOperation;
import com.agro360.operation.logic.finance.CompteOperation;
import com.agro360.operation.logic.stock.MagasinOperation;
import com.agro360.operation.metadata.BeanMetadataConfig;
import com.agro360.vd.av.CommandeStatusEnumVd;
import com.agro360.vd.av.CommandeTypeEnumVd;
import com.agro360.vd.av.RemiseTypeEnumVd;
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
	
	@Qualifier("av/commande")
	@Autowired
	private BeanMetadataConfig metadataConfig;
	
	@Qualifier("av/commande-search")
	@Autowired
	private BeanMetadataConfig searchMetadataConfig;

	public CommandeBean initCreateFormBean(ClientContext ctx, CommandeTypeEnumVd type, Optional<String> copyFrom) {
		var bean = new CommandeBean();
		//TODO, appliquer la copy
		bean.setAction(ClientOperationEnumVd.CREATE);
		bean.getCommandeCode().setValue(operation.generateCommandeCode());
		bean.getStatus().setValue(CommandeStatusEnumVd.BRLN);
		bean.getType().setValue(type);
		bean.getRemiseType().setValue(RemiseTypeEnumVd.MONTANT);
		bean.getRemiseMontant().setValue(BigDecimal.ZERO);
		bean.getPrixTotal().setValue(BigDecimal.ZERO);
		bean.getDate().setValue(LocalDate.now());
		bean.getRemise().setValue(BigDecimal.ZERO);
		bean.getTaxe().setValue(BigDecimal.ZERO);
		bean.getPrixTotalHT().setValue(BigDecimal.ZERO);
		bean.getPrixTotalTTC().setValue(BigDecimal.ZERO);
		bean.getPaiementComptant().setValue(BigDecimal.ZERO);

		initPartnerOption(ctx, bean.getPartner().getPartnerCode()::setValueOptions);
		initCompteOption(ctx, bean.getCompte().getCompteCode()::setValueOptions);
		initMagasinOption(ctx, bean.getMagasin().getMagasinCode()::setValueOptions);
		
		metadataConfig.applyMetadata(ctx, bean);
		return bean;
	}
	
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
		initCompteOption(ctx, bean.getCompte().getCompteCode()::setValueOptions);
		initMagasinOption(ctx, bean.getMagasin().getMagasinCode()::setValueOptions);

		metadataConfig.applyMetadata(ctx, bean);
		return bean;
	}

	public CommandeBean initDeleteFormBean(ClientContext ctx, String commandeCode) {
		var bean = operation.findCommandeByCode(ctx, commandeCode);
		
		bean.setAction(ClientOperationEnumVd.DELETE);
		return bean;
	}

	public CommandeBean initChangeStatusFormBean(ClientContext ctx, String commandeCode) {
		var bean = operation.findCommandeByCode(ctx, commandeCode);		
		bean.setAction(ClientOperationEnumVd.CHANGE_STATUS);
		return bean;
	}

	public CommandeSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = AchatVenteMapper.buildCommandeSearchBean();
		initCompteOption(ctx, bean.getCompte()::setValueOptions);
		
		searchMetadataConfig.applyMetadata(ctx, bean);
		return bean;
	}

	private void initCompteOption(ClientContext ctx, Consumer<Map<Object, String>> valueOptionsSetter) {
		Function<CompteBean, Object> codeFn = e -> e.getCompteCode().getValue();
		Function<CompteBean, String> libelleFn = e -> e.getDescription().getValue();
		
		var options = compteOperation.findComptesByCriteria(ctx, new CompteSearchBean())
				.stream().collect(Collectors.toMap(codeFn, libelleFn));

		valueOptionsSetter.accept(options);
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
