package com.agro360.dto.production.avicole;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
public class JourneePk implements Serializable {

	private static final long serialVersionUID = -7643918247496362428L;

	private String cycle;

	private Long numeroJournee;

	public JourneePk() {
		super();
	}

	public JourneePk(String cycle, Long numeroJournee) {
		super();
		this.cycle = cycle;
		this.numeroJournee = numeroJournee;
	}

	public JourneePk(JourneeDto dto) {
		super();
		this.cycle = dto.getCycle().getCycleCode();
		this.numeroJournee = dto.getNumeroJournee();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof JourneePk)) {
			return false;
		}
		JourneePk pk = (JourneePk) obj;
		return Objects.equals(cycle, pk.cycle) && Objects.equals(numeroJournee, pk.numeroJournee);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cycle, numeroJournee);
	}

}
