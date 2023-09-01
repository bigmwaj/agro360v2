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
public abstract class AbstractStatusTrackingDto<T> extends AbstractDto{

	@Column(name = "STATUS_DATE", updatable = true)
	private LocalDateTime statusDate;
	
	public abstract T getStatus();
}
