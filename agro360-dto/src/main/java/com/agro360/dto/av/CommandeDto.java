package com.agro360.dto.av;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.agro360.dto.common.AbstractStatusTrackingDto;
import com.agro360.dto.core.PartnerDto;
import com.agro360.dto.finance.CompteDto;
import com.agro360.dto.stock.MagasinDto;
import com.agro360.vd.av.CommandeStatusEnumVd;
import com.agro360.vd.av.CommandeTypeEnumVd;
import com.agro360.vd.av.RemiseTypeEnumVd;

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
@Entity(name = "AV_TBL_COMMANDE")
public class CommandeDto extends AbstractStatusTrackingDto<CommandeStatusEnumVd> {
	@Id
	@Column(name = "COMMANDE_CODE", updatable = false, length = 16)
	@EqualsAndHashCode.Include()
	private String commandeCode;

	@Column(name = "COMMANDE_DATE", nullable = false)
	private LocalDate date;

	@ManyToOne()
	@JoinColumn(name = "PARTNER_CODE", nullable = false, updatable = false)
	private PartnerDto partner;
	
	@ManyToOne()
	@JoinColumn(name = "MAGASIN_CODE", nullable = false, updatable = false)
	private MagasinDto magasin;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "COMMANDE_TYPE", nullable = false, updatable = false, length = CommandeTypeEnumVd.COLUMN_LENGTH)
	private CommandeTypeEnumVd type;

	@Enumerated(EnumType.STRING)
	@Column(name = "STATUS", nullable = false, length = CommandeStatusEnumVd.COLUMN_LENGTH)
	private CommandeStatusEnumVd status;

	@Column(name = "DESCRIPTION", length = 256)
	private String description;
	
	@ManyToOne()
	@JoinColumn(name = "COMPTE_CODE")
	private CompteDto compte;

	@Column(name = "PAIEMENT_COMPTANT", precision = 16, scale = 4)
	private BigDecimal paiementComptant;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "REMISE_TYPE", length = RemiseTypeEnumVd.COLUMN_LENGTH)
	private RemiseTypeEnumVd remiseType;

	@Column(name = "REMISE_TAUX")
	private Double remiseTaux;
	
	@Column(name = "REMISE_MONTANT", precision = 16, scale = 4, nullable = false)
	private BigDecimal remiseMontant;

	@Column(name = "REMISE_RAISON", length = 256)
	private String remiseRaison;
	
	/**
	 * Liste des valeurs calculées
	 */
	@Column(name = "TAXE", precision = 16, scale = 4, nullable = false)
	private BigDecimal taxe;

	@Column(name = "REMISE", precision = 16, scale = 4, nullable = false)
	private BigDecimal remise;
	
	@Column(name = "PRIX_TOTAL_HT", precision = 16, scale = 4, nullable = false)
	private BigDecimal prixTotalHT;
	
	@Column(name = "PRIX_TOTAL_TTC", precision = 16, scale = 4, nullable = false)
	private BigDecimal prixTotalTTC;
	
	@Column(name = "PRIX_TOTAL", precision = 16, scale = 4, nullable = false)
	private BigDecimal prixTotal;

}
