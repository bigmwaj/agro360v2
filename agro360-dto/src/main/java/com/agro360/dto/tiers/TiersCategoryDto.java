package com.agro360.dto.tiers;

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
@Entity(name = "TIERS_TBL_TIERS_CATEGORY")

@IdClass(TiersCategoryPk.class)
public class TiersCategoryDto extends AbstractDto {
	
	public TiersCategoryDto() {
		super();
	}
	
	public TiersCategoryDto(CategoryDto category) {
		super();
		this.category = category;
	}

	@Id
	@ManyToOne()
	@JoinColumn(name = "CATEGORY_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private CategoryDto category;

	@Id
	@Column(name = "TIERS_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String tiersCode;

}
