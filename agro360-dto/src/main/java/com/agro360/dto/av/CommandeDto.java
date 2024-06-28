package com.agro360.dto.av;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.dto.core.PartnerDto;
import com.agro360.dto.stock.MagasinDto;
import com.agro360.vd.av.CommandeStatusEnumVd;
import com.agro360.vd.av.CommandeTypeEnumVd;

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
@Entity(name = "AV_TBL_COMMANDE")
public class CommandeDto extends AbstractStatusTrackingDto<CommandeStatusEnumVd> {

	@Id
	@Column(name = "COMMANDE_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String commandeCode;

	@Column(name = "COMMANDE_DATE")
	private LocalDate date;

	@ManyToOne()
	@JoinColumn(name = "PARTNER_CODE", updatable = false)
	private PartnerDto partner;

	@ManyToOne()
	@JoinColumn(name = "MAGASIN_CODE", updatable = false)
	private MagasinDto magasin;

	@Enumerated(EnumType.STRING)
	@Column(name = "COMMANDE_TYPE", updatable = false)
	private CommandeTypeEnumVd type;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private CommandeStatusEnumVd status;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CUMUL_PAIEMENT")
	private BigDecimal cumulPaiement;

	@Column(name = "TAXE")
	private BigDecimal taxe;

	@Column(name = "REMISE")
	private BigDecimal remise;

	@Column(name = "PRIX_TOTAL_HT")
	private BigDecimal prixTotalHT;

	@Column(name = "PRIX_TOTAL")
	private BigDecimal prixTotal;

}
