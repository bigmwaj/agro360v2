package com.agro360.dto.stock;

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
@Entity(name = "STOCK_TBL_VARIANT")
@IdClass(VariantPk.class)
public class VariantDto extends AbstractDto {
	public static final int VARIANT_CODE_LENGTH = 16;

	@Id
	@ManyToOne()
	@JoinColumn(name = "ARTICLE_CODE", updatable = false, nullable = false)
	@EqualsAndHashCode.Include()
	private ArticleDto article;

	@Id
	@Column(name = "VARIANT_CODE", updatable = false, nullable = false, length = VARIANT_CODE_LENGTH)
	@EqualsAndHashCode.Include()
	private String variantCode;

	@Column(name = "DESCRIPTION", length = 64)
	private String description;

}
