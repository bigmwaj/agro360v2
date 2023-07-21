package com.agro360.dto.stock;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
public class CasierPk implements Serializable {

	private static final long serialVersionUID = -7643918247496362428L;

	private String magasin;

	private String casierCode;

	public CasierPk() {
		super();
	}

	public CasierPk(String magasin, String casierCode) {
		super();
		this.magasin = magasin;
		this.casierCode = casierCode;
	}

	public CasierPk(CasierDto dto) {
		super();
		this.magasin = dto.getMagasin().getMagasinCode();
		this.casierCode = dto.getCasierCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CasierPk)) {
			return false;
		}
		CasierPk pk = (CasierPk) obj;
		return Objects.equals(magasin, pk.magasin) && Objects.equals(casierCode, pk.casierCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(magasin, casierCode);
	}

}
