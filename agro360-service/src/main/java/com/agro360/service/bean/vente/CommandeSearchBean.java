package com.agro360.service.bean.vente;

import java.time.LocalDate;
import java.util.List;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.vente.StatutCommandeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CommandeSearchBean extends AbstractBean{

	private static final long serialVersionUID = 355487810396906725L;

	private FieldMetadata<String> commandeCode = new FieldMetadata<>();

	private FieldMetadata<String> client = new FieldMetadata<>();

	private FieldMetadata<List<StatutCommandeEnumVd>> statutDans = new FieldMetadata<>();

	private FieldMetadata<LocalDate> dateCommandeDebut = new FieldMetadata<>();
	
	private FieldMetadata<LocalDate> dateCommandeFin = new FieldMetadata<>();

	private FieldMetadata<String> ville = new FieldMetadata<>();

}
