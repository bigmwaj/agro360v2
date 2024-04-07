package com.agro360.bo.mapper.av;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.FactureBean;
import com.agro360.bo.bean.av.FactureSearchBean;
import com.agro360.bo.mapper.common.AbstractMapper;
import com.agro360.bo.mapper.core.PartnerMapper;
import com.agro360.dto.av.FactureDto;

@Component("av/FactureMapper")
public class FactureMapper extends AbstractMapper {

	@Autowired
	PartnerMapper partnerMapper;
	
	@Autowired
	CommandeMapper commandeMapper;
	
	public FactureSearchBean mapToSearchBean() {
		var bean = new FactureSearchBean();
		return bean;
	}

	public FactureBean map(FactureDto dto) {
		var bean = new FactureBean();

		bean.getFactureCode().setValue(dto.getFactureCode());		
		bean.getDate().setValue(dto.getDate());
		bean.getStatus().setValue(dto.getStatus());
		bean.getDescription().setValue(dto.getDescription());
		bean.getMontant().setValue(dto.getMontant());
		bean.getType().setValue(dto.getType());
		
		if (null != dto.getPartner()) {
			bean.setPartner(partnerMapper.map(dto.getPartner()));
		}
		
		if (null != dto.getCommande()) {
			bean.setCommande(commandeMapper.map(dto.getCommande()));
		}

		return bean;
	}
}
