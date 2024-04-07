package com.agro360.bo.mapper.production.avicole;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.production.avicole.CycleBean;
import com.agro360.bo.bean.production.avicole.MetadataBean;
import com.agro360.bo.mapper.common.AbstractMapper;
import com.agro360.dto.production.avicole.CycleDto;
import com.agro360.dto.production.avicole.MetadataDto;

@Component
public class MetadataMapper extends AbstractMapper {


	public MetadataBean mapToBean(MetadataDto dto) {
		var bean = new MetadataBean();
		bean.getMetadataCode().setValue(dto.getMetadataCode());
		bean.getValue().setValue(dto.getValue());
		return bean;
	}

	public MetadataDto mapToDto(CycleDto cycle, CycleBean cycleBean, MetadataBean bean) {
//		var cycleCode = cycleBean.getCycleCode().getValue();
//		var metadataCode = bean.getMetadataCode().getValue();

		MetadataDto dto = new MetadataDto();
//		MetadataPk pk;
//
//		if (null != cycleCode && null != metadataCode && dao.existsById(pk = new MetadataPk(cycleCode, metadataCode))) {
//			dto = dao.getReferenceById(pk);
//		} else {
//			dto = new MetadataDto();
//			dto.setCycle(cycle);
//			dto.setMetadataCode(metadataCode);
//		}

		setDtoValue(dto::setValue, bean.getValue());
		return dto;
	}
}
