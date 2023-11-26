package com.agro360.dto.stock;

import java.util.Objects;

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
//@EqualsAndHashCode(callSuper = false)
@Entity(name = "STOCK_TBL_CONVERSION")
@IdClass(ConversionPk.class)
public class ConversionDto extends AbstractDto {
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		
		if (!(obj instanceof ConversionDto))
			return false;
		
		var thisPk = new ConversionPk(this);
		var otherPk = new ConversionPk((ConversionDto) obj);
		return thisPk.equals(otherPk);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = prime + Objects.hash(article.getArticleCode(), unite.getUniteCode());
		return result;
	}

}
