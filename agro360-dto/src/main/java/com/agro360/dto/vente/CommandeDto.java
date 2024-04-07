package com.agro360.dto.vente;

import java.time.LocalDate;

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.dto.core.PartnerDto;
import com.agro360.dto.stock.MagasinDto;
import com.agro360.vd.vente.CommandeStatusEnumVd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "VENTE_TBL_COMMANDE")
public class CommandeDto extends AbstractStatusTrackingDto<CommandeStatusEnumVd> {
	@Id
	@Column(name = "COMMANDE_CODE", length = 16, nullable = false, updatable = false)
	@EqualsAndHashCode.Include()
	private String commandeCode;

	@Column(name = "COMMANDE_DATE", nullable = false)
	private LocalDate date;

	@ManyToOne()
	@JoinColumn(name = "CLIENT_CODE", nullable = false)
	private PartnerDto client;

	@ManyToOne()
	@JoinColumn(name = "MAGASIN_CODE", nullable = false)
	private MagasinDto magasin;

	@Column(name = "TRANSPORT_REQUIS")
	private Boolean transportRequis;

	@Column(name = "LIVREE")
	private Boolean livree;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUT", nullable = false, length = CommandeStatusEnumVd.COLUMN_LENGTH)
	private CommandeStatusEnumVd status;

	@Column(name = "DESCRIPTION", length = 255)
	private String description;

	@Column(name = "VILLE", nullable = false, length = 16)
	private String ville;

	@Column(name = "ADRESSE", nullable = false, length = 64)
	private String adresse;

}
