package com.agro360.service.mapper.stock;

import java.util.Objects;

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
	ICasierDao dao;
	
	public CasierBean mapToBean(CasierDto dto) {
		CasierBean bean = new CasierBean();
		bean.getCasierCode().setValue(dto.getCasierCode());
		bean.getDescription().setValue(dto.getDescription());
		return bean;
	}

	public CasierDto mapToDto(MagasinDto magasin, MagasinBean magasinBean, CasierBean bean) {
		CasierDto dto = null;
		var magasinCode = magasinBean.getMagasinCode().getValue();
		var casierCode = bean.getCasierCode().getValue();
		
		if (Objects.nonNull(magasinCode) && Objects.nonNull(casierCode)) {
			CasierPk pk = new CasierPk(magasinCode, casierCode);
			
			if (dao.existsById(pk)) {
				dto = dao.getById(pk);
			}
		}
		if (dto == null) {
			dto = new CasierDto();
			dto.setMagasin(magasin);
			dto.setCasierCode(casierCode);
		}
		dto.setDescription(bean.getDescription().getValue());
		return dto;
	}
}
