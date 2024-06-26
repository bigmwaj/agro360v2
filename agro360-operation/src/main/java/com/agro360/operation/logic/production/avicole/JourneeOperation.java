package com.agro360.operation.logic.production.avicole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.production.avicole.JourneeBean;
import com.agro360.bo.bean.production.avicole.JourneeSearchBean;
import com.agro360.bo.mapper.ProductionMapper;
import com.agro360.bo.message.Message;
import com.agro360.dao.common.IDao;
import com.agro360.dao.production.avicole.ICycleDao;
import com.agro360.dto.production.avicole.CycleDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.vd.common.ClientOperationEnumVd;

@Service
public class JourneeOperation extends AbstractOperation<CycleDto, String>{

	@Autowired
	private ICycleDao cycleDao;
	
	@Override
	protected IDao<CycleDto, String> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getRulePath() {
		return "production/avicole/journee";
	}
	
	public JourneeSearchBean initSearchFormBean() {
		var bean = ProductionMapper.buildJourneeSearchBean();
		return bean;
	}
	
	public List<JourneeBean> search(ClientContext ctx, JourneeSearchBean searchBean) {
		var cycle = this.cycleDao.getReferenceById(searchBean.getCycleCode().getValue());
		var journee = ProductionMapper.mapToBean(cycle, searchBean.getJournee().getValue());
		return Collections.singletonList(journee);
	}
	
	public List<Message> save(JourneeBean bean) {
		if( bean.getAction() == null ) {
			return Collections.singletonList(Message.error("Aucune action sélectionnée"));
		}
		
		if(  ClientOperationEnumVd.SYNC.equals(bean.getAction())) {
			return Collections.emptyList();
		}
		
		List<Message> messages = new ArrayList<>();

		return messages;
	}
}
