package com.agro360.bo.bean.production.avicole;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.agro360.bo.bean.common.AbstractStatusTrackingBean;
import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.production.avicole.CycleStatusEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CycleBean extends AbstractStatusTrackingBean<CycleStatusEnumVd> {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> cycleCode = new FieldMetadata<>("Cycle");

	private FieldMetadata<CycleStatusEnumVd> status = new FieldMetadata<>("Statut", 
			getOptionsMap(CycleStatusEnumVd.values(), 
			CycleStatusEnumVd::getLibelle));

	private MagasinBean magasin = new MagasinBean();

	private FieldMetadata<String> description = new FieldMetadata<>("Description");

	private FieldMetadata<String> racePlanifiee = new FieldMetadata<>("Race(Plan.)");

	private FieldMetadata<Double> quantitePlanifiee = new FieldMetadata<>("Qté(Plan.)");

	private FieldMetadata<LocalDate> datePlanifiee = new FieldMetadata<>("Date(Plan.)");
	
	private FieldMetadata<String> raceEffective = new FieldMetadata<>("Race(Eff.)");

	private FieldMetadata<Double> quantiteEffective = new FieldMetadata<>("Qté(Eff.)");

	private FieldMetadata<LocalDate> dateEffective = new FieldMetadata<>("Date(Eff.)");
	
	private List<MetadataBean> metadatas = new ArrayList<>();

	public void setMagasin(MagasinBean magasin) {
		Objects.requireNonNull(magasin);
		this.magasin = magasin;
	}
}
