package com.agro360.dto.stock;

import com.agro360.dto.common.AbstractCategoryDto;

import jakarta.persistence.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "STOCK_TBL_CAT")
public class CategoryDto extends AbstractCategoryDto<CategoryDto> {

}
