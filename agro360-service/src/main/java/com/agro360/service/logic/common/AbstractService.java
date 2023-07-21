package com.agro360.service.logic.common;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.agro360.dao.common.IDao;
import com.agro360.dto.common.AbstractDto;

public abstract class AbstractService<E extends AbstractDto, K extends Serializable, Dao extends IDao<E, K>> {
	
	public abstract Dao getDao();

	public E save(E dto) {
		dto.setCreateBy("A.MOUAFO");
		dto.setCreateAt(LocalDateTime.now());
		dto.setUpdateBy("A.MOUAFO");
		dto.setUpdateAt(LocalDateTime.now());
		return getDao().save(dto);
	}

}
