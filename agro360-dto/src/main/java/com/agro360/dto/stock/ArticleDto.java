package com.agro360.dto.stock;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.agro360.dto.common.AbstractDto;
import com.agro360.vd.stock.TypeArticleEnumVd;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "STOCK_TBL_ARTICLE")
public class ArticleDto extends AbstractDto 
{
	@Id
	@Column(name = "ARTICLE_CODE", updatable = false, length = 16)
	private String articleCode;

	@Column(name = "DESCRIPTION", length = 64)
	private String description;

	@ManyToOne()
	@JoinColumn(name = "UNITE_CODE", nullable = false, updatable = false)
	private UniteDto unite;

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE_ARTICLE", nullable = false, updatable = false, length = 8)
	private TypeArticleEnumVd typeArticle;

}
