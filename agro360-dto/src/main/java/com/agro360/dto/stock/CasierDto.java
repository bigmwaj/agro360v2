package com.agro360.dto.stock;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.agro360.dto.common.AbstractDto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity(name = "STOCK_TBL_CASIER")
@IdClass(CasierPk.class)
public class CasierDto extends AbstractDto {
	
	public static final int CODE_LENGTH = 16;

	@Id
	@ManyToOne()
	@JoinColumn(name = "MAGASIN_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private MagasinDto magasin;

	@Id
	@Column(name = "CASIER_CODE", updatable = false, length = CODE_LENGTH)
	@EqualsAndHashCode.Include()
	private String casierCode;

	@Column(name = "DESCRIPTION", length = 64)
	private String description;

}
