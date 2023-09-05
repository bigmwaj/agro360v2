package com.agro360.dto.production.avicole;

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
@Entity(name = "P_AVI_TBL_METADATA")
@IdClass(MetadataPk.class)
public class MetadataDto extends AbstractDto {

	@Id
	@ManyToOne()
	@JoinColumn(name = "CYCLE_CODE", nullable = false, updatable = false)
	@EqualsAndHashCode.Include()
	private CycleDto cycle;
	
	@Id
	@Column(name = "METADATA_CODE", length = 64, nullable = false, updatable = false)
	@EqualsAndHashCode.Include()
	private String metadataCode;

	@Column(name = "VALUE", length = 512, nullable = false)
	private String value;
}