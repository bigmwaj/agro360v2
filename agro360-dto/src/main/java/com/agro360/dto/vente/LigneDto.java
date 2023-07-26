package com.agro360.dto.vente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.agro360.dto.common.AbstractLineDto;
import com.agro360.dto.stock.CasierDto;
import com.agro360.dto.stock.MagasinDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "VENTE_TBL_COMMANDE_LIGNE")
public class LigneDto extends AbstractLineDto
{

	@ManyToOne()
	@JoinColumn(name = "COMMANDE_CODE", nullable = false, updatable = false)
	private CommandeDto commande;

	@ManyToOne()
	@JoinColumn(name = "MAGASIN_CODE", nullable = false)
	private MagasinDto magasin;

	@Column(name = "CASIER_CODE", length = CasierDto.CODE_LENGTH)
	private String casierCode;

	@Column(name = "NON_FACTURABLE")
	private Boolean nonFacturable;

	@Column(name = "NON_EMBALLEE")
	private Boolean nonEmballee;

	@Column(name = "NON_CARTONNEE")
	private Boolean nonCartonnee;

}
