package com.agro360.service.bean.achat;

import java.time.LocalDateTime;

import com.agro360.service.bean.common.AbstractEditFormBean;
import com.agro360.service.bean.stock.MagasinBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.achat.StatutReceptionEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ReceptionBean extends AbstractEditFormBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<Long> receptionId = new FieldMetadata<>();

	private LigneBean ligne = new LigneBean();

	private FieldMetadata<String> description = new FieldMetadata<>();

	private FieldMetadata<StatutReceptionEnumVd> statut = new FieldMetadata<>();

	private FieldMetadata<Double> prixUnitaire = new FieldMetadata<>();

	private FieldMetadata<Double> quantite = new FieldMetadata<>();

	private FieldMetadata<LocalDateTime> dateReception = new FieldMetadata<>();

	private MagasinBean magasin = new MagasinBean();

	private FieldMetadata<String> casierCode = new FieldMetadata<>();
	
}