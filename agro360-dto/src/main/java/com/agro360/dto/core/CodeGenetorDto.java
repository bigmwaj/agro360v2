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
	@Column(name = "CODE_GEN_CODE", updatable = false)
	@EqualsAndHashCode.Include()
	private String codeGenCode;
	
	@Column(name = "PREF", updatable = false)
	private String prefix;

	@Column(name = "SEQ")
	private Long sequence;

}
