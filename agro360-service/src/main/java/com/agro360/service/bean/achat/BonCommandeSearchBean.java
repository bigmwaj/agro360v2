package com.agro360.service.bean.achat;

import java.time.LocalDate;
import java.util.List;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.achat.StatusBonCommandeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class BonCommandeSearchBean extends AbstractBean{

	private static final long serialVersionUID = 355487810396906725L;

	private FieldMetadata<String> bonCommandeCode = new FieldMetadata<>("Code");
	
	private FieldMetadata<String> fournisseur = new FieldMetadata<>("Fournisseur");

	private FieldMetadata<List<StatusBonCommandeEnumVd>> statusIn = new FieldMetadata<>("Statuts dans",
			getOptionsMap(StatusBonCommandeEnumVd.values(), StatusBonCommandeEnumVd::getLibelle));

	private FieldMetadata<String> ville = new FieldMetadata<>("Ville");

	private FieldMetadata<LocalDate> dateBonCommandeDebut = new FieldMetadata<>("Date de Commande(début)");
	
	private FieldMetadata<LocalDate> dateBonCommandeFin = new FieldMetadata<>("Date de Commande(fin)");
	
}
