package com.agro360.dto.achat;

import com.agro360.dto.common.AbstractLigneDto;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "ACHAT_TBL_BC_LIGNE")
public class LigneDto extends AbstractLigneDto {
	
	@ManyToOne()
	@JoinColumn(name = "BON_COMMANDE_CODE", nullable = false, updatable = false)
	private BonCommandeDto bonCommande;

}
