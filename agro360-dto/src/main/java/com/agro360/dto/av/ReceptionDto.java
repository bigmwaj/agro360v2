package com.agro360.dto.av;

import java.time.LocalDateTime;

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.dto.stock.UniteDto;
import com.agro360.vd.achat.ReceptionStatusEnumVd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "AV_TBL_LIGNE_RECEP")
public class ReceptionDto extends AbstractStatusTrackingDto<ReceptionStatusEnumVd> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RECEPTION_ID", nullable = false, updatable = false)
	@EqualsAndHashCode.Include()
	private Long receptionId;

	@ManyToOne()
	@JoinColumn(name = "LIGNE_ID", nullable = false, updatable = false)
	private LigneDto ligne;

	@Column(name = "DESCRIPTION", length = 128)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = ReceptionStatusEnumVd.COLUMN_LENGTH)
	private ReceptionStatusEnumVd status;
	
	@ManyToOne()
	@JoinColumn(name = "UNITE_CODE", nullable = false)
	private UniteDto unite;

	@Column(name = "QUANTITE", nullable = false)
	private Double quantite;

	@Column(name = "RECEPTION_DATE", nullable = false)
	private LocalDateTime dateReception;

}
