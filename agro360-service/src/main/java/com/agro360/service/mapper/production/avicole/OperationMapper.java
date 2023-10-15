package com.agro360.service.mapper.production.avicole;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.common.IDao;
import com.agro360.dao.production.avicole.ICycleDao;
import com.agro360.dao.production.avicole.IOperationDao;
import com.agro360.dto.production.avicole.OperationDto;
import com.agro360.service.bean.production.avicole.CycleBean;
import com.agro360.service.bean.production.avicole.OperationBean;
import com.agro360.service.bean.production.avicole.OperationSearchBean;
import com.agro360.service.mapper.common.AstractLigneMapper;

@Component(value = "production/avicole/OperationMapper")
public class OperationMapper extends AstractLigneMapper<OperationDto>  {
	
	@Autowired
	private IOperationDao dao;
	
	@Autowired
	private ICycleDao cycleDao;
	
	@Override
	protected IDao<OperationDto, Long> getDao() {
		return dao;
	}
	
	public OperationBean mapToBean(OperationDto dto) {
		return mapToBean(dto, Collections.emptyMap());
	}

	public OperationBean mapToBean(OperationDto dto, Map<String, Object> options) {
		OperationBean bean = (OperationBean) super.mapToBean(dto, new OperationBean(), options);

		bean.getPhase().setValue(dto.getPhase());
		bean.getRubrique().setValue(dto.getRubrique());
		return bean;
	}

	public OperationDto mapToDto(OperationBean bean) {
		var dto = super.mapToDto(bean, OperationDto::new);
		return dto;
	}

	public OperationDto mapToDto(CycleBean cycleBean, OperationBean bean) {
		var dto = mapToDto(bean);
		dto.setCycle(AvicoleSharedMapperHelper.mapToDto(cycleDao, cycleBean));

		setDtoValue(dto::setPhase, bean.getPhase());
		setDtoValue(dto::setRubrique, bean.getRubrique());
		return dto;
	}

	public OperationSearchBean mapToSearchBean() {
		return new OperationSearchBean();
	}
}
