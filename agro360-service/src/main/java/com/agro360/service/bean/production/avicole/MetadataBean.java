package com.agro360.service.bean.production.avicole;

import java.util.Objects;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class MetadataBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> metadataCode = new FieldMetadata<>();
	
	private FieldMetadata<String> value = new FieldMetadata<>();

	private CycleBean cycle = new CycleBean();

	public void setCycle(CycleBean cycle) {
		Objects.requireNonNull(cycle);
		this.cycle = cycle;
	}
}
