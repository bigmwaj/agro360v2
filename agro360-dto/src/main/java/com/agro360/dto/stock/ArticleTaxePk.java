package com.agro360.dto.stock;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
public class ArticleTaxePk implements Serializable {

	private static final long serialVersionUID = -7643918247496362428L;
	
	private String articleCode;

	private String taxe;
	
	public ArticleTaxePk() {
		super();
	}

	public ArticleTaxePk(String articleCode, String taxe) {
		super();
		this.articleCode = articleCode;
		this.taxe = taxe;
	}

	public ArticleTaxePk(ArticleTaxeDto dto) {
		super();
		this.articleCode = dto.getArticleCode();
		if( dto.getTaxe() != null ) {
			this.taxe = dto.getTaxe().getTaxeCode();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ArticleTaxePk)) {
			return false;
		}
		var pk = (ArticleTaxePk) obj;
		return Objects.equals(articleCode, pk.articleCode)
				&& Objects.equals(taxe, pk.taxe);
	}

	@Override
	public int hashCode() {
		return Objects.hash(articleCode, taxe);
	}

}
