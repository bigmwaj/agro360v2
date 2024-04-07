package com.agro360.operation.logic.production.avicole;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.production.avicole.CycleBean;
import com.agro360.bo.bean.production.avicole.CycleSearchBean;
import com.agro360.dao.common.IDao;
import com.agro360.dao.production.avicole.ICycleDao;
import com.agro360.dto.production.avicole.CycleDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;

@Service
public class CycleOperation extends AbstractOperation<CycleDto, String> {

	@Autowired
	private ICycleDao dao;

	@Override
	protected IDao<CycleDto, String> getDao() {
		return dao;
	}
	
	@Override
	protected String getRulePath() {
		return "production/avicole/cycle";
	}

	public List<CycleBean> search(ClientContext ctx, CycleSearchBean orElse) {
		// TODO Auto-generated method stub
		return null;
	}

	public Object save(CycleBean bean) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
