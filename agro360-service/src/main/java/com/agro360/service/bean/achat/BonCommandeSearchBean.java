package com.agro360.service.bean.achat;

import java.time.LocalDate;
import java.util.List;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.achat.StatutBonCommandeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class BonCommandeSearchBean extends AbstractBean{

	private static final long serialVersionUID = 355487810396906725L;

	private FieldMetadata<String> bonCommandeCode = new FieldMetadata<>();
	
	private FieldMetadata<String> fournisseur = new FieldMetadata<>();

	private FieldMetadata<List<StatutBonCommandeEnumVd>> statutDans = new FieldMetadata<>();

	private FieldMetadata<String> ville = new FieldMetadata<>();

	private FieldMetadata<LocalDate> dateBonCommandeDebut = new FieldMetadata<>();
	
	private FieldMetadata<LocalDate> dateBonCommandeFin = new FieldMetadata<>();
	
}
