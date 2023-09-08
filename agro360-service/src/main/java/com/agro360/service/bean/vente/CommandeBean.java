package com.agro360.service.bean.vente;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.agro360.service.bean.common.AbstractStatusTrackingBean;
import com.agro360.service.bean.stock.MagasinBean;
import com.agro360.service.bean.tiers.TiersBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.common.EditActionEnumVd;
import com.agro360.vd.vente.StatusCommandeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CommandeBean extends AbstractStatusTrackingBean<StatusCommandeEnumVd> {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> commandeCode = new FieldMetadata<>("Code");

	private FieldMetadata<LocalDate> dateCommande = new FieldMetadata<>("Date");

	private TiersBean client = new TiersBean();

	private MagasinBean magasin = new MagasinBean();

	private FieldMetadata<Boolean> transportRequis = new FieldMetadata<>("Transport Requis?");

	private FieldMetadata<Boolean> livree = new FieldMetadata<>("Livrée?");

	private FieldMetadata<StatusCommandeEnumVd> status = new FieldMetadata<>("Statut", getOptionsMap(StatusCommandeEnumVd.values(), StatusCommandeEnumVd::getLibelle));

	private FieldMetadata<String> description = new FieldMetadata<>("Description");

	private FieldMetadata<String> ville = new FieldMetadata<>("Ville");

	private FieldMetadata<String> adresse = new FieldMetadata<>("Adresse");
	
	private FieldMetadata<Double> prixTotal = new FieldMetadata<>("Prix Total");

	private FieldMetadata<String> plusVendus = new FieldMetadata<>("Les plus vendus");

	private List<LigneBean> lignes = new ArrayList<>();

	public void setClient(TiersBean client) {
		Objects.requireNonNull(client);
		this.client = client;
	}

	public void setMagasin(MagasinBean magasin) {
		Objects.requireNonNull(magasin);
		this.magasin = magasin;
	}

	public void initForCreateForm() {
		setAction(EditActionEnumVd.CREATE);
		getCommandeCode().setValue(null);
		getDateCommande().setValue(LocalDate.now());
		getStatus().setValue(StatusCommandeEnumVd.BRLN);
		getStatusDate().setValue(LocalDateTime.now());

		getLignes().stream().forEach(e -> e.initForCreateForm());
		
		
	}

}
