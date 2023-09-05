package com.agro360.service.bean.production.avicole;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.production.avicole.StatusCycleEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CycleSearchBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> cycleCode = new FieldMetadata<>("Cycle");

	private FieldMetadata<StatusCycleEnumVd> status = new FieldMetadata<>("Status", 
			getOptionsMap(StatusCycleEnumVd.values(), 
					StatusCycleEnumVd::getLibelle));

	private FieldMetadata<String> magasin = new FieldMetadata<>("Magasin");
}
