package com.agro360.bo.bean.av;

import java.time.LocalDateTime;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ReceptionInputBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> description = new FieldMetadata<>();

	private FieldMetadata<Double> prixUnitaire = new FieldMetadata<>();

	private FieldMetadata<Double> quantite = new FieldMetadata<>();

	private FieldMetadata<LocalDateTime> dateReception = new FieldMetadata<>();

	private FieldMetadata<String> casierCode = new FieldMetadata<>();

	@Setter
	private LigneBean ligne = new LigneBean();

	@Setter
	private MagasinBean magasin = new MagasinBean();

}
