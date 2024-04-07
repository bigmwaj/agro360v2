package com.agro360.service.stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.bean.stock.UniteSearchBean;
import com.agro360.bo.mapper.stock.UniteMapper;
import com.agro360.bo.message.Message;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.UniteOperation;
import com.agro360.service.common.AbstractService;
import com.agro360.vd.common.EditActionEnumVd;

@Service
public class UniteService extends AbstractService {

	@Autowired
	UniteOperation service;

	@Autowired
	UniteMapper mapper;

	public List<UniteBean> searchAction(ClientContext ctx, Optional<UniteSearchBean> searchBean) {
		return  service.findUnitesByCriteria(ctx, searchBean.orElse(new UniteSearchBean()));
	}

	public void saveAction(ClientContext ctx,  List<UniteBean> beans, BindingResult br) {
		beans.stream().map(e -> save(ctx, e)).flatMap(List::stream).collect(Collectors.toList());
	}
	
	private List<Message> save(ClientContext ctx, UniteBean bean) {
		if( bean.getAction() == null ) {
			return Collections.singletonList(Message.error("Aucune action sélectionnée"));
		}
		
		if(  EditActionEnumVd.SYNC.equals(bean.getAction())) {
			return Collections.emptyList();
		}
		
		List<Message> messages = new ArrayList<>();

		switch (bean.getAction()) {
		case CREATE:
			service.createUnite(ctx, bean);
			break;

		case UPDATE:
			service.updateUnite(ctx, bean);
			break;

		case DELETE:
			service.deleteUnite(ctx, bean);
			break;
			
		default:
			
		}

		return messages;
	}
}
