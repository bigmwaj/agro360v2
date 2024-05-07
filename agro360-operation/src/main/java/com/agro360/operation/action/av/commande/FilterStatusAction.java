package com.agro360.operation.action.av.commande;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.dao.av.ICommandeDao;
import com.agro360.operation.action.common.AbstractAction;
import com.agro360.operation.context.ClientContext;
import com.agro360.vd.av.CommandeStatusEnumVd;

public class FilterStatusAction extends AbstractAction<Map<Object, String>, CommandeBean> {

	@Autowired
	private ICommandeDao dao;
	
	@Override
	public Map<Object, String> process(ClientContext ctx, CommandeBean bean) {
		var dto = dao.getReferenceById(bean.getCommandeCode().getValue());
		var currentStatus = dto.getStatus();
		
		Map<Object, String> valueOptions = new HashMap<>();
		
		switch (currentStatus) {
		case BRLN:
			valueOptions.put(CommandeStatusEnumVd.ATAP, CommandeStatusEnumVd.ATAP.getLibelle());
			break;
			
		case ATAP:
			valueOptions.put(CommandeStatusEnumVd.APPR, CommandeStatusEnumVd.APPR.getLibelle());
			break;
			
		case AANN:
			valueOptions.put(CommandeStatusEnumVd.ANNL, CommandeStatusEnumVd.ANNL.getLibelle());
			break;
			
		case RGLM:
			valueOptions.put(CommandeStatusEnumVd.ATAP, CommandeStatusEnumVd.ATAP.getLibelle());
			valueOptions.put(CommandeStatusEnumVd.AANN, CommandeStatusEnumVd.AANN.getLibelle());
			break;
			
		case TERM:
		case APPR:
		case RECP:
		case RECT:
			valueOptions.put(CommandeStatusEnumVd.CLOT, CommandeStatusEnumVd.CLOT.getLibelle());
			break;

		default:
			break;
		}
		
		return valueOptions;
	}
}