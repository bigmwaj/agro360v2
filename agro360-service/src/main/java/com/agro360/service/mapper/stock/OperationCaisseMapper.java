package com.agro360.service.mapper.stock;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.ICaisseDao;
import com.agro360.dao.stock.IOperationCaisseDao;
import com.agro360.dto.stock.OperationCaisseDto;
import com.agro360.service.bean.stock.CaisseBean;
import com.agro360.service.bean.stock.OperationCaisseBean;
import com.agro360.service.mapper.common.AstractLigneMapper;

@Component(value = "stock/OperationCaisseMapper")
public class OperationCaisseMapper extends AstractLigneMapper<OperationCaisseDto> {

	@Autowired
	private IOperationCaisseDao dao;
	
	@Autowired
	private ICaisseDao caisseDao;

	@Override
	protected IDao<OperationCaisseDto, Long> getDao() {
		return dao;
	}
	
	public OperationCaisseBean mapToBean(OperationCaisseDto dto) {
		return mapToBean(dto, Collections.emptyMap());
	}

	public OperationCaisseBean mapToBean(OperationCaisseDto dto, Map<String, Object> options) {
		OperationCaisseBean bean = (OperationCaisseBean) super.mapToBean(dto, new OperationCaisseBean(), options);

		bean.getDateOperation().setValue(dto.getDateOperation());
		bean.getTypeOperation().setValue(dto.getTypeOperation());
		return bean;
	}

	public OperationCaisseDto mapToDto(OperationCaisseBean bean) {
		var dto = super.mapToDto(bean, OperationCaisseDto::new);
		return dto;
	}

	public OperationCaisseDto mapToDto(CaisseBean caisseBean, OperationCaisseBean bean) {
		var dto = mapToDto(bean);
		dto.setCaisse(StockSharedMapperHelper.mapToDto(caisseDao, caisseBean));

		setDtoValue(dto::setDateOperation, bean.getDateOperation());
		setDtoValue(dto::setTypeOperation, bean.getTypeOperation());
		return dto;
	}
}
