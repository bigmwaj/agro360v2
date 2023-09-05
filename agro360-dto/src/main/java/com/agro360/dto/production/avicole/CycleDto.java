package com.agro360.dto.production.avicole;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.dto.stock.MagasinDto;
import com.agro360.vd.production.avicole.StatusCycleEnumVd;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "P_AVI_TBL_CYCLE")
public class CycleDto extends AbstractStatusTrackingDto<StatusCycleEnumVd> {

	@Id
	@Column(name = "CYCLE_CODE", updatable = false, length = 16)
	@EqualsAndHashCode.Include()
	private String cycleCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = StatusCycleEnumVd.COLUMN_LENGTH)
	private StatusCycleEnumVd status;

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
