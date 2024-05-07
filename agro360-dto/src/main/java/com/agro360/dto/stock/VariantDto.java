package com.agro360.dto.stock;

import com.agro360.dto.common.AbstractDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "STOCK_TBL_VARIANT")
@IdClass(VariantPk.class)
public class VariantDto extends AbstractDto {

	@Id
	@Column(name = "ARTICLE_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String articleCode;

	@Id
	@Column(name = "VARIANT_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String variantCode;
	
	@Column(name = "ALIAS")
	private String alias;

	@Column(name = "DESCRIPTION")
	private String description;

}
