package com.agro360.service.finance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.finance.TaxeBean;
import com.agro360.bo.message.Message;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.finance.TaxeOperation;
import com.agro360.service.common.AbstractService;
import com.agro360.vd.common.EditActionEnumVd;

@Transactional(rollbackFor = Exception.class)
@Service
public class TaxeService extends AbstractService {

	@Autowired
	TaxeOperation service;

	public List<TaxeBean> searchAction(ClientContext ctx) {
		return service.findAllTaxes(ctx);
	}

	public void saveAction(ClientContext ctx, List<TaxeBean> beans) {
		beans.stream().map(e -> save(ctx, e)).flatMap(List::stream).collect(Collectors.toList());
	}
	
	private List<Message> save(ClientContext ctx, TaxeBean bean) {
		if( bean.getAction() == null ) {
			return Collections.singletonList(Message.error("Aucune action sélectionnée"));
		}
		
		if(  EditActionEnumVd.SYNC.equals(bean.getAction())) {
			return Collections.emptyList();
		}
		
		List<Message> messages = new ArrayList<>();

		switch (bean.getAction()) {
		case CREATE:
			service.createTaxe(ctx, bean);
			break;

		case UPDATE:
			service.updateTaxe(ctx, bean);
			break;

		case DELETE:
			service.deleteTaxe(ctx, bean);
			break;
			
		default:
			
		}

		return messages;
	}
}
