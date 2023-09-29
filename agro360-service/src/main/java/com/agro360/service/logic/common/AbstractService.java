package com.agro360.service.logic.common;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.agro360.dao.common.IDao;
import com.agro360.dto.common.AbstractDto;
import com.agro360.service.bean.common.AbstractBean;

public abstract class AbstractService<E extends AbstractDto, K> {

	protected final static String MESSAGES_MODEL_KEY = "messages";

	protected final static String ID_MODEL_KEY = "id";

	protected IDao<E, K> dao;

	protected abstract IDao<E, K> getDao();
	
	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}

	public E save(E dto) {
		dto.setCreateBy("A.MOUAFO");
		dto.setCreateAt(LocalDateTime.now());
		dto.setUpdateBy("A.MOUAFO");
		dto.setUpdateAt(LocalDateTime.now());
		return getDao().save(dto);
	}

	public void delete(E dto) {
		getDao().delete(dto);
	}

	protected <B extends AbstractBean> B applyRules(B bean, String context) {
		var metadataPath = getRulePath() + "/" + context;
		getLogger().debug("On charge le metadate {}", metadataPath);
		return bean;
	}
	
	protected abstract String getRulePath();
}
