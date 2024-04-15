package com.agro360.dto.stock;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
public class ConversionPk implements Serializable {

	private static final long serialVersionUID = -7643918247496362428L;

	private String articleCode;

	private String unite;

	public ConversionPk() {
		super();
	}

	public ConversionPk(String articleCode, String unite) {
		super();
		this.articleCode = articleCode;
		this.unite = unite;
	}

	public ConversionPk(ConversionDto dto) {
		super();
		this.articleCode = dto.getArticleCode();
		if (dto.getUnite() != null) {
			this.unite = dto.getUnite().getUniteCode();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ConversionPk)) {
			return false;
		}
		var pk = (ConversionPk) obj;
		return Objects.equals(articleCode, pk.articleCode) && Objects.equals(unite, pk.unite);
	}

	@Override
	public int hashCode() {
		return Objects.hash(articleCode, unite);
	}

}
