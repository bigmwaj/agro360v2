package com.agro360.dto.vente;

import com.agro360.dto.common.AbstractLigneDto;
import com.agro360.dto.stock.CasierDto;
import com.agro360.dto.stock.MagasinDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "VENTE_TBL_COMMANDE_LIGNE")
public class LigneDto extends AbstractLigneDto {

	@ManyToOne()
	@JoinColumn(name = "COMMANDE_CODE", nullable = false, updatable = false)
	private CommandeDto commande;

	@ManyToOne()
	@JoinColumn(name = "MAGASIN_CODE")
	private MagasinDto magasin;

	@Column(name = "CASIER_CODE", length = CasierDto.CASIER_CODE_LENGTH)
	private String casierCode;

	@Column(name = "NON_FACTURABLE")
	private Boolean nonFacturable;

	@Column(name = "NON_EMBALLEE")
	private Boolean nonEmballee;

	@Column(name = "NON_CARTONNEE")
	private Boolean nonCartonnee;

}
