package com.agro360.service.bean.production.avicole;

import com.agro360.dto.achat.LigneDto;
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
public class OperationBean extends AbstractLigneBean<LigneDto> {

	private static final long serialVersionUID = 1647090333349627006L;
	
	private FieldMetadata<PhaseEnumVd> phase = new FieldMetadata<>();

	private FieldMetadata<RubriqueEnumVd> rubrique = new FieldMetadata<>();
}
