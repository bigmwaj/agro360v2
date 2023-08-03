package com.agro360.dto.stock;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.agro360.dto.common.AbstractDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "STOCK_TBL_CONVERSION")
@IdClass(ConversionPk.class)
public class ConversionDto extends AbstractDto
{	
	@Id
	@ManyToOne()
	@JoinColumn(name = "ARTICLE_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private ArticleDto article;
	
	@Id
	@ManyToOne()
	@JoinColumn(name = "UNITE_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private UniteDto unite;

	@Column(name = "FACTEUR", nullable = false, updatable = false)
	private Double facteur;
	
}
