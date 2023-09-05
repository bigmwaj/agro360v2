package com.agro360.dao.production.avicole;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.production.avicole.MetadataDto;
import com.agro360.dto.production.avicole.MetadataPk;

@Repository
public interface IMetadataDao extends IDao<MetadataDto, MetadataPk> {
	
}
