package com.agro360.dto.core;

import com.agro360.dto.common.AbstractAssignmentDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "CORE_TBL_PARTNER_CAT")

@IdClass(PartnerCategoryPk.class)
public class PartnerCategoryDto extends AbstractAssignmentDto<CategoryDto> {
	
	public PartnerCategoryDto() {
		super();
	}
	
	public PartnerCategoryDto(CategoryDto category) {
		super(category);
	}

	@Id
	@Column(name = "PARTNER_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String partnerCode;
	
	@Override
	public void setAssigneeCode(String assigneeCode) {
		this.partnerCode = assigneeCode;		
	}

}
