package com.agro360.bo.bean.av;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.agro360.bo.bean.common.AbstractStatusTrackingBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.achat.ReceptionStatusEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ReceptionBean extends AbstractStatusTrackingBean<ReceptionStatusEnumVd> {

	private static final long serialVersionUID = -16328407145183398L;

	@Setter
	private LigneBean ligne = new LigneBean();

	private FieldMetadata<String> description = new FieldMetadata<>();

	private FieldMetadata<BigDecimal> prixUnitaire = new FieldMetadata<>();

	private FieldMetadata<Double> quantite = new FieldMetadata<>();

	private FieldMetadata<LocalDateTime> dateReception = new FieldMetadata<>();

	private FieldMetadata<Long> receptionId = new FieldMetadata<>();
	
	private FieldMetadata<ReceptionStatusEnumVd> status = new FieldMetadata<>();

}
