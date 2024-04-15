package com.agro360.dto.av;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
public class LigneTaxePk implements Serializable {

	private static final long serialVersionUID = -7643918247496362428L;
	
	private String commandeCode;
	
	private Long ligneId;

	private String taxe;
	
	public LigneTaxePk() {
		super();
	}

	public LigneTaxePk(String commandeCode, Long ligneId, String taxe) {
		super();
		this.commandeCode = commandeCode;
		this.ligneId = ligneId;
		this.taxe = taxe;
	}

	public LigneTaxePk(LigneTaxeDto dto) {
		super();
		this.commandeCode = dto.getCommandeCode();
		this.ligneId = dto.getLigneId();
		if( dto.getTaxe() != null ) {
			this.taxe = dto.getTaxe().getTaxeCode();
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof LigneTaxePk)) {
			return false;
		}
		var pk = (LigneTaxePk) obj;
		return Objects.equals(commandeCode, pk.commandeCode)
				&& Objects.equals(ligneId, pk.ligneId)
				&& Objects.equals(taxe, pk.taxe);
	}

	@Override
	public int hashCode() {
		return Objects.hash(commandeCode, ligneId, taxe);
	}

}
