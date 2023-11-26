package com.agro360.dto.stock;

import java.time.LocalDate;

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.dto.tiers.TiersDto;
import com.agro360.vd.stock.StatusCaisseEnumVd;

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
