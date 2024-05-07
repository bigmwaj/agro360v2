package com.agro360.dto.stock;

import com.agro360.dto.common.AbstractDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "STOCK_TBL_UNITE")
public class UniteDto extends AbstractDto {

	@Id
	@Column(name = "UNITE_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String uniteCode;

	@Column(name = "DESCRIPTION")
	private String description;

}
