package com.agro360.bo.bean.production.avicole;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class JourneeBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<Long> numeroJournee = new FieldMetadata<>();

	private FieldMetadata<LocalDate> journee = new FieldMetadata<>();

	private List<ProductionBean> productions = new ArrayList<>();

	private CycleBean cycle = new CycleBean();

	public void setCycle(CycleBean cycle) {
		Objects.requireNonNull(cycle);
		this.cycle = cycle;
	}
}
