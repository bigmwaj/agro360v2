package com.agro360.service.bean.stock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.agro360.service.bean.common.AbstractStatusTrackingBean;
import com.agro360.service.bean.tiers.TiersBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.stock.StatusCaisseEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CaisseBean extends AbstractStatusTrackingBean<StatusCaisseEnumVd> {

	private static final long serialVersionUID = 1647090333349627006L;
	
	private MagasinBean magasin = new MagasinBean();
	
	private TiersBean agent = new TiersBean();

	private FieldMetadata<LocalDate> journee = new FieldMetadata<>("Journée");

	private FieldMetadata<StatusCaisseEnumVd> status = new FieldMetadata<>("Statut", getOptionsMap(StatusCaisseEnumVd.values(), StatusCaisseEnumVd::getLibelle));

	private FieldMetadata<String> note = new FieldMetadata<>("Note");
	
	private FieldMetadata<Double> depense = new FieldMetadata<>("Dépense");	
	
	private FieldMetadata<Double> recette = new FieldMetadata<>("Recette");	
	
	private FieldMetadata<Double> solde = new FieldMetadata<>("Solde");	

	private FieldMetadata<String> plusVendus = new FieldMetadata<>("Les plus vendus");
	
	private FieldMetadata<String> plusAchetes = new FieldMetadata<>("Les plus achetés");

	private List<OperationCaisseBean> operations = new ArrayList<>();

	private FieldMetadata<String> ajouterOperation = new FieldMetadata<>();
	
	private FieldMetadata<String> delete = new FieldMetadata<>();
	
	private FieldMetadata<String> changeStatus = new FieldMetadata<>();
	
	public void setMagasin(MagasinBean magasin) {
		Objects.requireNonNull(magasin);
		this.magasin = magasin;
	}
	
	public void setAgent(TiersBean agent) {
		Objects.requireNonNull(agent);
		this.agent = agent;
	}
}
