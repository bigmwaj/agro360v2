package com.agro360.dto.stock;

import com.agro360.dto.common.AbstractDto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CaisseDto extends AbstractDto {

	private String caisseCode;

	private String description;

}
