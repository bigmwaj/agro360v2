package com.agro360.bo.bean.finance;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class EtatRecetteDepenseBean {
	
	private FieldMetadata<LocalDate> semaine = new FieldMetadata<>("Semaine");

	private FieldMetadata<BigDecimal> depense = new FieldMetadata<>("Dépense");
	
	private FieldMetadata<BigDecimal> recette = new FieldMetadata<>("Recette");
	
	public EtatRecetteDepenseBean(LocalDate semaine) {
		this.semaine.setValue(semaine);
	}

}
