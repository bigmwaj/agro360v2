package com.agro360.bo.mapper.production.avicole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.production.avicole.CycleBean;
import com.agro360.bo.bean.production.avicole.CycleSearchBean;
import com.agro360.bo.mapper.common.AbstractMapper;
import com.agro360.bo.mapper.stock.MagasinMapper;
import com.agro360.dto.production.avicole.CycleDto;

@Component
public class CycleMapper extends AbstractMapper {

	@Autowired
	private MagasinMapper magasinMapper;
	
	public CycleSearchBean mapToSearchBean() {
		var bean = new CycleSearchBean();
		return bean;
	}

	public CycleBean mapToBean(CycleDto dto) {
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
			bean.setMagasin(magasinMapper.map(dto.getMagasin()));
		}
		return bean;
	}
}
