package com.agro360.operation.logic.production.avicole;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.production.avicole.OperationBean;
import com.agro360.bo.bean.production.avicole.OperationSearchBean;
import com.agro360.dao.common.IDao;
import com.agro360.dao.production.avicole.IOperationDao;
import com.agro360.dto.production.avicole.OperationDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;

@Service
public class OperationOperation extends AbstractOperation<OperationDto, Long> {

	@Autowired
	private IOperationDao dao;

	@Override
	protected IDao<OperationDto, Long> getDao() {
		return dao;
	}

	public List<OperationBean> search(ClientContext ctx, OperationSearchBean orElse) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object save(ClientContext ctx, List<OperationBean> bean) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
