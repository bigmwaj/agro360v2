package com.agro360.service.finance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.finance.RubriqueBean;
import com.agro360.bo.bean.finance.RubriqueSearchBean;
import com.agro360.bo.message.Message;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.finance.RubriqueOperation;
import com.agro360.service.common.AbstractService;
import com.agro360.vd.common.EditActionEnumVd;

@Transactional(rollbackFor = Exception.class)
@Service
public class RubriqueService extends AbstractService {

	@Autowired
	RubriqueOperation service;

	public List<RubriqueBean> searchAction(ClientContext ctx, Optional<RubriqueSearchBean> searchBean) {
		return service.findRubriquesByCriteria(ctx, searchBean.orElse(new RubriqueSearchBean()));
	}

	public void saveAction(ClientContext ctx, List<RubriqueBean> beans) {
		beans.stream().map(e -> save(ctx, e)).flatMap(List::stream).collect(Collectors.toList());
	}
	
	private List<Message> save(ClientContext ctx, RubriqueBean bean) {
		if( bean.getAction() == null ) {
			return Collections.singletonList(Message.error("Aucune action sélectionnée"));
		}
		
		if(  EditActionEnumVd.SYNC.equals(bean.getAction())) {
			return Collections.emptyList();
		}
		
		List<Message> messages = new ArrayList<>();

		switch (bean.getAction()) {
		case CREATE:
			service.createRubrique(ctx, bean);
			break;

		case UPDATE:
			service.updateRubrique(ctx, bean);
			break;

		case DELETE:
			service.deleteRubrique(ctx, bean);
			break;
			
		default:
			
		}

		return messages;
	}
}
