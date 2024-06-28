package com.agro360.dto.av;

import java.math.BigDecimal;

import com.agro360.dto.common.AbstractDto;
import com.agro360.dto.finance.TaxeDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "AV_TBL_FACTURE_TAXE")
@IdClass(FactureTaxePk.class)
public class FactureTaxeDto extends AbstractDto {

	@Id
	@Column(name = "FACTURE_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String factureCode;

	@Id
	@EqualsAndHashCode.Include()
	@JoinColumn(name = "TAXE_CODE", updatable = false)
	@ManyToOne()
	private TaxeDto taxe;

	/**
	 * Historisation
	 */
	@Column(name = "TAUX")
	private Double taux;

	@Column(name = "MONTANT")
	private BigDecimal montant;

}
