package com.agro360.dto.production.avicole;

import com.agro360.dto.common.AbstractDto;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.UniteDto;
import com.agro360.dto.stock.VariantDto;
import com.agro360.vd.production.avicole.ProductionCategoryEnumVd;

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
@Entity(name = "P_AVI_TBL_PRODUCTION")
public class ProductionDto extends AbstractDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PRODUCTION_ID", nullable = false, updatable = false)
	@EqualsAndHashCode.Include()
	private Long productionId;

	@ManyToOne()
	@JoinColumn(name = "CYCLE_CODE", nullable = false, updatable = false)
	private CycleDto cycle;

	@Column(name = "NUMERO_JOURNEE", nullable = false)
	private Long numeroJournee;

	@ManyToOne()
	@JoinColumn(name = "UNITE_CODE", nullable = false)
	private UniteDto unite;

	@ManyToOne()
	@JoinColumn(name = "ARTICLE_CODE", nullable = false)
	private ArticleDto article;

	@Column(name = "VARIANT_CODE", length = VariantDto.VARIANT_CODE_LENGTH)
	private String variantCode;

	@Column(name = "COMMENTAIRE", length = 128)
	private String commentaire;

	@Column(name = "QUANTITE", nullable = false)
	private Double quantite;

	@Enumerated(EnumType.STRING)
	@Column(name = "CATEGORY", nullable = false, updatable = false, length = ProductionCategoryEnumVd.COLUMN_LENGTH)
	private ProductionCategoryEnumVd category;

}
