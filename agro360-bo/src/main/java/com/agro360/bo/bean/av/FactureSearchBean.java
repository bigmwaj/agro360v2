package com.agro360.bo.bean.av;

import java.time.LocalDate;
import java.util.List;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.av.FactureStatusEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class FactureSearchBean extends AbstractBean{

	private static final long serialVersionUID = 355487810396906725L;

	private FieldMetadata<String> factureCode = new FieldMetadata<>("Code");

	private FieldMetadata<String> partner = new FieldMetadata<>("Partenaire");

	private FieldMetadata<List<FactureStatusEnumVd>> statusIn = new FieldMetadata<>("Statuts dans", FactureStatusEnumVd.getAsMap());

	private FieldMetadata<LocalDate> dateDebut = new FieldMetadata<>("Date(début)");

	private FieldMetadata<LocalDate> dateFin = new FieldMetadata<>("Date(fin)");

}
