package com.agro360.bo.bean.av;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.agro360.bo.bean.common.AbstractStatusTrackingBean;
import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.finance.CompteBean;
import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.av.CommandeStatusEnumVd;
import com.agro360.vd.av.CommandeTypeEnumVd;
import com.agro360.vd.common.EditActionEnumVd;

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
	
	private FieldMetadata<BigDecimal> prixTotal = new FieldMetadata<>("Prix Total");
	
	private FieldMetadata<BigDecimal> paiementComptant = new FieldMetadata<>("Paiement Comptant");

	private List<LigneBean> lignes = new ArrayList<>();

	@Setter
	private MagasinBean magasin = new MagasinBean();

	@Setter
	private PartnerBean partner = new PartnerBean();
	
	@Setter
	private CompteBean compte = new CompteBean();
	
	public void initForCreateForm() {
		setAction(EditActionEnumVd.CREATE);
		getCommandeCode().setValue(null);
		getDate().setValue(LocalDate.now());
		getStatus().setValue(CommandeStatusEnumVd.BRLN);
		getStatusDate().setValue(LocalDateTime.now());

		getLignes().stream().forEach(e -> e.initForCreateForm());
	}
}
