package com.agro360.bo.bean.stock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.agro360.bo.bean.common.AbstractStatusTrackingBean;
import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.stock.CaisseStatusEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CaisseBean extends AbstractStatusTrackingBean<CaisseStatusEnumVd> {

	private static final long serialVersionUID = 1647090333349627006L;
	
	private MagasinBean magasin = new MagasinBean();
	
	private PartnerBean Partner = new PartnerBean();

	private FieldMetadata<LocalDate> journee = new FieldMetadata<>("Journée");

	private FieldMetadata<CaisseStatusEnumVd> status = new FieldMetadata<>("Statut", getOptionsMap(CaisseStatusEnumVd.values(), CaisseStatusEnumVd::getLibelle));

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
	
	public void setPartner(PartnerBean Partner) {
		Objects.requireNonNull(Partner);
		this.Partner = Partner;
	}
}
