package com.agro360.dto.core;

import com.agro360.dto.common.AbstractDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity(name = "CORE_TBL_CODE_GEN")
public class CodeGenetorDto extends AbstractDto {
	
	@Id
	@Column(name = "CODE_GEN_CODE", nullable = false, updatable = false, length = 16)
	@EqualsAndHashCode.Include()
	private String codeGenCode;
	
	@Column(name = "PREF", nullable = false, updatable = false, length = 4)
	private String prefix;

	@Column(name = "SEQ", nullable = false)
	private Long sequence;

}
