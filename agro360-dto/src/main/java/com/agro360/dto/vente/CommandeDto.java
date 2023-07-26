package com.agro360.dto.vente;

import java.time.LocalDateTime;

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
import com.agro360.vd.vente.StatutCommandeEnumVd;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "VENTE_TBL_COMMANDE")
public class CommandeDto extends AbstractDto
{
	@Id
	@Column(name = "COMMANDE_CODE", length = 16, nullable = false, updatable = false)
	@EqualsAndHashCode.Include()
	private String commandeCode;

	@Column(name = "DATE_COMMANDE", nullable = false)
	private LocalDateTime dateCommande;

	@ManyToOne()
	@JoinColumn(name = "CLIENT_CODE", nullable = false)
	private TiersDto client;

	@ManyToOne()
	@JoinColumn(name = "MAGASIN_CODE", nullable = false)
	private MagasinDto magasin;

	@Column(name = "TRANSPORT_REQUIS")
	private Boolean transportRequis;

	@Column(name = "LIVREE")
	private Boolean livree;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUT", nullable = false, length = 4)
	private StatutCommandeEnumVd statut;

	@Column(name = "DESCRIPTION", length = 255)
	private String description;

	@Column(name = "VILLE", nullable = false, length = 16)
	private String ville;

	@Column(name = "ADRESSE", nullable = false, length = 64)
	private String adresse;

}
