package com.agro360.bo.bean.av;

import java.math.BigDecimal;

import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.av.FactureTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class EtatDetteBean {
	
	public EtatDetteBean(FactureTypeEnumVd type, BigDecimal montant) {
		this.type.setValue(type);
		this.montant.setValue(montant);
		
		switch (type) {
		case ACHAT:
			libelle.setValue("Dettes Fournisseurs");
			break;
		case VENTE:
			libelle.setValue("Dettes Clients");
			break;

		default:
			break;
		}
	}

	private FieldMetadata<String> libelle = new FieldMetadata<>("Libelle");
	
	private FieldMetadata<FactureTypeEnumVd> type = new FieldMetadata<>("Type");

	private FieldMetadata<BigDecimal> montant = new FieldMetadata<>("Montant");

}
