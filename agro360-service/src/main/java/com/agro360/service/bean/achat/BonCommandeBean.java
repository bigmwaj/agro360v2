package com.agro360.service.bean.achat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.agro360.service.bean.common.AbstractStatusTrackingBean;
import com.agro360.service.bean.stock.MagasinBean;
import com.agro360.service.bean.tiers.TiersBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.achat.StatusBonCommandeEnumVd;
import com.agro360.vd.common.EditActionEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class BonCommandeBean extends AbstractStatusTrackingBean<StatusBonCommandeEnumVd> {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> bonCommandeCode = new FieldMetadata<>("Code");

	private FieldMetadata<Boolean> livraison = new FieldMetadata<>("Livraison?");

	private FieldMetadata<LocalDate> dateBonCommande = new FieldMetadata<>("Date");

	private MagasinBean magasin = new MagasinBean();

	private TiersBean fournisseur = new TiersBean();

	private FieldMetadata<StatusBonCommandeEnumVd> status = new FieldMetadata<>("Statut", getOptionsMap(StatusBonCommandeEnumVd.values(), StatusBonCommandeEnumVd::getLibelle));

	private FieldMetadata<String> description = new FieldMetadata<>("Description");
	
	private FieldMetadata<Double> prixTotal = new FieldMetadata<>("Prix Total");
	
	private FieldMetadata<String> plusAchetes = new FieldMetadata<>("Les plus achetés");

	private List<LigneBean> lignes = new ArrayList<>();

	public void setFournisseur(TiersBean fournisseur) {
		Objects.requireNonNull(fournisseur);
		this.fournisseur = fournisseur;
	}

	public void setMagasin(MagasinBean magasin) {
		Objects.requireNonNull(magasin);
		this.magasin = magasin;
	}
	
	public void initForCreateForm() {
		setAction(EditActionEnumVd.CREATE);
		getBonCommandeCode().setValue(null);
		getDateBonCommande().setValue(LocalDate.now());
		getStatus().setValue(StatusBonCommandeEnumVd.BRLN);
		getStatusDate().setValue(LocalDateTime.now());

		getLignes().stream().forEach(e -> e.initForCreateForm());
	}
}
