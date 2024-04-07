package com.agro360.bo.bean.av;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.agro360.bo.bean.common.AbstractStatusTrackingBean;
import com.agro360.bo.bean.core.PartnerBean;
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

	private FieldMetadata<String> factureCode = new FieldMetadata<>("Code");

	private FieldMetadata<LocalDate> date = new FieldMetadata<>("Date");

	private FieldMetadata<FactureTypeEnumVd> type = new FieldMetadata<>("Type", FactureTypeEnumVd.getAsMap());
	
	private FieldMetadata<FactureStatusEnumVd> status = new FieldMetadata<>("Statut", FactureStatusEnumVd.getAsMap());

	private FieldMetadata<String> description = new FieldMetadata<>("Description");
	
	private FieldMetadata<BigDecimal> montant = new FieldMetadata<>("Montant");

	@Setter
	private PartnerBean partner = new PartnerBean();

	@Setter
	private CommandeBean commande = new CommandeBean();
	

}
