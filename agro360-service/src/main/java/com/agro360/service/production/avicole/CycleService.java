package com.agro360.service.production.avicole;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.production.avicole.CycleBean;
import com.agro360.bo.bean.production.avicole.CycleSearchBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.production.avicole.CycleOperation;
import com.agro360.service.common.AbstractService;

@Service
public class CycleService extends AbstractService {

	@Autowired
	CycleOperation cycleOperation;
	
	public List<CycleBean> searchAction(ClientContext ctx, Optional<CycleSearchBean> searchBean) {
		return cycleOperation
				.search(ctx, searchBean.orElse(new CycleSearchBean()));
	}

	public void saveAction(ClientContext ctx, CycleBean bean) {
		cycleOperation.save(bean);
	}
	
}
