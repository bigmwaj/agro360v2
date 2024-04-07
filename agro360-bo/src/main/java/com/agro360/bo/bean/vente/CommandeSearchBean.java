package com.agro360.bo.bean.vente;

import java.time.LocalDate;
import java.util.List;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.vente.CommandeStatusEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CommandeSearchBean extends AbstractBean{

	private static final long serialVersionUID = 355487810396906725L;

	private FieldMetadata<String> commandeCode = new FieldMetadata<>("Code");

	private FieldMetadata<String> client = new FieldMetadata<>("Client");

	private FieldMetadata<List<CommandeStatusEnumVd>> statusIn = new FieldMetadata<>("Statut");

	private FieldMetadata<LocalDate> dateDebut = new FieldMetadata<>("Date Commande(Début)");
	
	private FieldMetadata<LocalDate> dateFin = new FieldMetadata<>("Date Commande(Fin)");

	private FieldMetadata<String> ville = new FieldMetadata<>("Ville");

}
