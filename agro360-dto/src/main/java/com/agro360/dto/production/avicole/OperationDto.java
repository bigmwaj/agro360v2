package com.agro360.dto.production.avicole;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.agro360.dto.common.AbstractLigneDto;
import com.agro360.vd.production.avicole.PhaseEnumVd;
import com.agro360.vd.production.avicole.RubriqueEnumVd;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "P_AVI_TBL_OPERATION")
public class OperationDto extends AbstractLigneDto {

	@ManyToOne()
	@JoinColumn(name = "CYCLE_CODE", nullable = false, updatable = false)
	private CycleDto cycle;

	@Column(name = "NUMERO_JOURNEE", nullable = false, updatable = false)
	private Long numeroJournee;

	@Enumerated(EnumType.STRING)
	@Column(name = "PHASE", nullable = false, updatable = false, length = PhaseEnumVd.COLUMN_LENGTH)
	private PhaseEnumVd phase;

	@Enumerated(EnumType.STRING)
	@Column(name = "RUBRIQUE", nullable = false, updatable = false, length = RubriqueEnumVd.COLUMN_LENGTH)
	private RubriqueEnumVd rubrique;

}
