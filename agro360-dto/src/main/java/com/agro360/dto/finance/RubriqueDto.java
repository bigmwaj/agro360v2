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
	@Column(name = "RUBRIQUE_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String rubriqueCode;

	@Column(name = "LIBELLE")
	private String libelle;

	@Column(name = "DESCRIPTION")
	private String description;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TRANSACTION_TYPE", updatable = false)
	private TransactionTypeEnumVd type;
}
