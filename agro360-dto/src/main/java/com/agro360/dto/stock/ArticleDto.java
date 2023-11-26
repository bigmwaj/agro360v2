package com.agro360.dto.stock;

import com.agro360.dto.common.AbstractDto;
import com.agro360.vd.stock.TypeArticleEnumVd;

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
	@Column(name = "ARTICLE_CODE", updatable = false, length = 16)
	@EqualsAndHashCode.Include()
	private String articleCode;

	@Column(name = "DESCRIPTION", length = 64)
	private String description;

	@ManyToOne()
	@JoinColumn(name = "UNITE_CODE", nullable = false, updatable = false)
	private UniteDto unite;

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE_ARTICLE", nullable = false, updatable = false, length = TypeArticleEnumVd.COLUMN_LENGTH)
	private TypeArticleEnumVd typeArticle;

}
