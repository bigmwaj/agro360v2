package com.agro360.bo.bean.av;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.agro360.bo.bean.common.AbstractStatusTrackingBean;
import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.av.CommandeStatusEnumVd;
import com.agro360.vd.av.CommandeTypeEnumVd;

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

	private FieldMetadata<BigDecimal> cumulPaiement = new FieldMetadata<>("Cumul Paiement");

	private FieldMetadata<BigDecimal> prixTotalHT = new FieldMetadata<>("Prix Total(HT)");

	private FieldMetadata<BigDecimal> prixTotal = new FieldMetadata<>("Prix Total + Taxe - Remise");

	private FieldMetadata<BigDecimal> taxe = new FieldMetadata<>("Taxe");

	private FieldMetadata<BigDecimal> remise = new FieldMetadata<>("Remise Totale");

	private FieldMetadata<String> createLigneBtn = new FieldMetadata<>();

	private FieldMetadata<String> reglerBtn = new FieldMetadata<>();
	
	private FieldMetadata<String> terminerBtn = new FieldMetadata<>();
	
	private FieldMetadata<String> annulerBtn = new FieldMetadata<>();

	private FieldMetadata<String> receptionBtn = new FieldMetadata<>("Réception ligne");

	private FieldMetadata<String> retourBtn = new FieldMetadata<>("Retour ligne");

	private List<LigneBean> lignes = new ArrayList<>();
	
	@Setter
	private MagasinBean magasin = new MagasinBean();

	@Setter
	private PartnerBean partner = new PartnerBean();
}
