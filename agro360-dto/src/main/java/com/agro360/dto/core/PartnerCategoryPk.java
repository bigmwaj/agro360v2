package com.agro360.dto.core;

import java.io.Serializable;
import java.util.Objects;

import lombok.Data;

@Data
public class PartnerCategoryPk implements Serializable {

	private static final long serialVersionUID = -7643918247496362428L;

	private String category;

	private String partnerCode;

	public PartnerCategoryPk() {
		super();
	}

	public PartnerCategoryPk(String partnerCode, String category) {
		super();
		this.category = category;
		this.partnerCode = partnerCode;
	}

	public PartnerCategoryPk(PartnerCategoryDto dto) {
		super();
		this.category = dto.getCategory().getCategoryCode();
		this.partnerCode = dto.getPartnerCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PartnerCategoryPk)) {
			return false;
		}
		PartnerCategoryPk pk = (PartnerCategoryPk) obj;
		return Objects.equals(category, pk.category) && Objects.equals(partnerCode, pk.partnerCode);
	}

	@Override
	public int hashCode() {
		return Objects.hash(category, partnerCode);
	}

}
