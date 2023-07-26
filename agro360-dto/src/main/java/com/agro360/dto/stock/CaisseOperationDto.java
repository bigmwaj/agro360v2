package com.agro360.dto.stock;

import com.agro360.dto.common.AbstractLineDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CaisseOperationDto extends AbstractLineDto
{
	private String caisseCode;

	private String dateOperation;

	private String typeOperation;

}
