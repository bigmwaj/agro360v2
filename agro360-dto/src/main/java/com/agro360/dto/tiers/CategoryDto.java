package com.agro360.dto.tiers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.agro360.dto.common.AbstractDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "TIERS_TBL_CATEGORY")
public class CategoryDto extends AbstractDto{

	@Id
	@Column(name = "CATEGORY_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String categoryCode;

	@Column(name = "DESCRIPTION", length = 64)
	private String description;
	
	@ManyToOne()
	@JoinColumn(name = "PARENT_CATEGORY_CODE", updatable = false)
	private CategoryDto parent;	

}
