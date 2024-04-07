package com.agro360.form.av;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.CommandeSearchBean;
import com.agro360.bo.mapper.av.CommandeMapper;
import com.agro360.dto.av.CommandeDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.CommandeOperation;
import com.agro360.operation.logic.av.LigneOperation;
import com.agro360.vd.av.CommandeStatusEnumVd;
import com.agro360.vd.common.EditActionEnumVd;

@Component
public class CommandeForm {

	@Autowired
	CommandeMapper mapper;

	@Autowired
	CommandeOperation operation;	
	
	@Autowired
	LigneOperation ligneService;	
	
	@Autowired
	LigneForm ligneForm;	

	public CommandeBean initCreateFormBean(ClientContext ctx, Optional<String> copyFrom) {
		var bean = mapper.map(new CommandeDto());
		
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
		var bean = mapper.mapToSearchBean();
		return bean;
	}

}
