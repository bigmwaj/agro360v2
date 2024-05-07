package com.agro360.dto.av;

import com.agro360.dto.common.AbstractDto;
import com.agro360.dto.finance.TransactionDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "AV_TBL_REG_FACT")
public class ReglementFactureDto extends AbstractDto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "REG_ID", updatable = false)
	@EqualsAndHashCode.Include()
	private Long reglementId;
	
	@Column(name = "FACTURE_CODE", updatable = false)
	private String factureCode;
	
	@ManyToOne()
	@JoinColumn(name = "TRANSACTION_CODE", updatable = false)
	private TransactionDto transaction;

}
