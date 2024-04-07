package com.agro360.bo.bean.production.avicole;

import java.time.LocalDate;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class JourneeSearchBean extends AbstractBean{

	private static final long serialVersionUID = -2528590034330460986L;

	private FieldMetadata<String> cycleCode = new FieldMetadata<>("Cycle");

	private FieldMetadata<LocalDate> journee = new FieldMetadata<>("Journee");
	
}
