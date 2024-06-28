package com.agro360.dto.core;

import com.agro360.dto.common.AbstractCategoryDto;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "CORE_TBL_CAT")
public class CategoryDto extends AbstractCategoryDto<CategoryDto> {

}
