package com.agro360.dto.stock;

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
@Entity(name = "STOCK_TBL_VARIANT")

@IdClass(VariantPk.class)
public class VariantDto extends AbstractDto 
{

	@Id
	@ManyToOne()
	@JoinColumn(name = "ARTICLE_CODE", updatable = false)
	private ArticleDto article;

	@Id
	@Column(name = "VARIANT_CODE", updatable = false, length = 16)
	private String variantCode;

	@Column(name = "DESCRIPTION", length = 64)
	private String description;

}
