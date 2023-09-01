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

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.dto.tiers.TiersDto;
import com.agro360.vd.stock.StatusCaisseEnumVd;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "STOCK_TBL_CAISSE")
@IdClass(CaissePk.class)
public class CaisseDto extends AbstractStatusTrackingDto<StatusCaisseEnumVd> {

	@Id
	@ManyToOne()
	@JoinColumn(name = "MAGASIN_CODE", nullable = false, updatable = false, referencedColumnName = "MAGASIN_CODE")
	@EqualsAndHashCode.Include()
	private MagasinDto magasin;
	
	@Id
	@ManyToOne()
	@JoinColumn(name = "AGENT_CODE", nullable = false, updatable = false, referencedColumnName = "TIERS_CODE")
	@EqualsAndHashCode.Include()
	private TiersDto agent;

	@Id
	@Column(name = "JOURNEE", updatable = false, nullable = false)
	@EqualsAndHashCode.Include()
	private LocalDate journee;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = StatusCaisseEnumVd.COLUMN_LENGTH)
	private StatusCaisseEnumVd status;

	@Column(name = "NOTE", length = 128)
	private String note;

}
