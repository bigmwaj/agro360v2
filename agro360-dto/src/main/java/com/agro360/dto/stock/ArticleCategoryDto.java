package com.agro360.dto.stock;

import com.agro360.dto.common.AbstractAssignmentDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "STOCK_TBL_ARTICLE_CAT")

@IdClass(ArticleCategoryPk.class)
public class ArticleCategoryDto extends AbstractAssignmentDto<CategoryDto> {
	
	public ArticleCategoryDto() {
		super();
	}
	
	public ArticleCategoryDto(CategoryDto category) {
		super(category);
	}

	@Id
	@Column(name = "ARTICLE_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String articleCode;

	@Override
	public void setAssigneeCode(String hierarchyCode) {
		this.articleCode = hierarchyCode;		
	}
}
