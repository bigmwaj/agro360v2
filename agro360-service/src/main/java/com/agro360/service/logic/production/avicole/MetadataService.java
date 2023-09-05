package com.agro360.service.logic.production.avicole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.production.avicole.IMetadataDao;
import com.agro360.dto.production.avicole.MetadataDto;
import com.agro360.dto.production.avicole.MetadataPk;
import com.agro360.service.logic.common.AbstractService;

@Service
public class MetadataService extends AbstractService<MetadataDto, MetadataPk> {

	@Autowired
	private IMetadataDao dao;

	@Override
	protected IDao<MetadataDto, MetadataPk> getDao() {
		return dao;
	}

}
