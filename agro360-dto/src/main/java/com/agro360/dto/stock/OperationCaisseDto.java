package com.agro360.dto.stock;

import java.time.LocalDateTime;

import com.agro360.dto.common.AbstractLigneDto;
import com.agro360.vd.stock.StatusCaisseEnumVd;
import com.agro360.vd.stock.TypeOperationEnumVd;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "STOCK_TBL_OPE_CAISSE")
public class OperationCaisseDto extends AbstractLigneDto{

	@ManyToOne()
	@JoinColumns({
		@JoinColumn(name = "MAGASIN_CODE", updatable = false, nullable = false, referencedColumnName = "MAGASIN_CODE"),
		@JoinColumn(name = "AGENT_CODE", updatable = false, nullable = false, referencedColumnName = "AGENT_CODE"),
		@JoinColumn(name = "JOURNEE", updatable = false, nullable = false, referencedColumnName = "JOURNEE") 
	})
	private CaisseDto caisse;
	
	@Column(name = "DATE_OPERATION", nullable = false)
	private LocalDateTime dateOperation;
	
	
	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE_OPERATION", nullable = false, length = StatusCaisseEnumVd.COLUMN_LENGTH)
	private TypeOperationEnumVd typeOperation;

}
