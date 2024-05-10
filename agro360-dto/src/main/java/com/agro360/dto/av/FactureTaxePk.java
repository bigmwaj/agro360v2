package com.agro360.dto.av;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
public class FactureTaxePk implements Serializable {

	private static final long serialVersionUID = -7643918247496362428L;
	
	private String factureCode;
	
	private String taxe;
	
	public FactureTaxePk() {
		super();
	}

	public FactureTaxePk(String factureCode, String taxe) {
		super();
		this.factureCode = factureCode;
		this.taxe = taxe;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof FactureTaxePk)) {
			return false;
		}
		var pk = (FactureTaxePk) obj;
		return Objects.equals(factureCode, pk.factureCode)
				&& Objects.equals(taxe, pk.taxe);
	}

	@Override
	public int hashCode() {
		return Objects.hash(factureCode, taxe);
	}

}
