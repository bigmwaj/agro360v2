package com.agro360.dto.stock;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.agro360.dto.common.AbstractDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "STOCK_TBL_MAGASIN")
public class MagasinDto extends AbstractDto {

	@Id
	@Column(name = "MAGASIN_CODE", updatable = false, length = 16)
	@EqualsAndHashCode.Include()
	private String magasinCode;

	@Column(name = "DESCRIPTION", length = 64)
	private String description;

}
