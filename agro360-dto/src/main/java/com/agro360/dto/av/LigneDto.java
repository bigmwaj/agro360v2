package com.agro360.dto.av;

import java.math.BigDecimal;

import com.agro360.dto.common.AbstractDto;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.UniteDto;
import com.agro360.vd.av.LigneTypeEnumVd;
import com.agro360.vd.av.RemiseTypeEnumVd;

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
@EqualsAndHashCode(callSuper = true)
@Entity(name = "AV_TBL_LIGNE")
public class LigneDto extends AbstractDto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LIGNE_ID", updatable = false)
	@EqualsAndHashCode.Include()
	private Long ligneId;
	
	@Column(name = "COMMANDE_CODE", updatable = false)
	private String commandeCode;

	@ManyToOne()
	@JoinColumn(name = "UNITE_CODE")
	private UniteDto unite;

	@ManyToOne()
	@JoinColumn(name = "ARTICLE_CODE")
	private ArticleDto article;

	@Column(name = "VARIANT_CODE")
	private String variantCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "LIGNE_TYPE", updatable = false, length = LigneTypeEnumVd.COLUMN_LENGTH)
	private LigneTypeEnumVd type;

	@Column(name = "DESCRIPTION")
	private String description;

	@Column(name = "QUANTITE")
	private Double quantite;

	@Column(name = "PRIX_UNITAIRE", precision = 16, scale = 4)
	private BigDecimal prixUnitaire;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "REMISE_TYPE")
	private RemiseTypeEnumVd remiseType;

	@Column(name = "REMISE_TAUX")
	private Double remiseTaux;
	
	@Column(name = "REMISE_MONTANT", precision = 16, scale = 4)
	private BigDecimal remiseMontant;

	@Column(name = "REMISE_RAISON", length = 256)
	private String remiseRaison;
	
	@Column(name = "TAXE", precision = 16, scale = 4)
	private BigDecimal taxe;
	
	@Column(name = "PRIX_TOTAL_HT", precision = 16, scale = 4)
	private BigDecimal prixTotalHT;
	
	// prixTotalHT - remise + taxes
	@Column(name = "PRIX_TOTAL", precision = 16, scale = 4)
	private BigDecimal prixTotal;
	
	@Column(name = "REMISE", precision = 16, scale = 4)
	private BigDecimal remise;

}
