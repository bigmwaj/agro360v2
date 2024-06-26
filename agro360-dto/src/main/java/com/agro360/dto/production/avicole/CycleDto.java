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
	@Column(name = "CYCLE_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String cycleCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private CycleStatusEnumVd status;

	@ManyToOne()
	@JoinColumn(name = "MAGASIN_CODE")
	private MagasinDto magasin;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "RACE_PLAN")
	private String racePlanifiee;

	@Column(name = "QTE_PLAN")
	private Double quantitePlanifiee;

	@Column(name = "DATE_PLAN")
	private LocalDate datePlanifiee;
	
	@Column(name = "RACE_EFF")
	private String raceEffective;

	@Column(name = "QTE_EFF")
	private Double quantiteEffective;

	@Column(name = "DATE_EFF")
	private LocalDate dateEffective;
}
