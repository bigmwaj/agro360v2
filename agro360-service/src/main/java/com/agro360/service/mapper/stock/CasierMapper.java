package com.agro360.service.mapper.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.stock.ICasierDao;
import com.agro360.dto.stock.CasierDto;
import com.agro360.dto.stock.CasierPk;
import com.agro360.dto.stock.MagasinDto;
import com.agro360.service.bean.stock.CasierBean;
import com.agro360.service.bean.stock.MagasinBean;
import com.agro360.service.mapper.common.AbstractMapper;

@Component
public class CasierMapper extends AbstractMapper {

	@Autowired
	private ICasierDao dao;

	public CasierBean mapToBean(CasierDto dto) {
		var bean = new CasierBean();
		bean.getCasierCode().setValue(dto.getCasierCode());
		bean.getDescription().setValue(dto.getDescription());
		return bean;
	}

	public CasierDto mapToDto(MagasinDto magasin, MagasinBean magasinBean, CasierBean bean) {
		var magasinCode = magasinBean.getMagasinCode().getValue();
		var casierCode = bean.getCasierCode().getValue();

		CasierDto dto;
		CasierPk pk;

		if (null != magasinCode && null != casierCode && dao.existsById(pk = new CasierPk(magasinCode, casierCode))) {
			dto = dao.getReferenceById(pk);
		} else {
			dto = new CasierDto();
			dto.setMagasin(magasin);
			dto.setCasierCode(casierCode);
		}

		setDtoValue(dto::setDescription, bean.getDescription());
		return dto;
	}
}
