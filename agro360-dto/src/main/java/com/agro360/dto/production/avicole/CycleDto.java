package com.agro360.dto.production.avicole;

import java.time.LocalDate;

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.dto.stock.MagasinDto;
import com.agro360.vd.production.avicole.CycleStatusEnumVd;

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
@Entity(name = "P_AVI_TBL_CYCLE")
public class CycleDto extends AbstractStatusTrackingDto<CycleStatusEnumVd> {

	@Id
	@Column(name = "CYCLE_CODE", updatable = false, length = 16)
	@EqualsAndHashCode.Include()
	private String cycleCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = CycleStatusEnumVd.COLUMN_LENGTH)
	private CycleStatusEnumVd status;

	@ManyToOne()
	@JoinColumn(name = "MAGASIN_CODE", nullable = false)
	private MagasinDto magasin;

	@Column(name = "DESCRIPTION", length = 64)
	private String description;

	@Column(name = "RACE_PLAN", nullable = false)
	private String racePlanifiee;

	@Column(name = "QTE_PLAN", nullable = false)
	private Double quantitePlanifiee;

	@Column(name = "DATE_PLAN", nullable = false)
	private LocalDate datePlanifiee;
	
	@Column(name = "RACE_EFF")
	private String raceEffective;

	@Column(name = "QTE_EFF")
	private Double quantiteEffective;

	@Column(name = "DATE_EFF")
	private LocalDate dateEffective;

}
