package com.agro360.form.av;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.FactureBean;
import com.agro360.bo.bean.av.FactureSearchBean;
import com.agro360.bo.mapper.AchatVenteMapper;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.FactureOperation;
import com.agro360.vd.av.FactureStatusEnumVd;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class FactureForm {

	@Autowired
	private FactureOperation operation;	

	public FactureBean initCreateFormBean(ClientContext ctx, Optional<String> copyFrom) {
		var bean = new FactureBean();
		
		bean.setAction(ClientOperationEnumVd.CREATE);
		
		bean.getStatus().setValue(FactureStatusEnumVd.BRLN);
		bean.getStatus().setEditable(false);
		
		bean.getFactureCode().setRequired(true);
		bean.getFactureCode().setValue(operation.generateFactureCode());
		
		bean.getType().setRequired(true);
		
		bean.getDate().setRequired(true);
		bean.getDate().setValue(LocalDate.now());
		
		bean.getMontant().setRequired(true);
		
		return bean;
	}
	
	public FactureBean initEditFormBean(ClientContext ctx, String factureCode) {
		var bean = operation.findFactureByCode(ctx, factureCode);
		
		bean.setAction(ClientOperationEnumVd.UPDATE);
		
		bean.getFactureCode().setRequired(true);
		bean.getStatus().setEditable(false);
		return bean;
	}

	public FactureBean initDeleteFormBean(ClientContext ctx, String factureCode) {
		var bean = operation.findFactureByCode(ctx, factureCode);
		
		bean.setAction(ClientOperationEnumVd.DELETE);
		
		bean.getFactureCode().setRequired(true);
		return bean;
	}

	public FactureBean initChangeStatusFormBean(ClientContext ctx, String factureCode) {
		var bean = operation.findFactureByCode(ctx, factureCode);
		
		bean.setAction(ClientOperationEnumVd.CHANGE_STATUS);
		
		bean.getFactureCode().setRequired(true);
		bean.getStatus().setRequired(true);
		
		bean.getStatusDate().setValue(LocalDateTime.now());
		return bean;
	}

	public FactureSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = AchatVenteMapper.buildFactureSearchBean();
		return bean;
	}

}
