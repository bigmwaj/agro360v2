package com.agro360.dto.common;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public abstract class AbstractAssignmentDto<T extends AbstractCategoryDto<T>> extends AbstractDto {
	
	public AbstractAssignmentDto() {
		super();
	}
	
	public AbstractAssignmentDto(T category) {
		super();
		this.category = category;
	}

	@Id
	@ManyToOne()
	@JoinColumn(name = "CATEGORY_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private T category;
	
	public abstract void setAssigneeCode(String assigneeCode);

}
