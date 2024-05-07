package com.agro360.dto.av;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.dto.core.PartnerDto;
import com.agro360.vd.av.FactureStatusEnumVd;
import com.agro360.vd.av.FactureTypeEnumVd;

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
@Entity(name = "AV_TBL_FACTURE")
public class FactureDto extends AbstractStatusTrackingDto<FactureStatusEnumVd> {
	
	@Id
	@Column(name = "FACTURE_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String factureCode;

	@Column(name = "FACTURE_DATE")
	private LocalDate date;

	@ManyToOne()
	@JoinColumn(name = "PARTNER_CODE", updatable = false)
	private PartnerDto partner;
	
	@ManyToOne()
	@JoinColumn(name = "COMMANDE_CODE", updatable = false)
	private CommandeDto commande;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "FACTURE_TYPE", updatable = false)
	private FactureTypeEnumVd type;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private FactureStatusEnumVd status;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "CUMUL_PAIEMENT", precision = 16, scale = 4)
	private BigDecimal cumulPaiement;
	
	@Column(name = "TAXE", precision = 16, scale = 4)
	private BigDecimal taxe;
	
	@Column(name = "PRIX_TOTAL_HT", precision = 16, scale = 4)
	private BigDecimal prixTotalHT;
	
	@Column(name = "PRIX_TOTAL", precision = 16, scale = 4)
	private BigDecimal prixTotal;
	
	@Column(name = "REMISE", precision = 16, scale = 4)
	private BigDecimal remise;

}
