package com.agro360.service.bean.stock;

import java.time.LocalDateTime;

import com.agro360.dto.stock.OperationCaisseDto;
import com.agro360.service.bean.common.AbstractLigneBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.stock.TypeOperationEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class OperationCaisseBean extends AbstractLigneBean<OperationCaisseDto> {

	private static final long serialVersionUID = 3706447795412478903L;

	private FieldMetadata<LocalDateTime> dateOperation = new FieldMetadata<>();

	private FieldMetadata<TypeOperationEnumVd> typeOperation = new FieldMetadata<>();
}
