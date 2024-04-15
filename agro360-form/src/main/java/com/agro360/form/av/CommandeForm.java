package com.agro360.form.av;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.CommandeSearchBean;
import com.agro360.bo.mapper.AchatVenteMapper;
import com.agro360.dto.av.CommandeDto;
import com.agro360.form.common.AbstractForm;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.CommandeOperation;
import com.agro360.operation.logic.av.LigneOperation;
import com.agro360.vd.av.CommandeStatusEnumVd;
import com.agro360.vd.av.RemiseTypeEnumVd;
import com.agro360.vd.common.EditActionEnumVd;

@Component
public class CommandeForm extends AbstractForm{

	@Autowired
	CommandeOperation operation;	
	
	@Autowired
	LigneOperation ligneService;	
	
	@Autowired
	LigneForm ligneForm;	

	public CommandeBean initCreateFormBean(ClientContext ctx, Optional<String> copyFrom) {
		var bean = AchatVenteMapper.map(new CommandeDto());
		
		bean.setAction(EditActionEnumVd.CREATE);
		
		bean.getStatus().setValue(CommandeStatusEnumVd.BRLN);
		bean.getStatus().setEditable(false);
		
		bean.getCommandeCode().setRequired(true);
		bean.getCommandeCode().setValue(operation.generateCommandeCode());

		bean.getType().setRequired(true);
		
		bean.getDate().setRequired(true);
		bean.getDate().setValue(LocalDate.now());
		
		bean.getPartner().getPartnerCode().setRequired(true);
		
		bean.getMagasin().getMagasinCode().setRequired(true);
		
		bean.getRemiseMontant().setValue(BigDecimal.ZERO);
		bean.getRemiseType().setValue(RemiseTypeEnumVd.MONTANT);
		
		bean.getPrixTotal().setValue(BigDecimal.ZERO);
		bean.getPrixTotal().setEditable(false);
		
		bean.getRemise().setValue(BigDecimal.ZERO);
		bean.getRemise().setEditable(false);
		
		bean.getTaxe().setValue(BigDecimal.ZERO);
		bean.getTaxe().setEditable(false);
		
		bean.getPrixTotalHT().setValue(BigDecimal.ZERO);
		bean.getPrixTotalHT().setEditable(false);
		
		bean.getPrixTotalTTC().setValue(BigDecimal.ZERO);
		bean.getPrixTotalTTC().setEditable(false);
		return bean;
	}
	
	public CommandeBean initEditFormBean(ClientContext ctx, String commandeCode) {
		var bean = operation.findCommandeByCode(ctx, commandeCode);
		
		bean.setAction(EditActionEnumVd.UPDATE);
		
		bean.getCommandeCode().setEditable(false);
		
		bean.getStatus().setEditable(false);

		bean.getType().setEditable(false);
		
		bean.getDate().setRequired(true);
		
		bean.getPartner().getPartnerCode().setEditable(false);
		
		bean.getMagasin().getMagasinCode().setEditable(false);
		
		bean.getPrixTotal().setEditable(false);
		bean.getRemise().setEditable(false);
		bean.getTaxe().setEditable(false);
		bean.getPrixTotalHT().setEditable(false);
		bean.getPrixTotalTTC().setEditable(false);
		
		var lignes = ligneService.findLignesCommande(ctx, commandeCode);

		for (var  ligne : lignes) {
			ligneForm.initUpdateFormBean(ctx, bean, ligne);
			bean.getLignes().add(ligne);
		}
		
		return bean;
	}

	public CommandeBean initDeleteFormBean(ClientContext ctx, String commandeCode) {
		var bean = operation.findCommandeByCode(ctx, commandeCode);
		
		bean.setAction(EditActionEnumVd.DELETE);
		
		bean.getCommandeCode().setRequired(true);
		bean.getCommandeCode().setEditable(false);
		return bean;
	}

	public CommandeBean initChangeStatusFormBean(ClientContext ctx, String commandeCode) {
		var bean = operation.findCommandeByCode(ctx, commandeCode);
		
		bean.setAction(EditActionEnumVd.CHANGE_STATUS);
		
		bean.getCommandeCode().setRequired(true);
		bean.getCommandeCode().setEditable(false);
		bean.getStatus().setRequired(true);
		return bean;
	}

	public CommandeSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = AchatVenteMapper.buildCommandeSearchBean();
		return bean;
	}

}
