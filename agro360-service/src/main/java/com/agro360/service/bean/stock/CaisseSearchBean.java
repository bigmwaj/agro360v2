package com.agro360.service.bean.stock;

import java.time.LocalDate;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.stock.StatusCaisseEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CaisseSearchBean extends AbstractBean{

	private static final long serialVersionUID = 1712894151919005098L;

	private FieldMetadata<LocalDate> journeeDebut = new FieldMetadata<>("Journée(Début)");
	
	private FieldMetadata<LocalDate> journeeFin = new FieldMetadata<>("Journée(Fin)");
	
	private FieldMetadata<String> magasin = new FieldMetadata<>("Magasin");
	
	private FieldMetadata<String> agent = new FieldMetadata<>("Agent");

	private FieldMetadata<StatusCaisseEnumVd> status = new FieldMetadata<>("Statut");
	
}
