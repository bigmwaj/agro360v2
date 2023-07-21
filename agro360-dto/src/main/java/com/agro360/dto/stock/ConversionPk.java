package com.agro360.dto.stock;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
public class ConversionPk implements Serializable {

	private static final long serialVersionUID = -7643918247496362428L;

	private String article;

	private String unite;

	public ConversionPk() {
		super();
	}

	public ConversionPk(String article, String unite) {
		super();
		this.article = article;
		this.unite = unite;
	}

	public ConversionPk(ConversionDto dto) {
		super();
		this.article = dto.getArticle().getArticleCode();
		this.unite = dto.getUnite().getUniteCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ConversionPk)) {
			return false;
		}
		ConversionPk pk = (ConversionPk) obj;
		return Objects.equals(article, pk.article) && Objects.equals(unite, pk.unite);
	}

	@Override
	public int hashCode() {
		return Objects.hash(article, unite);
	}

}
