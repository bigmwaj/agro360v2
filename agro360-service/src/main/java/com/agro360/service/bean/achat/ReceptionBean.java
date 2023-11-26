package com.agro360.service.bean.achat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

import com.agro360.service.bean.common.AbstractStatusTrackingBean;
import com.agro360.service.bean.stock.MagasinBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.achat.StatusReceptionEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ReceptionBean extends AbstractStatusTrackingBean<StatusReceptionEnumVd> {

	private static final long serialVersionUID = -16328407145183398L;

	private LigneBean ligne = new LigneBean();

	private FieldMetadata<String> description = new FieldMetadata<>();

	private FieldMetadata<BigDecimal> prixUnitaire = new FieldMetadata<>();

	private FieldMetadata<Double> quantite = new FieldMetadata<>();

	private FieldMetadata<LocalDateTime> dateReception = new FieldMetadata<>();

	private MagasinBean magasin = new MagasinBean();

	private FieldMetadata<String> casierCode = new FieldMetadata<>();
	
	private FieldMetadata<Long> receptionId = new FieldMetadata<>();
	
	private FieldMetadata<StatusReceptionEnumVd> status = new FieldMetadata<>();

	public void setMagasin(MagasinBean magasin) {
		Objects.requireNonNull(magasin);
		this.magasin = magasin;
	}

	public void setLigne(LigneBean ligne) {
		Objects.requireNonNull(ligne);
		this.ligne = ligne;
	}

}
