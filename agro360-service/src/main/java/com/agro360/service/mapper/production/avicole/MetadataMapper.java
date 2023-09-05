package com.agro360.service.mapper.production.avicole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.production.avicole.IMetadataDao;
import com.agro360.dto.production.avicole.CycleDto;
import com.agro360.dto.production.avicole.MetadataDto;
import com.agro360.dto.production.avicole.MetadataPk;
import com.agro360.service.bean.production.avicole.CycleBean;
import com.agro360.service.bean.production.avicole.MetadataBean;
import com.agro360.service.mapper.common.AbstractMapper;

@Component
public class MetadataMapper extends AbstractMapper {

	@Autowired
	private IMetadataDao dao;

	public MetadataBean mapToBean(MetadataDto dto) {
		var bean = new MetadataBean();
		bean.getMetadataCode().setValue(dto.getMetadataCode());
		bean.getValue().setValue(dto.getValue());
		return bean;
	}

	public MetadataDto mapToDto(CycleDto cycle, CycleBean cycleBean, MetadataBean bean) {
		var cycleCode = cycleBean.getCycleCode().getValue();
		var metadataCode = bean.getMetadataCode().getValue();

		MetadataDto dto;
		MetadataPk pk;

		if (null != cycleCode && null != metadataCode && dao.existsById(pk = new MetadataPk(cycleCode, metadataCode))) {
			dto = dao.getReferenceById(pk);
		} else {
			dto = new MetadataDto();
			dto.setCycle(cycle);
			dto.setMetadataCode(metadataCode);
		}

		setDtoValue(dto::setValue, bean.getValue());
		return dto;
	}
}
