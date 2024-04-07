package com.agro360.bo.mapper.av;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.CommandeSearchBean;
import com.agro360.bo.mapper.common.AbstractMapper;
import com.agro360.bo.mapper.core.PartnerMapper;
import com.agro360.bo.mapper.finance.CompteMapper;
import com.agro360.bo.mapper.stock.MagasinMapper;
import com.agro360.dto.av.CommandeDto;

@Component("av/CommandeMapper")
public class CommandeMapper extends AbstractMapper {

	@Autowired
	private PartnerMapper partnerMapper;

	@Autowired
	private MagasinMapper magasinMapper;
	
	@Autowired
	CompteMapper compteMapper;
	
	public CommandeSearchBean mapToSearchBean() {
		var bean = new CommandeSearchBean();
		return bean;
	}

	public CommandeBean map(CommandeDto dto) {
		var bean = new CommandeBean();

		bean.getCommandeCode().setValue(dto.getCommandeCode());
		bean.getDate().setValue(dto.getDate());
		bean.getStatus().setValue(dto.getStatus());
		bean.getDescription().setValue(dto.getDescription());
		bean.getType().setValue(dto.getType());
		bean.getPaiementComptant().setValue(dto.getPaiementComptant());
		
		if (null != dto.getPartner()) {
			bean.setPartner(partnerMapper.map(dto.getPartner()));
		}

		if (dto.getMagasin() != null) {
			bean.setMagasin(magasinMapper.map(dto.getMagasin()));
		}
		
		if( dto.getCompte() != null ) {
			bean.setCompte(compteMapper.map(dto.getCompte()));
		}

		return bean;
	}
}
