package com.agro360.dto.stock;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
public class VariantPk implements Serializable {

	private static final long serialVersionUID = -7643918247496362428L;

	private String article;

	private String variantCode;

	public VariantPk() {
		super();
	}

	public VariantPk(String article, String variantCode) {
		super();
		this.article = article;
		this.variantCode = variantCode;
	}

	public VariantPk(VariantDto dto) {
		super();
		this.article = dto.getArticle().getArticleCode();
		this.variantCode = dto.getVariantCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof VariantPk)) {
			return false;
		}
		VariantPk pk = (VariantPk) obj;
		return Objects.equals(article, pk.article) && Objects.equals(variantCode, pk.variantCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(article, variantCode);
	}

}
