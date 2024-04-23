package com.agro360.bo.bean.stock;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.stock.OperationTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class OperationBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<Long> operationId = new FieldMetadata<>("ID");

	private FieldMetadata<String> magasinCode = new FieldMetadata<>("Magasin");

	private FieldMetadata<String> articleCode = new FieldMetadata<>("Article");

	private FieldMetadata<String> variantCode = new FieldMetadata<>("Variant");

	private FieldMetadata<BigDecimal> prixUnitaire = new FieldMetadata<>("Prix Unitaire");

	private FieldMetadata<Double> inventaireAvant = new FieldMetadata<>("Inventaire Avant");

	private FieldMetadata<Double> quantite = new FieldMetadata<>("Quantité");

	private FieldMetadata<LocalDateTime> date = new FieldMetadata<>("Date");

	private FieldMetadata<OperationTypeEnumVd> type = new FieldMetadata<>("Type", OperationTypeEnumVd.getAsMap());
}
