package com.agro360.dto.stock;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
public class InventairePk implements Serializable {

	private static final long serialVersionUID = -7643918247496362428L;
	
	private String magasin;

	private String article;
	
	private String variantCode;

	public InventairePk() {
		super();
	}

	public InventairePk(String magasin, String article, String variantCode) {
		super();
		this.magasin = magasin;
		this.article = article;
		this.variantCode = variantCode;
	}

	public InventairePk(InventaireDto dto) {
		super();
		this.variantCode = dto.getVariantCode();
		if (dto.getArticle() != null) {
			this.article = dto.getArticle().getArticleCode();
		}
		if (dto.getMagasin() != null) {
			this.magasin = dto.getMagasin().getMagasinCode();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof InventairePk)) {
			return false;
		}
		var pk = (InventairePk) obj;
		return  Objects.equals(magasin, pk.magasin) 
				&& Objects.equals(article, pk.article) 
				&& Objects.equals(variantCode, pk.variantCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(magasin, article, variantCode);
	}

}
