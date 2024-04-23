package com.agro360.bo.mapper;

import com.agro360.bo.bean.finance.CompteBean;
import com.agro360.bo.bean.finance.CompteSearchBean;
import com.agro360.bo.bean.finance.RubriqueBean;
import com.agro360.bo.bean.finance.RubriqueSearchBean;
import com.agro360.bo.bean.finance.TaxeBean;
import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.bo.bean.finance.TransactionSearchBean;
import com.agro360.dto.finance.CompteDto;
import com.agro360.dto.finance.RubriqueDto;
import com.agro360.dto.finance.TaxeDto;
import com.agro360.dto.finance.TransactionDto;

public class FinanceMapper {

	public static CompteSearchBean buildCompteSearchBean() {
		var bean = new CompteSearchBean();
		return bean;
	}

	public static CompteBean map(CompteDto dto) {
		var bean = new CompteBean();
		bean.getCompteCode().setValue(dto.getCompteCode());

		bean.getDescription().setValue(dto.getDescription());

		return bean;
	}

	public static RubriqueSearchBean buildRubriqueSearchBean() {
		var bean = new RubriqueSearchBean();

		return bean;
	}

	public static RubriqueBean map(RubriqueDto dto) {
		var bean = new RubriqueBean();

		bean.getRubriqueCode().setValue(dto.getRubriqueCode());

		bean.getType().setValue(dto.getType());
		bean.getDescription().setValue(dto.getDescription());
		bean.getNom().setValue(dto.getNom());

		return bean;
	}

	public static TransactionSearchBean buildTransactionSearchBean() {
		var bean = new TransactionSearchBean();
		return bean;
	}

	public static TransactionBean map(TransactionDto dto) {
		var bean = new TransactionBean();

		bean.getTransactionCode().setValue(dto.getTransactionCode());
		bean.getNote().setValue(dto.getNote());
		bean.getMontant().setValue(dto.getMontant());
		bean.getDate().setValue(dto.getDate());
		bean.getType().setValue(dto.getType());

		bean.getStatus().setValue(dto.getStatus());

		if( dto.getPartner() != null ) {
			bean.setPartner(CoreMapper.map(dto.getPartner()));
		}

		if( dto.getRubrique() != null ) {
			bean.setRubrique(FinanceMapper.map(dto.getRubrique()));
		}

		if( dto.getCompte() != null ) {
			bean.setCompte(FinanceMapper.map(dto.getCompte()));
		}

		return bean;
	}

	public static TaxeBean map(TaxeDto dto) {
		var bean = new TaxeBean();

		bean.getTaxeCode().setValue(dto.getTaxeCode());
		bean.getTaux().setValue(dto.getTaux());
		bean.getDescription().setValue(dto.getDescription());

		return bean;
	}
}
