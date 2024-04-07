package com.agro360.bo.mapper.finance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.bo.bean.finance.TransactionSearchBean;
import com.agro360.bo.mapper.common.AbstractMapper;
import com.agro360.bo.mapper.core.PartnerMapper;
import com.agro360.dto.finance.TransactionDto;

@Component
public class TransactionMapper extends AbstractMapper {
	
	@Autowired
	PartnerMapper partnerMapper;
	
	@Autowired
	RubriqueMapper rubriqueMapper;
	
	@Autowired
	CompteMapper compteMapper;

	public TransactionSearchBean mapToSearchBean() {
		var bean = new TransactionSearchBean();
		return bean;
	}

	public TransactionBean map(TransactionDto dto) {
		var bean = new TransactionBean();
		
		bean.getTransactionCode().setValue(dto.getTransactionCode());
		bean.getNote().setValue(dto.getNote());
		bean.getMontant().setValue(dto.getMontant());
		bean.getDate().setValue(dto.getDate());
		bean.getType().setValue(dto.getType());

		bean.getStatus().setValue(dto.getStatus());
		
		if( dto.getPartner() != null ) {
			bean.setPartner(partnerMapper.map(dto.getPartner()));
		}
		
		if( dto.getRubrique() != null ) {
			bean.setRubrique(rubriqueMapper.map(dto.getRubrique()));
		}
		
		if( dto.getCompte() != null ) {
			bean.setCompte(compteMapper.map(dto.getCompte()));
		}

		return bean;
	}
}
