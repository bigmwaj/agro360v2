package com.agro360.service.production.avicole;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.production.avicole.JourneeBean;
import com.agro360.bo.bean.production.avicole.JourneeSearchBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.production.avicole.JourneeOperation;
import com.agro360.service.common.AbstractService;

@Transactional(rollbackFor = Exception.class)
@Service
public class JourneeService extends AbstractService {

	@Autowired
	private JourneeOperation journeeOperation;
	
	public List<JourneeBean> searchAction(ClientContext ctx, Optional<JourneeSearchBean> searchBean) {
		return journeeOperation
				.search(ctx, searchBean.orElse(new JourneeSearchBean()));
	}
	
	public void saveAction(ClientContext ctx, JourneeBean bean) {
		journeeOperation.save(bean);
	}
}
