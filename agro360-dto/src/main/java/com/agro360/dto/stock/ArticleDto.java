package com.agro360.dto.stock;

import com.agro360.dto.common.AbstractDto;
import com.agro360.vd.stock.ArticleTypeEnumVd;

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
@Entity(name = "STOCK_TBL_ARTICLE")
public class ArticleDto extends AbstractDto {
	
	@Id
	@Column(name = "ARTICLE_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String articleCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@ManyToOne()
	@JoinColumn(name = "UNITE_CODE", updatable = false)
	private UniteDto unite;

	@Enumerated(EnumType.STRING)
	@Column(name = "ARTICLE_TYPE", updatable = false)
	private ArticleTypeEnumVd type;

}
