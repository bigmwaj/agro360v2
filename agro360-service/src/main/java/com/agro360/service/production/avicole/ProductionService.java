package com.agro360.service.production.avicole;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.production.avicole.ProductionBean;
import com.agro360.bo.bean.production.avicole.ProductionSearchBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.production.avicole.ProductionOperation;
import com.agro360.service.common.AbstractService;

@Transactional(rollbackFor = Exception.class)
@Service
public class ProductionService extends AbstractService {

	@Autowired
	protected ProductionOperation productionOperation;
	
	public List<ProductionBean> searchAction(ClientContext ctx,  String cycle, Optional<ProductionSearchBean> searchBean) {
		return productionOperation.search(searchBean.orElse(new ProductionSearchBean()));
	}

	public void saveAction(ClientContext ctx,  String cycle, List<ProductionBean> beans) {
		productionOperation.save(beans);
	}
	
}
