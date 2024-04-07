package com.agro360.dto.stock;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import lombok.Data;

@Data
public class CaissePk implements Serializable {

	private static final long serialVersionUID = -7643918247496362428L;

	private String magasin;
	
	private String Partner;

	private LocalDate journee;

	public CaissePk() {
		super();
	}

	public CaissePk(String magasin, String Partner, LocalDate journee) {
		super();
		this.magasin = magasin;
		this.Partner = Partner;
		this.journee = journee;
	}

	public CaissePk(CaisseDto dto) {
		super();
		this.magasin = dto.getMagasin().getMagasinCode();
		this.Partner = dto.getPartner().getPartnerCode();
		this.journee = dto.getJournee();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CaissePk)) {
			return false;
		}
		CaissePk pk = (CaissePk) obj;
		return Objects.equals(magasin, pk.magasin) && Objects.equals(Partner, pk.Partner) && Objects.equals(journee, pk.journee);
	}

	@Override
	public int hashCode() {
		return Objects.hash(magasin, Partner, journee);
	}

}
