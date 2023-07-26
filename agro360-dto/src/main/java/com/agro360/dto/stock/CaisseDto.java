package com.agro360.dto.stock;

import com.agro360.dto.common.AbstractDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CaisseDto extends AbstractDto {

	@EqualsAndHashCode.Include()
	private String caisseCode;

	private String description;

}
