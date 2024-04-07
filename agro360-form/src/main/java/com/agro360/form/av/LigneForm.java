package com.agro360.form.av;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.mapper.av.LigneMapper;
import com.agro360.dto.av.LigneDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.LigneOperation;
import com.agro360.vd.common.EditActionEnumVd;

@Component
public class LigneForm {

	@Autowired
	LigneMapper mapper;

	@Autowired
	LigneOperation operation;	

	public LigneBean initCreateFormBean(ClientContext ctx, String commandeCode, Optional<Long> copyFrom, Optional<String> articleCode) {
		var bean = mapper.map(new LigneDto());
		
		bean.setAction(EditActionEnumVd.CREATE);
		
		bean.getType().setRequired(true);
		
		bean.getQuantite().setRequired(true);
		bean.getQuantite().setValue(0.0);
		return bean;
	}
	
	LigneBean initUpdateFormBean(ClientContext ctx, CommandeBean commande, LigneBean bean) {
		
		bean.setAction(EditActionEnumVd.UPDATE);
		
		bean.getType().setRequired(true);
		
		bean.getQuantite().setRequired(true);
		return bean;
	}

}
