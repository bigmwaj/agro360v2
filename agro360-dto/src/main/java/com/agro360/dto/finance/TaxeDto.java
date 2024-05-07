package com.agro360.dto.finance;

import com.agro360.dto.common.AbstractDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "FIN_TBL_TAXE")
public class TaxeDto extends AbstractDto {
	
	@Id
	@Column(name = "TAXE_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String taxeCode;
	
	@Column(name = "TAUX")
	private Double taux;

	@Column(name = "DESCRIPTION")
	private String description;

}
