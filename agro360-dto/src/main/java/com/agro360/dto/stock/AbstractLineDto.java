package com.agro360.dto.stock;

import com.agro360.dto.common.AbstractDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class AbstractLineDto extends AbstractDto{

	private Long lineId;

	private ArticleDto article;

	private VariantDto variant;

	private UniteDto unite;
	
	private Integer numero;

	private String type;

	private String description;

	private Double quantite;

	private Double prixUnitaire;

	private Double prixTotal;
	
}
