package com.agro360.dto.achat;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.agro360.dto.common.AbstractLigneDto;

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
