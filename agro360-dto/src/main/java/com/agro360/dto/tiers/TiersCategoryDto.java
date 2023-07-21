package com.agro360.dto.tiers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.agro360.dto.common.AbstractDto;

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
	private CategoryDto category;

	@Id
	@Column(name = "TIERS_CODE", updatable = false)
	private String tiersCode;

}
