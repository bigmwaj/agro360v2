package com.agro360.dto.tiers;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
public class TiersCategoryPk implements Serializable {

	private static final long serialVersionUID = -7643918247496362428L;

	private String category;

	private String tiersCode;

	public TiersCategoryPk() {
		super();
	}

	public TiersCategoryPk(String tiersCode, String category) {
		super();
		this.category = category;
		this.tiersCode = tiersCode;
	}

	public TiersCategoryPk(TiersCategoryDto dto) {
		super();
		this.category = dto.getCategory().getCategoryCode();
		this.tiersCode = dto.getTiersCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TiersCategoryPk)) {
			return false;
		}
		TiersCategoryPk pk = (TiersCategoryPk) obj;
		return Objects.equals(category, pk.category) && Objects.equals(tiersCode, pk.tiersCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, tiersCode);
	}

}
