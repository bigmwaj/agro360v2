package com.agro360.dto.stock;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import lombok.Data;

@Data
public class CaissePk implements Serializable {

	private static final long serialVersionUID = -7643918247496362428L;

	private String agent;

	private LocalDate journee;

	public CaissePk() {
		super();
	}

	public CaissePk(String agent, LocalDate journee) {
		super();
		this.agent = agent;
		this.journee = journee;
	}

	public CaissePk(CaisseDto dto) {
		super();
		this.agent = dto.getAgent().getTiersCode();
		this.journee = dto.getJournee();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CaissePk)) {
			return false;
		}
		CaissePk pk = (CaissePk) obj;
		return Objects.equals(agent, pk.agent) && Objects.equals(journee, pk.journee);
	}

	@Override
	public int hashCode() {
		return Objects.hash(agent, journee);
	}

}
