package com.agro360.dto.stock;

import com.agro360.dto.common.AbstractDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "STOCK_TBL_CASIER")
@IdClass(CasierPk.class)
public class CasierDto extends AbstractDto {
	
	public static final int CASIER_CODE_LENGTH = 16;

	@Id
	@ManyToOne()
	@JoinColumn(name = "MAGASIN_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private MagasinDto magasin;

	@Id
	@Column(name = "CASIER_CODE", updatable = false, length = CASIER_CODE_LENGTH)
	@EqualsAndHashCode.Include()
	private String casierCode;

	@Column(name = "DESCRIPTION", length = 64)
	private String description;

}
