package com.agro360.dto.production.avicole;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.agro360.dto.common.AbstractDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "P_AVI_TBL_JOURNEE")
@IdClass(JourneePk.class)
public class JourneeDto extends AbstractDto {

	@Id
	@ManyToOne()
	@JoinColumn(name = "CYCLE_CODE", nullable = false, updatable = false)
	private CycleDto cycle;

	@Id
	@Column(name = "NUMERO_JOURNEE", nullable = false, updatable = false)
	private Long numeroJournee;

}
