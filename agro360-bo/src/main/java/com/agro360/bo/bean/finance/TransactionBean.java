package com.agro360.bo.bean.finance;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.agro360.bo.bean.common.AbstractStatusTrackingBean;
import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.finance.TransactionStatusEnumVd;
import com.agro360.vd.finance.TransactionTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class TransactionBean extends AbstractStatusTrackingBean<TransactionStatusEnumVd>{

	private static final long serialVersionUID = 7055886608944438654L;

	private FieldMetadata<String> transactionCode = new FieldMetadata<>("Transaction");

	private FieldMetadata<TransactionTypeEnumVd> type = new FieldMetadata<>("Type", TransactionTypeEnumVd.getAsMap());

	private FieldMetadata<LocalDate> date = new FieldMetadata<>("Date Transaction");

	private FieldMetadata<TransactionStatusEnumVd> status = new FieldMetadata<>("Status", TransactionStatusEnumVd.getAsMap());

	private FieldMetadata<String> note = new FieldMetadata<>("Note");

	private FieldMetadata<BigDecimal> montant = new FieldMetadata<>("Montant");

	private FieldMetadata<String> deleteBtn = new FieldMetadata<>();

	@Setter
	private PartnerBean partner = new PartnerBean();

	@Setter
	private RubriqueBean rubrique = new RubriqueBean();

	@Setter
	private CompteBean compte = new CompteBean();

}
