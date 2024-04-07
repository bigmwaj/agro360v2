package com.agro360.bo.bean.achat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.agro360.bo.bean.common.AbstractStatusTrackingBean;
import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.achat.BonCommandeStatusEnumVd;
import com.agro360.vd.common.EditActionEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class BonCommandeBean extends AbstractStatusTrackingBean<BonCommandeStatusEnumVd> {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> bonCommandeCode = new FieldMetadata<>("Code");

	private FieldMetadata<Boolean> livraison = new FieldMetadata<>("Livraison?");

	private FieldMetadata<LocalDate> dateBonCommande = new FieldMetadata<>("Date");

	private MagasinBean magasin = new MagasinBean();

	private PartnerBean fournisseur = new PartnerBean();

	private FieldMetadata<BonCommandeStatusEnumVd> status = new FieldMetadata<>("Statut", getOptionsMap(BonCommandeStatusEnumVd.values(), BonCommandeStatusEnumVd::getLibelle));

	private FieldMetadata<String> description = new FieldMetadata<>("Description");
	
	private FieldMetadata<Double> prixTotal = new FieldMetadata<>("Prix Total");
	
	private FieldMetadata<String> plusAchetes = new FieldMetadata<>("Les plus achetés");

	private List<LigneBean> lignes = new ArrayList<>();

	public void setFournisseur(PartnerBean fournisseur) {
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
		getStatus().setValue(BonCommandeStatusEnumVd.BRLN);
		getStatusDate().setValue(LocalDateTime.now());

		getLignes().stream().forEach(e -> e.initForCreateForm());
	}
}
