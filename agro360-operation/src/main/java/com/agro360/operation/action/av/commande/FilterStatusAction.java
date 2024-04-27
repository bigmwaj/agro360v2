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
		case REJT:
			valueOptions.put(CommandeStatusEnumVd.ATAP, CommandeStatusEnumVd.ATAP.getLibelle());
			break;
		case ATAP:
			valueOptions.put(CommandeStatusEnumVd.APPR, CommandeStatusEnumVd.APPR.getLibelle());
			valueOptions.put(CommandeStatusEnumVd.REJT, CommandeStatusEnumVd.REJT.getLibelle());
			break;
		case APPR:
			valueOptions.put(CommandeStatusEnumVd.RECP, CommandeStatusEnumVd.RECP.getLibelle());
			valueOptions.put(CommandeStatusEnumVd.RECT, CommandeStatusEnumVd.RECT.getLibelle());
			break;
		case RECP:
			valueOptions.put(CommandeStatusEnumVd.RECT, CommandeStatusEnumVd.RECT.getLibelle());
			break;

		default:
			break;
		}
		
		return valueOptions;
	}
}