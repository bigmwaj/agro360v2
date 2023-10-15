package com.agro360.service.bean.production.avicole;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ProductionSearchBean extends AbstractBean{

	private static final long serialVersionUID = -2528590034330460986L;

	private FieldMetadata<String> cycleCode = new FieldMetadata<>("Cycle");
	
}
