package com.agro360.dto.common;

import java.math.BigDecimal;

import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.UniteDto;
import com.agro360.dto.stock.VariantDto;
import com.agro360.vd.stock.TypeLigneEnumVd;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public abstract class AbstractLigneDto extends AbstractDto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "LIGNE_ID", nullable = false, updatable = false)
	@EqualsAndHashCode.Include()
	private Long ligneId;

	@Column(name = "NUMERO", nullable = false)
	private Integer numero;

	@ManyToOne()
	@JoinColumn(name = "UNITE_CODE", nullable = false)
	private UniteDto unite;

	@ManyToOne()
	@JoinColumn(name = "ARTICLE_CODE", nullable = false)
	private ArticleDto article;

	@Column(name = "VARIANT_CODE", length = VariantDto.VARIANT_CODE_LENGTH)
	private String variantCode;

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE_LIGNE", nullable = false, updatable = false, length = TypeLigneEnumVd.COLUMN_LENGTH)
	private TypeLigneEnumVd typeLigne;

	@Column(name = "DESCRIPTION", length = 128)
	private String description;

	@Column(name = "QUANTITE", nullable = false)
	private Double quantite;

	@Column(name = "PRIX_UNITAIRE", nullable = false, precision = 16, scale = 4)
	private BigDecimal prixUnitaire;

}
