package com.agro360.dto.stock;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

import com.agro360.dto.common.AbstractLigneDto;
import com.agro360.vd.stock.TypeOperationEnumVd;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "STOCK_TBL_OPE_CAISSE")
public class OperationCaisseDto extends AbstractLigneDto{

	@ManyToOne()
	@JoinColumns({ 
		@JoinColumn(name = "AGENT_CODE", insertable = false, updatable = false),
		@JoinColumn(name = "JOURNEE", insertable = false, updatable = false) 
	})
	private CaisseDto caisse;

	@Column(name = "DATE_OPERATION", nullable = false)
	private LocalDateTime dateOperation;

	@Enumerated(EnumType.STRING)
	@Column(name = "TYPE_OPERATION", nullable = false)
	private TypeOperationEnumVd typeOperation;

}
