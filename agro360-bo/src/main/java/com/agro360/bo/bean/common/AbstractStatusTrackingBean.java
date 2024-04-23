package com.agro360.bo.bean.common;

import java.time.LocalDateTime;

import com.agro360.bo.metadata.FieldMetadata;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbstractStatusTrackingBean<T> extends AbstractBean{

	private static final long serialVersionUID = 6878276582025081700L;

	private FieldMetadata<LocalDateTime> statusDate = new FieldMetadata<>("Date Statut");

	public abstract FieldMetadata<T> getStatus();
}
