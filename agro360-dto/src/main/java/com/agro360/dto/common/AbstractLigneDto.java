package com.agro360.dto.common;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.UniteDto;
import com.agro360.dto.stock.VariantDto;
import com.agro360.vd.stock.TypeLigneEnumVd;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public abstract class AbstractLigneDto extends AbstractDto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
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

	@ManyToOne()
	@JoinColumns({
	  @JoinColumn(name = "VARIANT_CODE", insertable = false, updatable = false),
	  @JoinColumn(name = "ARTICLE_CODE", insertable = false, updatable = false)
	})
	private VariantDto variant;

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE_LIGNE", nullable = false, updatable = false, length = 16)
	private TypeLigneEnumVd typeLigne;

	@Column(name = "DESCRIPTION", length = 128)
	private String description;

	@Column(name = "QUANTITE", nullable = false)
	private Double quantite;

	@Column(name = "PRIX_UNITAIRE", nullable = false, precision = 16, scale = 4)
	private Double prixUnitaire;

}
