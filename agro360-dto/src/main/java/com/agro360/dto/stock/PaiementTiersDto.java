package com.agro360.dto.stock;

import com.agro360.dto.common.AbstractDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaiementTiersDto extends AbstractDto {
	@EqualsAndHashCode.Include()
	private String paiementId;

	private String montant;

	private String statut;

	private String datePaiement;

	private String personCode;

	private String processus;

	private String source;

	private String description;

}
