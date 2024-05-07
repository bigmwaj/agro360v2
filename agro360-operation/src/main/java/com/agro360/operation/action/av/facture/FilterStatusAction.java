package com.agro360.operation.action.av.facture;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.agro360.bo.bean.av.FactureBean;
import com.agro360.dao.av.IFactureDao;
import com.agro360.operation.action.common.AbstractAction;
import com.agro360.operation.context.ClientContext;
import com.agro360.vd.av.FactureStatusEnumVd;

public class FilterStatusAction extends AbstractAction<Map<Object, String>, FactureBean> {

	@Autowired
	private IFactureDao dao;
	
	@Override
	public Map<Object, String> process(ClientContext ctx, FactureBean bean) {
		var dto = dao.getReferenceById(bean.getFactureCode().getValue());
		var currentStatus = dto.getStatus();
		
		Map<Object, String> valueOptions = new HashMap<>();
		
		switch (currentStatus) {
		case BRLN:
			valueOptions.put(FactureStatusEnumVd.ATAP, FactureStatusEnumVd.ATAP.getLibelle());
			break;
			
		case REJT:
			valueOptions.put(FactureStatusEnumVd.ATAP, FactureStatusEnumVd.ATAP.getLibelle());
			valueOptions.put(FactureStatusEnumVd.ANNL, FactureStatusEnumVd.ANNL.getLibelle());
			break;
			
		case ATAP:
			valueOptions.put(FactureStatusEnumVd.APPR, FactureStatusEnumVd.APPR.getLibelle());
			valueOptions.put(FactureStatusEnumVd.REJT, FactureStatusEnumVd.REJT.getLibelle());
			break;
			
		case APPR:
			valueOptions.put(FactureStatusEnumVd.CLOT, FactureStatusEnumVd.CLOT.getLibelle());
			break;

		default:
			break;
		}
		
		return valueOptions;
	}
}