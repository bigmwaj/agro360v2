package com.agro360.dto.achat;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.agro360.dto.common.AbstractDto;
import com.agro360.dto.stock.MagasinDto;
import com.agro360.dto.tiers.TiersDto;
import com.agro360.vd.achat.StatutBonCommandeEnumVd;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "ACHAT_TBL_BC")
public class BonCommandeDto extends AbstractDto {
	@Id
	@Column(name = "BON_COMMANDE_CODE", updatable = false, length = 16)
	@EqualsAndHashCode.Include()
	private String bonCommandeCode;

	@Column(name = "LIVRAISON", updatable = false, length = 16)
	private Boolean livraison;

	@Column(name = "DATE_BON_COMMANDE", nullable = false)
	private LocalDate dateBonCommande;

	@ManyToOne()
	@JoinColumn(name = "FOURNISSEUR_CODE", nullable = false)
	private TiersDto fournisseur;
	
	@ManyToOne()
	@JoinColumn(name = "MAGASIN_CODE", nullable = false)
	private MagasinDto magasin;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUT", nullable = false, length = StatutBonCommandeEnumVd.COLUMN_LENGTH)
	private StatutBonCommandeEnumVd statut;

	@Column(name = "DESCRIPTION", length = 256)
	private String description;

}
