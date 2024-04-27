package com.agro360.bo.bean.finance;

import java.time.LocalDate;
import java.util.List;

import com.agro360.bo.bean.common.AbstractSearchBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.finance.TransactionStatusEnumVd;
import com.agro360.vd.finance.TransactionTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class TransactionSearchBean extends AbstractSearchBean{

	private static final long serialVersionUID = -1273209527092665188L;

	private FieldMetadata<String> transactionCode = new FieldMetadata<>("Transaction");

	private FieldMetadata<TransactionTypeEnumVd> type = new FieldMetadata<>("Type", TransactionTypeEnumVd.getAsMap());

	private FieldMetadata<List<TransactionStatusEnumVd>> statusIn = new FieldMetadata<>("Statut", TransactionStatusEnumVd.getAsMap());

	private FieldMetadata<LocalDate> dateDebut = new FieldMetadata<>("Date(début)");

	private FieldMetadata<LocalDate> dateFin = new FieldMetadata<>("Date(fin)");
	
	private FieldMetadata<String> partner = new FieldMetadata<>("Partenaire");

	private FieldMetadata<String> rubrique = new FieldMetadata<>("Rubrique");

	private FieldMetadata<String> compte = new FieldMetadata<>("Compte");
	
	private FieldMetadata<String> transfertBtn = new FieldMetadata<>();
	
	private FieldMetadata<String> rubriqueBtn = new FieldMetadata<>();
	
	private FieldMetadata<String> compteBtn = new FieldMetadata<>();
	
	private FieldMetadata<String> taxeBtn = new FieldMetadata<>();

}
