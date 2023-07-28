package com.agro360.dto.stock;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.agro360.dto.common.AbstractDto;
import com.agro360.dto.tiers.TiersDto;
import com.agro360.vd.stock.StatutCaisseEnumVd;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "STOCK_TBL_CAISSE")
@IdClass(CaissePk.class)
public class CaisseDto extends AbstractDto {

	@Id
	@ManyToOne()
	@JoinColumn(name = "AGENT_CODE", nullable = false, updatable = false)
	@EqualsAndHashCode.Include()
	private TiersDto agent;

	@Id
	@Column(name = "JOURNEE", updatable = false, nullable = false)
	@EqualsAndHashCode.Include()
	private LocalDate journee;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUT", nullable = false, length = 8)
	private StatutCaisseEnumVd statut;

	@Column(name = "NOTE", length = 128)
	private String note;

}
