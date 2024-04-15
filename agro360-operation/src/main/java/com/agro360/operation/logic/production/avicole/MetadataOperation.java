package com.agro360.operation.logic.production.avicole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.production.avicole.IMetadataDao;
import com.agro360.dto.production.avicole.MetadataDto;
import com.agro360.dto.production.avicole.MetadataPk;
import com.agro360.operation.logic.common.AbstractOperation;

@Service
public class MetadataOperation extends AbstractOperation<MetadataDto, MetadataPk> {

	@Autowired
	private IMetadataDao dao;

	@Override
	protected IDao<MetadataDto, MetadataPk> getDao() {
		return dao;
	}

}
