package com.agro360.form.stock;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.bean.stock.UniteSearchBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.form.common.MetadataBeanName;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.UniteOperation;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class UniteForm {
	
	@Autowired
	private UniteOperation operation;
	
	@MetadataBeanName("stock/unite-search")
	public UniteSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = StockMapper.buildUniteSearchBean();
		return bean;
	}

	@MetadataBeanName("stock/unite")
	public UniteBean initCreateFormBean(ClientContext ctx, Optional<String> copyFrom) {
		var bean = copyFrom.map(e -> operation.findUniteByCode(ctx, e))
				.orElse(new UniteBean());
		bean.getUniteCode().setValue(null);
		bean.setAction(ClientOperationEnumVd.CREATE);
		return bean;
	}
	
	private void setToUpdate(UniteBean bean) {
		bean.setAction(ClientOperationEnumVd.UPDATE);
	}
	
	@MetadataBeanName("stock/unite")
	public List<UniteBean> initUpdateFormBean(ClientContext ctx, List<UniteBean> beans) {
		beans.stream().forEach(this::setToUpdate);
		return beans;
	}
}
