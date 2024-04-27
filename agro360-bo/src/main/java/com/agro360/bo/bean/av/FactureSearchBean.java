package com.agro360.bo.bean.av;

import java.time.LocalDate;
import java.util.List;

import com.agro360.bo.bean.common.AbstractSearchBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.av.FactureStatusEnumVd;
import com.agro360.vd.av.FactureTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class FactureSearchBean extends AbstractSearchBean{

	private static final long serialVersionUID = 355487810396906725L;

	private FieldMetadata<String> factureCode = new FieldMetadata<>("Code");

	private FieldMetadata<String> partner = new FieldMetadata<>("Partenaire");

	private FieldMetadata<List<FactureStatusEnumVd>> statusIn = new FieldMetadata<>("Statut", FactureStatusEnumVd.getAsMap());
	
	private FieldMetadata<FactureTypeEnumVd> type = new FieldMetadata<>("Type", FactureTypeEnumVd.getAsMap());

	private FieldMetadata<LocalDate> dateDebut = new FieldMetadata<>("Date(début)");

	private FieldMetadata<LocalDate> dateFin = new FieldMetadata<>("Date(fin)");
	
	private FieldMetadata<String> createFactureBtn = new FieldMetadata<>();

}
