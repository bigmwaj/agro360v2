package com.agro360.dto.common;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Bigmwaj
 */
@Data
@EqualsAndHashCode(callSuper = false)
@MappedSuperclass
public abstract class AbstractDto {

	@Column(name = "CREATED_BY", updatable = false)
	private String createBy;

	@Column(name = "CREATED_AT", updatable = false)
	private LocalDateTime createAt;

	@Column(name = "UPDATED_BY")
	private String updateBy;

	@Column(name = "UPDATED_AT")
	private LocalDateTime updateAt;
}
