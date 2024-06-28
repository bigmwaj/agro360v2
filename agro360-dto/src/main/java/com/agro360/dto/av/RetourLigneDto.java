package com.agro360.dto.av;

import java.time.LocalDateTime;

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.dto.stock.UniteDto;
import com.agro360.vd.av.RetourStatusEnumVd;

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
@Entity(name = "AV_TBL_RETOUR")
public class RetourLigneDto extends AbstractStatusTrackingDto<RetourStatusEnumVd> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RETOUR_ID", updatable = false)
	@EqualsAndHashCode.Include()
	private Long retourId;

	@Column(name = "COMMANDE_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String commandeCode;

	@EqualsAndHashCode.Include()
	@ManyToOne()
	@JoinColumn(name = "LIGNE_ID", updatable = false)
	private LigneDto ligne;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS")
	private RetourStatusEnumVd status;

	@ManyToOne()
	@JoinColumn(name = "UNITE_CODE")
	private UniteDto unite;

	@Column(name = "QUANTITE")
	private Double quantite;

	@Column(name = "RETOUR_DATE")
	private LocalDateTime date;

	@Column(name = "DESCRIPTION")
	private String description;

}
