package com.agro360.dto.achat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.dto.stock.CasierDto;
import com.agro360.dto.stock.MagasinDto;
import com.agro360.vd.achat.StatusReceptionEnumVd;

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
@Entity(name = "ACHAT_TBL_BC_LIGNE_RECEP")
public class ReceptionDto extends AbstractStatusTrackingDto<StatusReceptionEnumVd> {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "RECEPTION_ID", nullable = false, updatable = false)
	@EqualsAndHashCode.Include()
	private Long receptionId;

	@ManyToOne()
	@JoinColumn(name = "LIGNE_ID", nullable = false, updatable = false)
	private LigneDto ligne;

	@Column(name = "DESCRIPTION", length = 128)
	private String description;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = StatusReceptionEnumVd.COLUMN_LENGTH)
	private StatusReceptionEnumVd status;

	@Column(name = "PRIX_UNITAIRE", nullable = false, precision = 16, scale = 4)
	private BigDecimal prixUnitaire;

	@Column(name = "QUANTITE", nullable = false)
	private Double quantite;

	@Column(name = "DATE_RECEPTION", nullable = false)
	private LocalDateTime dateReception;

	@ManyToOne()
	@JoinColumn(name = "MAGASIN_CODE", nullable = false)
	private MagasinDto magasin;

	@Column(name = "CASIER_CODE", length = CasierDto.CASIER_CODE_LENGTH)
	private String casierCode;

}
