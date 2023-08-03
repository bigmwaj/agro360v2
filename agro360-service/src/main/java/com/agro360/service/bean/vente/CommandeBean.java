package com.agro360.service.bean.vente;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.agro360.service.bean.common.AbstractEditFormBean;
import com.agro360.service.bean.stock.MagasinBean;
import com.agro360.service.bean.tiers.TiersBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.vente.StatutCommandeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CommandeBean extends AbstractEditFormBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String>  commandeCode = new FieldMetadata<>();

	private FieldMetadata<LocalDate> dateCommande = new FieldMetadata<>();

	private TiersBean client = new TiersBean();

	private MagasinBean magasin = new MagasinBean();

	private FieldMetadata<Boolean> transportRequis = new FieldMetadata<>();

	private FieldMetadata<Boolean> livree = new FieldMetadata<>();

	private FieldMetadata<StatutCommandeEnumVd> statut = new FieldMetadata<>();

	private FieldMetadata<String> description = new FieldMetadata<>();

	private FieldMetadata<String> ville = new FieldMetadata<>();

	private FieldMetadata<String> adresse = new FieldMetadata<>();
	
	private List<LigneBean> lignes = new ArrayList<>();
	
	public void setClient(TiersBean client) {
		Objects.requireNonNull(client);
		this.client = client;
	}
	
	public void setMagasin(MagasinBean magasin) {
		Objects.requireNonNull(magasin);
		this.magasin = magasin;
	}
	
}
