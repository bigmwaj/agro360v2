package com.agro360.bo.bean.av;

import java.time.LocalDate;
import java.util.List;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.av.CommandeStatusEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CommandeSearchBean extends AbstractBean{

	private static final long serialVersionUID = 355487810396906725L;

	private FieldMetadata<String> commandeCode = new FieldMetadata<>("Code");
	
	private FieldMetadata<String> partner = new FieldMetadata<>("Partenaire");

	private FieldMetadata<List<CommandeStatusEnumVd>> statusIn = new FieldMetadata<>("Statuts dans", CommandeStatusEnumVd.getAsMap());

	private FieldMetadata<String> ville = new FieldMetadata<>("Ville");

	private FieldMetadata<LocalDate> dateDebut = new FieldMetadata<>("Date de Commande(début)");
	
	private FieldMetadata<LocalDate> dateFin = new FieldMetadata<>("Date de Commande(fin)");
	
}
