package com.agro360.service.production.avicole;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.agro360.bo.bean.production.avicole.OperationBean;
import com.agro360.bo.bean.production.avicole.OperationSearchBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.production.avicole.OperationOperation;
import com.agro360.service.common.AbstractService;

public abstract class OperationService extends AbstractService {

	@Autowired
	protected OperationOperation operationOperation;
	
	public List<OperationBean> searchAction(ClientContext ctx, Optional<OperationSearchBean> searchBean) {
		return operationOperation.search(ctx, searchBean.orElse(new OperationSearchBean()));
	}

	public void saveAction(ClientContext ctx, List<OperationBean> beans) {
		operationOperation.save(ctx, beans);
	}
	
	
}
