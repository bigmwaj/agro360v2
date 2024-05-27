package com.agro360.dto.production.avicole;

import com.agro360.dto.common.AbstractLigneDto;
import com.agro360.vd.production.avicole.PhaseEnumVd;
import com.agro360.vd.production.avicole.RubriqueEnumVd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "P_AVI_TBL_OPERATION")
public class OperationDto extends AbstractLigneDto {

	@ManyToOne()
	@JoinColumn(name = "CYCLE_CODE", updatable = false)
	private CycleDto cycle;

	@Column(name = "NUMERO_JOURNEE", updatable = false)
	private Long numeroJournee;

	@Enumerated(EnumType.STRING)
	@Column(name = "PHASE", updatable = false)
	private PhaseEnumVd phase;

	@Enumerated(EnumType.STRING)
	@Column(name = "RUBRIQUE", updatable = false)
	private RubriqueEnumVd rubrique;

}
