package com.agro360.dto.core;

import com.agro360.dto.common.AbstractDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "CORE_TBL_CAT")
public class CategoryDto extends AbstractDto{

	@Id
	@Column(name = "CATEGORY_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String categoryCode;

	@Column(name = "DESCRIPTION")
	private String description;
	
	@ManyToOne()
	@JoinColumn(name = "PARENT_CATEGORY_CODE", updatable = false)
	private CategoryDto parent;	

}
