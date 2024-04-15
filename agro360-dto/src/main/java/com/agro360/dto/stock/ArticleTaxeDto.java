package com.agro360.dto.stock;

import com.agro360.dto.common.AbstractDto;
import com.agro360.dto.finance.TaxeDto;

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
@Entity(name = "STOCK_TBL_ARTICLE_TAXE")
@IdClass(ArticleTaxePk.class)
public class ArticleTaxeDto extends AbstractDto {
	
	@Id
	@Column(name = "ARTICLE_CODE", updatable = false, length = 16)
	@EqualsAndHashCode.Include()
	private String articleCode;
	
	@Id
	@EqualsAndHashCode.Include()
	@JoinColumn(name = "TAXE_CODE")
	@ManyToOne()
	private TaxeDto taxe;
	
}
