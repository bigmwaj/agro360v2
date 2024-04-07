package com.agro360.bo.bean.production.avicole;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.production.avicole.CycleStatusEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CycleSearchBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> cycleCode = new FieldMetadata<>("Cycle");

	private FieldMetadata<CycleStatusEnumVd> status = new FieldMetadata<>("Status", 
			getOptionsMap(CycleStatusEnumVd.values(), 
					CycleStatusEnumVd::getLibelle));

	private FieldMetadata<String> magasin = new FieldMetadata<>("Magasin");
}
