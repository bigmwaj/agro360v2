package com.agro360.dto.stock;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.agro360.dto.common.AbstractDto;
import com.agro360.vd.stock.OperationTypeEnumVd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "STOCK_TBL_OPERATION")
public class OperationDto extends AbstractDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OPERATION_ID", nullable = false, updatable = false)
	@EqualsAndHashCode.Include()
	private Long operationId;
	
	@Column(name = "MAGASIN_CODE", updatable = false, nullable = false)
	private String magasinCode;
	
	@Column(name = "ARTICLE_CODE", updatable = false, nullable = false)
	private String articleCode;
	
	@Column(name = "VARIANT_CODE", updatable = false, nullable = false)
	private String variantCode;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "OPERATION_TYPE", nullable = false, updatable = false, length = OperationTypeEnumVd.COLUMN_LENGTH)
	private OperationTypeEnumVd type;
	
	@Column(name = "OPERATION_DATE", updatable = false, nullable = false)
	private LocalDateTime date;

	@Column(name = "INVENTAIRE_AVANT", nullable = false)
	private Double inventaireAvant;
	
	@Column(name = "QUANTITE", nullable = false)
	private Double quantite;
	
	@Column(name = "PRIX_UNITAIRE", precision = 16, scale = 4)
	private BigDecimal prixUnitaire;

}
