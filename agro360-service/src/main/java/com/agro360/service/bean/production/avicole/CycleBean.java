package com.agro360.service.bean.production.avicole;

import java.time.LocalDate;
import java.util.Objects;

import com.agro360.service.bean.common.AbstractEditFormBean;
import com.agro360.service.bean.stock.MagasinBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.production.avicole.StatutCycleEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CycleBean extends AbstractEditFormBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> articleCode = new FieldMetadata<>();

	private FieldMetadata<String> cycleCode = new FieldMetadata<>();

	private FieldMetadata<StatutCycleEnumVd> statut = new FieldMetadata<>();

	private MagasinBean magasin = new MagasinBean();

	private FieldMetadata<String> description = new FieldMetadata<>();

	private FieldMetadata<Double> quantitePlanifiee = new FieldMetadata<>();

	private FieldMetadata<LocalDate> datePlanifiee = new FieldMetadata<>();

	private FieldMetadata<Double> quantiteEffective = new FieldMetadata<>();

	private FieldMetadata<LocalDate> dateEffective = new FieldMetadata<>();

	public void setMagasin(MagasinBean magasin) {
		Objects.requireNonNull(magasin);
		this.magasin = magasin;
	}
}
