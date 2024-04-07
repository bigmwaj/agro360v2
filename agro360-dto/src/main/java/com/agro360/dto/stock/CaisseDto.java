package com.agro360.dto.stock;

import java.time.LocalDate;

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.dto.core.PartnerDto;
import com.agro360.vd.stock.CaisseStatusEnumVd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "STOCK_TBL_CAISSE")
@IdClass(CaissePk.class)
public class CaisseDto extends AbstractStatusTrackingDto<CaisseStatusEnumVd> {

	@Id
	@ManyToOne()
	@JoinColumn(name = "MAGASIN_CODE", nullable = false, updatable = false, referencedColumnName = "MAGASIN_CODE")
	@EqualsAndHashCode.Include()
	private MagasinDto magasin;
	
	@Id
	@ManyToOne()
	@JoinColumn(name = "Partner_CODE", nullable = false, updatable = false, referencedColumnName = "PARTNER_CODE")
	@EqualsAndHashCode.Include()
	private PartnerDto Partner;

	@Id
	@Column(name = "JOURNEE", updatable = false, nullable = false)
	@EqualsAndHashCode.Include()
	private LocalDate journee;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = CaisseStatusEnumVd.COLUMN_LENGTH)
	private CaisseStatusEnumVd status;

	@Column(name = "NOTE", length = 128)
	private String note;

}
