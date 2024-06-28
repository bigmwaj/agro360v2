package com.agro360.dto.common;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public abstract class AbstractCategoryDto<T extends AbstractCategoryDto<T>> extends AbstractDto {

	@Id
	@Column(name = "CATEGORY_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String categoryCode;

	@Column(name = "DESCRIPTION")
	private String description;

	@ManyToOne()
	@JoinColumn(name = "PARENT_CATEGORY_CODE", updatable = false)
	private T parent;

}
