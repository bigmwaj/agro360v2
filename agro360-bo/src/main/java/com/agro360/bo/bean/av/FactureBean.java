package com.agro360.bo.bean.av;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.agro360.bo.bean.common.AbstractStatusTrackingBean;
import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.finance.CompteBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.av.FactureStatusEnumVd;
import com.agro360.vd.av.FactureTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class FactureBean extends AbstractStatusTrackingBean<FactureStatusEnumVd> {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> factureCode = new FieldMetadata<>("Facture");

	private FieldMetadata<LocalDate> date = new FieldMetadata<>("Date");

	private FieldMetadata<FactureTypeEnumVd> type = new FieldMetadata<>("Type", FactureTypeEnumVd.getAsMap());

	private FieldMetadata<FactureStatusEnumVd> status = new FieldMetadata<>("Statut", FactureStatusEnumVd.getAsMap());

	private FieldMetadata<String> description = new FieldMetadata<>("Description");

	private FieldMetadata<BigDecimal> cumulPaiement = new FieldMetadata<>("Cumul Paiement");

	private FieldMetadata<BigDecimal> prixTotalHT = new FieldMetadata<>("Prix Total(HT)");

	private FieldMetadata<BigDecimal> remise = new FieldMetadata<>("Remise Totale");

	private FieldMetadata<BigDecimal> prixTotal = new FieldMetadata<>("Prix Total - Remise + Taxes");

	private FieldMetadata<BigDecimal> taxe = new FieldMetadata<>("Taxe");

	private FieldMetadata<String> reglerBtn = new FieldMetadata<>();

	@Setter
	private PartnerBean partner = new PartnerBean();

	@Setter
	private CommandeBean commande = new CommandeBean();

	@Setter
	private CompteBean compte = new CompteBean();


}
