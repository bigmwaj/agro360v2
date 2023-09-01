package com.agro360.dto.achat;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.dto.stock.CasierDto;
import com.agro360.dto.stock.MagasinDto;
import com.agro360.vd.achat.StatusReceptionEnumVd;

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
	private Double prixUnitaire;

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
