package com.agro360.form.stock;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.bean.stock.MagasinSearchBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.MagasinOperation;


@Component
public class MagasinForm {

	@Autowired
	MagasinOperation operation;

	public MagasinSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = StockMapper.buildMagasinSearchBean();
		return bean;
	}
	
	public MagasinBean initEditFormBean(ClientContext ctx, String magasinCode) {
		var bean = operation.findMagasinByCode(ctx, magasinCode);
		return bean;
	}
	
	public MagasinBean initDeleteFormBean(ClientContext ctx, String magasinCode) {
		var bean = operation.findMagasinByCode(ctx, magasinCode);
		return bean;
	}

	public MagasinBean initCreateFormBean(ClientContext ctx, Optional<String> copyFrom) {
		var bean = new MagasinBean();
		bean.getMagasinCode().setValue(null);
		
		AbstractBean.setActionToCreate.accept(bean);
		bean.getCasiers().stream().forEach(AbstractBean.setActionToCreate);
		
		return bean;
	}
}
