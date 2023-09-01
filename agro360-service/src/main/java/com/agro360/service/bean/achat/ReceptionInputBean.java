package com.agro360.service.bean.achat;

import java.time.LocalDateTime;
import java.util.Objects;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.bean.stock.MagasinBean;
import com.agro360.service.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ReceptionInputBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private LigneBean ligne = new LigneBean();

	private FieldMetadata<String> description = new FieldMetadata<>();

	private FieldMetadata<Double> prixUnitaire = new FieldMetadata<>();

	private FieldMetadata<Double> quantite = new FieldMetadata<>();

	private FieldMetadata<LocalDateTime> dateReception = new FieldMetadata<>();

	private MagasinBean magasin = new MagasinBean();

	private FieldMetadata<String> casierCode = new FieldMetadata<>();
	
	public void setMagasin(MagasinBean magasin) {
		Objects.requireNonNull(magasin);
		this.magasin = magasin;
	}
	
	public void setLigne(LigneBean ligne) {
		Objects.requireNonNull(ligne);
		this.ligne = ligne;
	}
	
}
