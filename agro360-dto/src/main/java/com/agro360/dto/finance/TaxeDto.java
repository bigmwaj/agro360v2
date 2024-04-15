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
	@Column(name = "TAXE_CODE", updatable = false, length = 16)
	@EqualsAndHashCode.Include()
	private String taxeCode;
	
	@Column(name = "TAUX", nullable = false)
	private Double taux;

	@Column(name = "DESCRIPTION", length = 256)
	private String description;

}
