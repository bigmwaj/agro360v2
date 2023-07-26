package com.agro360.dto.stock;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.agro360.dto.common.AbstractDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "STOCK_TBL_UNITE")
public class UniteDto extends AbstractDto
{

	@Id
	@Column(name = "UNITE_CODE", updatable = false, length = 16)
	@EqualsAndHashCode.Include()
	private String uniteCode;

	@Column(name = "DESCRIPTION", length = 64)
	private String description;

	@Column(name = "ABREVIATION", length = 8)
	private String abreviation;

}
