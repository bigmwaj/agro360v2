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
	@Column(name = "FACTURE_CODE", updatable = false, length = 16)
	@EqualsAndHashCode.Include()
	private String factureCode;

	@Column(name = "FACTURE_DATE", nullable = false)
	private LocalDate date;

	@ManyToOne()
	@JoinColumn(name = "PARTNER_CODE", nullable = false, updatable = false)
	private PartnerDto partner;
	
	@ManyToOne()
	@JoinColumn(name = "COMMANDE_CODE", updatable = false)
	private CommandeDto commande;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "FACTURE_TYPE", nullable = false, updatable = false, length = FactureTypeEnumVd.COLUMN_LENGTH)
	private FactureTypeEnumVd type;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = FactureStatusEnumVd.COLUMN_LENGTH)
	private FactureStatusEnumVd status;

	@Column(name = "DESCRIPTION", length = 256)
	private String description;
	
	@Column(name = "MONTANT", nullable = false, precision = 16, scale = 4)
	private BigDecimal montant;

}
