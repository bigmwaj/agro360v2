package com.agro360.dto.stock;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDto extends AbstractLineDto
{
	private String statut;

	private String dateTransaction;

	private String personCode;

	private String magasinCode;
	
	private String casierCode;

}
