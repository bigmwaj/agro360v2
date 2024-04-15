package com.agro360.dto.av;

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
@Entity(name = "AV_TBL_LIGNE_TAXE")
@IdClass(LigneTaxePk.class)
public class LigneTaxeDto extends AbstractDto {
	
	@Id
	@Column(name = "COMMANDE_CODE", updatable = false, length = 16)
	@EqualsAndHashCode.Include()
	private String commandeCode;
	
	@Id
	@Column(name = "LIGNE_ID", nullable = false, updatable = false)
	@EqualsAndHashCode.Include()
	private Long ligneId;
	
	@Id
	@EqualsAndHashCode.Include()
	@JoinColumn(name = "TAXE_CODE")
	@ManyToOne()
	private TaxeDto taxe;
	
	/**
	 * Pour permettre qu'on modifie le taux de la taxe source sans impacté la valeur d'usage actuelle
	 */
	@Column(name = "TAUX", nullable = false)
	private Double taux; 
	
}
