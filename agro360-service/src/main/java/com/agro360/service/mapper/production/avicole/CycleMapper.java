package com.agro360.service.mapper.production.avicole;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.production.avicole.ICycleDao;
import com.agro360.dao.stock.IMagasinDao;
import com.agro360.dto.production.avicole.CycleDto;
import com.agro360.service.bean.production.avicole.CycleBean;
import com.agro360.service.bean.production.avicole.CycleSearchBean;
import com.agro360.service.logic.production.avicole.helper.MetadataHelper;
import com.agro360.service.mapper.common.AbstractMapper;
import com.agro360.service.mapper.stock.MagasinMapper;
import com.agro360.service.mapper.stock.StockSharedMapperHelper;

@Component
public class CycleMapper extends AbstractMapper {

	public final static String OPTION_MAP_METADATA_KEY = "MAP_METADATA";

	@Autowired
	private ICycleDao dao;
	
	@Autowired
	private IMagasinDao magasinDao;
	
	@Autowired
	private MetadataHelper metadataHelper;

	@Autowired
	private MagasinMapper magasinMapper;
	
	public CycleSearchBean mapToSearchBean() {
		var bean = new CycleSearchBean();
		return bean;
	}

	public CycleBean mapToBean(CycleDto dto, Map<String, Object> options) {
		var cycleCode = dto.getCycleCode();
		var bean = new CycleBean();
		
		bean.getCycleCode().setValue(cycleCode);
		bean.getDescription().setValue(dto.getDescription());
		bean.getRacePlanifiee().setValue(dto.getRacePlanifiee());
		bean.getQuantitePlanifiee().setValue(dto.getQuantitePlanifiee());
		bean.getDatePlanifiee().setValue(dto.getDatePlanifiee());
		bean.getRaceEffective().setValue(dto.getRaceEffective());
		bean.getQuantiteEffective().setValue(dto.getQuantiteEffective());
		bean.getDateEffective().setValue(dto.getDateEffective());		
		bean.getStatus().setValue(dto.getStatus());
		bean.getStatusDate().setValue(dto.getStatusDate());

		if (dto.getMagasin() != null) {
			bean.setMagasin(magasinMapper.mapToBean(dto.getMagasin()));
		}
		
		var mapMetadata = options.getOrDefault(OPTION_MAP_METADATA_KEY, null);
		if (Objects.nonNull(cycleCode) && mapMetadata instanceof Boolean && (Boolean) mapMetadata) {
			try {
				var modelBeans = metadataHelper.findMetadatasFromXmlModel();
				bean.getMetadatas().addAll(modelBeans);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bean;
	}

	public CycleBean mapToBean(CycleDto dto) {
		return mapToBean(dto, Collections.emptyMap());
	}

	public CycleDto mapToDto(CycleBean bean) {
		var dto = AvicoleSharedMapperHelper.mapToDto(dao, bean);
		
		setDtoValue(dto::setCycleCode, bean.getCycleCode());
		setDtoValue(dto::setDescription, bean.getDescription());
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setStatusDate, bean.getStatusDate());
		setDtoValue(dto::setQuantitePlanifiee, bean.getQuantitePlanifiee());
		setDtoValue(dto::setQuantiteEffective, bean.getQuantiteEffective());
		setDtoValue(dto::setRacePlanifiee, bean.getRacePlanifiee());
		setDtoValue(dto::setRaceEffective, bean.getRaceEffective());
		setDtoValue(dto::setDatePlanifiee, bean.getDatePlanifiee());
		setDtoValue(dto::setDateEffective, bean.getDateEffective());

		dto.setMagasin(StockSharedMapperHelper.mapToDto(magasinDao, bean.getMagasin()));

		return dto;
	}
}
