package com.agro360.dto.core;

import com.agro360.dto.common.AbstractDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "CORE_TBL_PARTNER_CAT")

@IdClass(PartnerCategoryPk.class)
public class PartnerCategoryDto extends AbstractDto {
	
	public PartnerCategoryDto() {
		super();
	}
	
	public PartnerCategoryDto(CategoryDto category) {
		super();
		this.category = category;
	}

	@Id
	@ManyToOne()
	@JoinColumn(name = "CATEGORY_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private CategoryDto category;

	@Id
	@Column(name = "PARTNER_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String partnerCode;

}
