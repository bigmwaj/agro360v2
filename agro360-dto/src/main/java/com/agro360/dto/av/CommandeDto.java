package com.agro360.dto.av;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.dto.core.PartnerDto;
import com.agro360.dto.finance.CompteDto;
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
	@Column(name = "COMMANDE_CODE", updatable = false, length = 16)
	@EqualsAndHashCode.Include()
	private String commandeCode;

	@Column(name = "COMMANDE_DATE", nullable = false)
	private LocalDate date;

	@ManyToOne()
	@JoinColumn(name = "PARTNER_CODE", nullable = false, updatable = false)
	private PartnerDto partner;
	
	@ManyToOne()
	@JoinColumn(name = "MAGASIN_CODE", nullable = false, updatable = false)
	private MagasinDto magasin;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "COMMANDE_TYPE", nullable = false, updatable = false, length = CommandeTypeEnumVd.COLUMN_LENGTH)
	private CommandeTypeEnumVd type;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = CommandeStatusEnumVd.COLUMN_LENGTH)
	private CommandeStatusEnumVd status;

	@Column(name = "DESCRIPTION", length = 256)
	private String description;
	
	@ManyToOne()
	@JoinColumn(name = "COMPTE_CODE")
	private CompteDto compte;

	@Column(name = "PAIEMENT_COMPTANT", precision = 16, scale = 4)
	private BigDecimal paiementComptant;
	

}
