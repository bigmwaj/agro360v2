package com.agro360.service.bean.achat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.agro360.service.bean.common.AbstractEditFormBean;
import com.agro360.service.bean.tiers.TiersBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.achat.StatutBonCommandeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class BonCommandeBean extends AbstractEditFormBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String>  bonCommandeCode = new FieldMetadata<>();

	private FieldMetadata<Boolean> livraison = new FieldMetadata<>();

	private FieldMetadata<LocalDate> dateBonCommande = new FieldMetadata<>();

	private TiersBean fournisseur = new TiersBean();

	private FieldMetadata<StatutBonCommandeEnumVd> statut = new FieldMetadata<>();

	private FieldMetadata<String> description = new FieldMetadata<>();

	private FieldMetadata<String>  ville = new FieldMetadata<>();

	private FieldMetadata<String>  adresse = new FieldMetadata<>();
	
	private List<LigneBean> lignes = new ArrayList<>();
	
	
	public void setFournisseur(TiersBean fournisseur) {
		Objects.requireNonNull(fournisseur);
		this.fournisseur = fournisseur;
	}
}
