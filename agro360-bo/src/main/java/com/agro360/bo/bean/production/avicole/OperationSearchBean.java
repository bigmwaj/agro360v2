package com.agro360.bo.bean.production.avicole;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.production.avicole.PhaseEnumVd;
import com.agro360.vd.production.avicole.RubriqueEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class OperationSearchBean extends AbstractBean{

	private static final long serialVersionUID = -2528590034330460986L;

	private FieldMetadata<String> cycleCode = new FieldMetadata<>("Cycle");

	private FieldMetadata<PhaseEnumVd> phase = new FieldMetadata<>("Phase");

	private FieldMetadata<RubriqueEnumVd> rubrique = new FieldMetadata<>("Rubrique");
	
}
