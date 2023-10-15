package com.agro360.service.bean.production.avicole;

import com.agro360.dto.production.avicole.OperationDto;
import com.agro360.service.bean.common.AbstractLigneBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.service.utils.TypeScriptInfos;
import com.agro360.vd.production.avicole.PhaseEnumVd;
import com.agro360.vd.production.avicole.RubriqueEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
@TypeScriptInfos(igroreSuperClassParam = true)
public class OperationBean extends AbstractLigneBean<OperationDto> {

	private static final long serialVersionUID = 1647090333349627006L;
	
	private FieldMetadata<Long> numeroJournee = new FieldMetadata<>("#Journée");
	
	private FieldMetadata<PhaseEnumVd> phase = new FieldMetadata<>("Phase");

	private FieldMetadata<RubriqueEnumVd> rubrique = new FieldMetadata<>("Rubrique");
}
