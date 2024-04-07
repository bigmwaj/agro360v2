package com.agro360.dto.achat;

import java.time.LocalDate;

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.dto.core.PartnerDto;
import com.agro360.dto.stock.MagasinDto;
import com.agro360.vd.achat.BonCommandeStatusEnumVd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "ACHAT_TBL_BC")
public class BonCommandeDto extends AbstractStatusTrackingDto<BonCommandeStatusEnumVd> {
	@Id
	@Column(name = "BON_COMMANDE_CODE", updatable = false, length = 16)
	@EqualsAndHashCode.Include()
	private String bonCommandeCode;

	@Column(name = "LIVRAISON")
	private Boolean livraison;

	@Column(name = "DATE_BON_COMMANDE", nullable = false)
	private LocalDate dateBonCommande;

	@ManyToOne()
	@JoinColumn(name = "FOURNISSEUR_CODE", nullable = false)
	private PartnerDto fournisseur;
	
	@ManyToOne()
	@JoinColumn(name = "MAGASIN_CODE", nullable = false)
	private MagasinDto magasin;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = BonCommandeStatusEnumVd.COLUMN_LENGTH)
	private BonCommandeStatusEnumVd status;

	@Column(name = "DESCRIPTION", length = 256)
	private String description;

}
