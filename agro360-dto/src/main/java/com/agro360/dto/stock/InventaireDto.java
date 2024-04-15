package com.agro360.dto.stock;

import java.math.BigDecimal;

import com.agro360.dto.common.AbstractDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "STOCK_TBL_INVENTAIRE")
@IdClass(InventairePk.class)
public class InventaireDto extends AbstractDto {

	@Id
	@ManyToOne()
	@JoinColumn(name = "MAGASIN_CODE", updatable = false, nullable = false)
	@EqualsAndHashCode.Include()
	private MagasinDto magasin;
	
	@Id
	@ManyToOne()
	@JoinColumn(name = "ARTICLE_CODE", updatable = false, nullable = false)
	@EqualsAndHashCode.Include()
	private ArticleDto article;
	
	@Id
	@Column(name = "VARIANT_CODE", updatable = false, nullable = false)
	@EqualsAndHashCode.Include()
	private String variantCode;

	@Column(name = "QUANTITE", nullable = false)
	private Double quantite;
	
	@Column(name = "PRIX_UNITAIRE_ACHAT", precision = 16, scale = 4)
	private BigDecimal prixUnitaireAchat;
	
	@Column(name = "PRIX_UNITAIRE_VENTE", nullable = false, precision = 16, scale = 4)
	private BigDecimal prixUnitaireVente;

}