package com.agro360.dto.production.avicole;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
public class MetadataPk implements Serializable {

	private static final long serialVersionUID = -7643918247496362428L;

	private String cycle;
	
	private String metadataCode;

	public MetadataPk() {
		super();
	}

	public MetadataPk(String cycle, String metadataCode) {
		super();
		this.cycle = cycle;
		this.metadataCode = metadataCode;
	}

	public MetadataPk(MetadataDto dto) {
		super();
		this.cycle = dto.getCycle().getCycleCode();
		this.metadataCode = dto.getMetadataCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof MetadataPk)) {
			return false;
		}
		MetadataPk pk = (MetadataPk) obj;
		return Objects.equals(cycle, pk.cycle) && Objects.equals(metadataCode, pk.metadataCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(cycle, metadataCode);
	}

}
