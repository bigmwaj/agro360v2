package com.agro360.dto.achat;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.agro360.dto.common.AbstractDto;
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

	@Column(name = "DATE_COMMANDE_CODE", nullable = false)
	private LocalDateTime dateBonCommande;

	@ManyToOne()
	@JoinColumn(name = "FOURNISSEUR_CODE", nullable = false)
	private TiersDto fournisseur;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUT", nullable = false, length = 4)
	private StatutBonCommandeEnumVd statut;

	@Column(name = "DESCRIPTION", length = 255)
	private String description;

	@Column(name = "VILLE", nullable = false, length = 16)
	private String ville;

	@Column(name = "ADRESSE", nullable = false, length = 64)
	private String adresse;

}