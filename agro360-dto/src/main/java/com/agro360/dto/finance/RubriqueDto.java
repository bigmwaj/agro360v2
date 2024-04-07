package com.agro360.dto.finance;

import com.agro360.dto.common.AbstractDto;
import com.agro360.vd.finance.TransactionTypeEnumVd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "FIN_TBL_RUBRIQUE")
public class RubriqueDto extends AbstractDto{

	@Id
	@Column(name = "RUBRIQUE_CODE", updatable = false, length = 16)
	@EqualsAndHashCode.Include()
	private String rubriqueCode;

	@Column(name = "NOM", length = 32)
	private String nom;

	@Column(name = "DESCRIPTION", length = 128)
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TRANSACTION_TYPE", nullable = false, updatable = false, length = TransactionTypeEnumVd.COLUMN_LENGTH)
	private TransactionTypeEnumVd type;
}
