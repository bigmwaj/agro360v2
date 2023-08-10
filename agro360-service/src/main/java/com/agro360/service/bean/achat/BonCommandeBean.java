package com.agro360.service.bean.achat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.agro360.service.bean.common.AbstractFormBean;
import com.agro360.service.bean.stock.MagasinBean;
import com.agro360.service.bean.tiers.TiersBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.achat.StatutBonCommandeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class BonCommandeBean extends AbstractFormBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> bonCommandeCode = new FieldMetadata<>();

	private FieldMetadata<Boolean> livraison = new FieldMetadata<>();

	private FieldMetadata<LocalDate> dateBonCommande = new FieldMetadata<>();

	private MagasinBean magasin = new MagasinBean();

	private TiersBean fournisseur = new TiersBean();

	private FieldMetadata<StatutBonCommandeEnumVd> statut = new FieldMetadata<>();

	private FieldMetadata<String> description = new FieldMetadata<>();

	private List<LigneBean> lignes = new ArrayList<>();

	public void setFournisseur(TiersBean fournisseur) {
		Objects.requireNonNull(fournisseur);
		this.fournisseur = fournisseur;
	}

	public void setMagasin(MagasinBean magasin) {
		Objects.requireNonNull(magasin);
		this.magasin = magasin;
	}
}
