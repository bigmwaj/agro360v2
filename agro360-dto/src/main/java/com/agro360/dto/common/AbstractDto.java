package com.agro360.dto.common;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Bigmwaj
 */
@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public abstract class AbstractDto {
	
	protected static String TEMP_PREF = "###";

	@Column(name = "CREATED_BY", nullable = false, updatable = false)
	private String createBy;

	@Column(name = "CREATED_AT", nullable = false, updatable = false)
	private LocalDateTime createAt;

	@Column(name = "UPDATED_BY", nullable = false, updatable = true)
	private String updateBy;

	@Column(name = "UPDATED_AT", nullable = false, updatable = true)
	private LocalDateTime updateAt;
}