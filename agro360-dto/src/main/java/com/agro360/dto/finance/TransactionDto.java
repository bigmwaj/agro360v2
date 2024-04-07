package com.agro360.dto.finance;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.dto.core.PartnerDto;
import com.agro360.vd.finance.TransactionStatusEnumVd;
import com.agro360.vd.finance.TransactionTypeEnumVd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "FIN_TBL_TRANSACTION")
public class TransactionDto extends AbstractStatusTrackingDto<TransactionStatusEnumVd>{

	@Id
	@Column(name = "TRANSACTION_CODE", updatable = false, length = 20)
	@EqualsAndHashCode.Include()
	private String transactionCode;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = TransactionStatusEnumVd.COLUMN_LENGTH)
	private TransactionStatusEnumVd status;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TRANSACTION_TYPE", nullable = false, updatable = false, length = TransactionTypeEnumVd.COLUMN_LENGTH)
	private TransactionTypeEnumVd type;

	@ManyToOne()
	@JoinColumn(name = "PARTNER_CODE", updatable = false, nullable = false)
	private PartnerDto partner;
	
	@ManyToOne()
	@JoinColumn(name = "COMPTE_CODE", updatable = false, nullable = false)
	private CompteDto compte;
	
	@ManyToOne()
	@JoinColumn(name = "RUBRIQUE_CODE", updatable = false, nullable = false)
	private RubriqueDto rubrique;

	@Column(name = "TRANSACTION_DATE",  nullable = false)
	@EqualsAndHashCode.Include()
	private LocalDate date;

	@Column(name = "NOTE", length = 128)
	private String note;
	
	@Column(name = "MONTANT", nullable = false, precision = 16, scale = 4)
	private BigDecimal montant;
}
