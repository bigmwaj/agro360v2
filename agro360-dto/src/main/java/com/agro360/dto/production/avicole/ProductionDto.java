package com.agro360.dto.production.avicole;

import com.agro360.dto.common.AbstractDto;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.UniteDto;
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
	@Column(name = "PRODUCTION_ID", updatable = false)
	@EqualsAndHashCode.Include()
	private Long productionId;

	@ManyToOne()
	@JoinColumn(name = "CYCLE_CODE", updatable = false)
	private CycleDto cycle;

	@Column(name = "NUMERO_JOURNEE")
	private Long numeroJournee;

	@ManyToOne()
	@JoinColumn(name = "UNITE_CODE")
	private UniteDto unite;

	@ManyToOne()
	@JoinColumn(name = "ARTICLE_CODE")
	private ArticleDto article;

	@Column(name = "VARIANT_CODE")
	private String variantCode;

	@Column(name = "COMMENTAIRE")
	private String commentaire;

	@Column(name = "QUANTITE")
	private Double quantite;

	@Enumerated(EnumType.STRING)
	@Column(name = "CATEGORY", updatable = false)
	private ProductionCategoryEnumVd category;

}
