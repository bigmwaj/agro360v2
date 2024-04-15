package com.agro360.bo.bean.av;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.agro360.bo.bean.common.AbstractStatusTrackingBean;
import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.finance.CompteBean;
import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.av.CommandeStatusEnumVd;
import com.agro360.vd.av.CommandeTypeEnumVd;
import com.agro360.vd.av.RemiseTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CommandeBean extends AbstractStatusTrackingBean<CommandeStatusEnumVd> {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> commandeCode = new FieldMetadata<>("Commande");

	private FieldMetadata<LocalDate> date = new FieldMetadata<>("Date");

	private FieldMetadata<CommandeTypeEnumVd> type = new FieldMetadata<>("Type", CommandeTypeEnumVd.getAsMap());
	
	private FieldMetadata<CommandeStatusEnumVd> status = new FieldMetadata<>("Statut", CommandeStatusEnumVd.getAsMap());

	private FieldMetadata<String> description = new FieldMetadata<>("Description");	
	
	private FieldMetadata<BigDecimal> paiementComptant = new FieldMetadata<>("Paiement Comptant");
	
	private FieldMetadata<RemiseTypeEnumVd> remiseType = new FieldMetadata<>("Type Remise", RemiseTypeEnumVd.getAsMap());
	
	private FieldMetadata<Double> remiseTaux = new FieldMetadata<>("Taux Remise");
	
	private FieldMetadata<BigDecimal> remiseMontant = new FieldMetadata<>("Montant Remise");
	
	private FieldMetadata<String> remiseRaison = new FieldMetadata<>("Raison Remise");
	
	private FieldMetadata<BigDecimal> prixTotalHT = new FieldMetadata<>("Prix Total(HT)");
	
	private FieldMetadata<BigDecimal> prixTotalTTC = new FieldMetadata<>("Prix Total(TTC)");
	
	private FieldMetadata<BigDecimal> prixTotal = new FieldMetadata<>("Prix Total");
	
	private FieldMetadata<BigDecimal> taxe = new FieldMetadata<>("Taxe");
	
	private FieldMetadata<BigDecimal> remise = new FieldMetadata<>("Remise Totale");
	
	private List<LigneBean> lignes = new ArrayList<>();

	@Setter
	private MagasinBean magasin = new MagasinBean();

	@Setter
	private PartnerBean partner = new PartnerBean();
	
	@Setter
	private CompteBean compte = new CompteBean();
}
