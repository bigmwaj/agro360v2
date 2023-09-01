package com.agro360.service.bean.vente;

import java.time.LocalDate;
import java.util.List;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.vente.StatusCommandeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CommandeSearchBean extends AbstractBean{

	private static final long serialVersionUID = 355487810396906725L;

	private FieldMetadata<String> commandeCode = new FieldMetadata<>("Code");

	private FieldMetadata<String> client = new FieldMetadata<>("Client");

	private FieldMetadata<List<StatusCommandeEnumVd>> statusIn = new FieldMetadata<>("Statut");

	private FieldMetadata<LocalDate> dateCommandeDebut = new FieldMetadata<>("Date Commande(Début)");
	
	private FieldMetadata<LocalDate> dateCommandeFin = new FieldMetadata<>("Date Commande(Fin)");

	private FieldMetadata<String> ville = new FieldMetadata<>("Ville");

}
