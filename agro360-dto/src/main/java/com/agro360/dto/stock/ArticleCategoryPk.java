package com.agro360.dto.stock;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
public class ArticleCategoryPk implements Serializable {

	private static final long serialVersionUID = -7643918247496362428L;

	private String category;

	private String articleCode;

	public ArticleCategoryPk() {
		super();
	}

	public ArticleCategoryPk(String articleCode, String category) {
		super();
		this.category = category;
		this.articleCode = articleCode;
	}

	public ArticleCategoryPk(ArticleCategoryDto dto) {
		super();
		this.category = dto.getCategory().getCategoryCode();
		this.articleCode = dto.getArticleCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ArticleCategoryPk)) {
			return false;
		}
		ArticleCategoryPk pk = (ArticleCategoryPk) obj;
		return Objects.equals(category, pk.category) && Objects.equals(articleCode, pk.articleCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, articleCode);
	}

}
