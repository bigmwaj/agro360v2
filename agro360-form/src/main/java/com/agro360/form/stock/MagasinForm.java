package com.agro360.form.stock;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.bean.stock.MagasinSearchBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.form.common.AbstractForm;
import com.agro360.form.common.MetadataBeanName;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.MagasinOperation;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class MagasinForm extends AbstractForm{
	
	@Autowired
	MagasinOperation operation;

	@MetadataBeanName("stock/magasin-search")
	public MagasinSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = StockMapper.buildMagasinSearchBean();
		return bean;
	}
	
	@MetadataBeanName("stock/magasin")
	public MagasinBean initEditFormBean(ClientContext ctx, String magasinCode) {
		var bean = operation.findMagasinByCode(ctx, magasinCode);
		bean.setAction(ClientOperationEnumVd.UPDATE);
		return bean;
	}
	
	public MagasinBean initDeleteFormBean(ClientContext ctx, String magasinCode) {
		var bean = operation.findMagasinByCode(ctx, magasinCode);
		return bean;
	}

	@MetadataBeanName("stock/magasin")
	public MagasinBean initCreateFormBean(ClientContext ctx, Optional<String> copyFrom) {
		var bean = copyFrom.map(e -> operation.findMagasinByCode(ctx, e))
				.orElse(new MagasinBean());
		
		bean.getMagasinCode().setValue(null);
		bean.setAction(ClientOperationEnumVd.CREATE);
		
		return bean;
	}
}
