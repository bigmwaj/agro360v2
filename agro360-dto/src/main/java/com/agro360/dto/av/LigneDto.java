package com.agro360.dto.av;

import java.math.BigDecimal;

import com.agro360.dto.common.AbstractDto;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.UniteDto;
import com.agro360.dto.stock.VariantDto;
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
	@Column(name = "LIGNE_ID", nullable = false, updatable = false)
	@EqualsAndHashCode.Include()
	private Long ligneId;
	
	@Column(name = "COMMANDE_CODE", nullable = false, updatable = false)
	private String commandeCode;

	@ManyToOne()
	@JoinColumn(name = "UNITE_CODE", nullable = false)
	private UniteDto unite;

	@ManyToOne()
	@JoinColumn(name = "ARTICLE_CODE")
	private ArticleDto article;

	@Column(name = "VARIANT_CODE", length = VariantDto.VARIANT_CODE_LENGTH)
	private String variantCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "LIGNE_TYPE", nullable = false, updatable = false, length = LigneTypeEnumVd.COLUMN_LENGTH)
	private LigneTypeEnumVd type;

	@Column(name = "DESCRIPTION", length = 128)
	private String description;

	@Column(name = "QUANTITE", nullable = false)
	private Double quantite;

	@Column(name = "PRIX_UNITAIRE", nullable = false, precision = 16, scale = 4)
	private BigDecimal prixUnitaire;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "REMISE_TYPE", length = RemiseTypeEnumVd.COLUMN_LENGTH)
	private RemiseTypeEnumVd remiseType;

	@Column(name = "REMISE_TAUX")
	private Double remiseTaux;
	
	@Column(name = "REMISE_MONTANT", precision = 16, scale = 4)
	private BigDecimal remiseMontant;

	@Column(name = "REMISE_RAISON", length = 256)
	private String remiseRaison;
	
	@Column(name = "TAXE", precision = 16, scale = 4, nullable = false)
	private BigDecimal taxe;
	
	@Column(name = "PRIX_TOTAL_HT", precision = 16, scale = 4, nullable = false)
	private BigDecimal prixTotalHT;
	
	@Column(name = "PRIX_TOTAL_TTC", precision = 16, scale = 4, nullable = false)
	private BigDecimal prixTotalTTC;
	
	// prixTotalHT - remise + taxes
	@Column(name = "PRIX_TOTAL", precision = 16, scale = 4, nullable = false)
	private BigDecimal prixTotal;

}
