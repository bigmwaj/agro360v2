package com.agro360.dto.common;

import java.time.LocalDateTime;

import com.agro360.dto.core.PartnerDto;

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

	@Column(name = "CREATED_BY", nullable = false, updatable = false, length = PartnerDto.PARTNER_CODE_LENGTH)
	private String createBy;

	@Column(name = "CREATED_AT", nullable = false, updatable = false)
	private LocalDateTime createAt;

	@Column(name = "UPDATED_BY", nullable = false, updatable = true, length = PartnerDto.PARTNER_CODE_LENGTH)
	private String updateBy;

	@Column(name = "UPDATED_AT", nullable = false, updatable = true)
	private LocalDateTime updateAt;
}
