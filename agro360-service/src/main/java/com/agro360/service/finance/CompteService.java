package com.agro360.service.finance;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.finance.CompteBean;
import com.agro360.bo.bean.finance.CompteSearchBean;
import com.agro360.bo.message.Message;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.finance.CompteOperation;
import com.agro360.service.common.AbstractService;
import com.agro360.vd.common.ClientOperationEnumVd;

@Transactional(rollbackFor = Exception.class)
@Service
public class CompteService extends AbstractService {

	@Autowired
	CompteOperation service;
	
	public List<CompteBean> searchAction(ClientContext ctx, Optional<CompteSearchBean> searchBean) {
		return service.findComptesByCriteria(ctx, searchBean.orElse(new CompteSearchBean()));
	}

	public void saveAction(ClientContext ctx, List<CompteBean> beans) {
		beans.stream().map(e -> save(ctx, e)).flatMap(List::stream).collect(Collectors.toList());
	}
	
	private List<Message> save(ClientContext ctx, CompteBean bean) {
		if( bean.getAction() == null ) {
			return Collections.singletonList(Message.error("Aucune action sélectionnée"));
		}
		
		if(  ClientOperationEnumVd.SYNC.equals(bean.getAction())) {
			return Collections.emptyList();
		}
		
		List<Message> messages = new ArrayList<>();

		switch (bean.getAction()) {
		case CREATE:
			service.createCompte(ctx, bean);
			break;

		case UPDATE:
			service.updateCompte(ctx, bean);
			break;

		case DELETE:
			service.deleteCompte(ctx, bean);
			break;
			
		default:
			
		}

		return messages;
	}

	public Object generateEtatCompteAction(ClientContext clientContext) {
		return service.generateEtatCompte(clientContext);
	}
}
