package com.agro360.dto.stock;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
public class CasierPk implements Serializable {

	private static final long serialVersionUID = -7643918247496362428L;

	private String magasinCode;

	private String casierCode;

	public CasierPk() {
		super();
	}

	public CasierPk(String magasinCode, String casierCode) {
		super();
		this.magasinCode = magasinCode;
		this.casierCode = casierCode;
	}

	public CasierPk(CasierDto dto) {
		super();
		this.magasinCode = dto.getMagasinCode();
		this.casierCode = dto.getCasierCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CasierPk)) {
			return false;
		}
		CasierPk pk = (CasierPk) obj;
		return Objects.equals(magasinCode, pk.magasinCode) && Objects.equals(casierCode, pk.casierCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(magasinCode, casierCode);
	}

}
