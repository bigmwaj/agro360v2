package com.agro360.bo.mapper;

import java.util.Map;

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
		bean.getType().setValue(dto.getType());
		bean.getLibelle().setValue(dto.getLibelle());
		bean.getDescription().setValue(dto.getDescription());
		if( dto.getPartner() != null ) {
			bean.setPartner(CoreMapper.map(dto.getPartner()));
		}

		return bean;
	}

	public static Map.Entry<Object, String> asOption(CompteBean bean) {
		var value = bean.getCompteCode().getValue();
		var label = bean.getLibelle().getValue();
		if( label == null ) {
			label = value;
		}
		return Map.of(Object.class.cast(value), label)
				.entrySet().stream()
				.findFirst().get();
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
		bean.getLibelle().setValue(dto.getLibelle());

		return bean;
	}

	public static Map.Entry<Object, String> asOption(RubriqueBean bean) {
		var value = bean.getRubriqueCode().getValue();
		var label = bean.getDescription().getValue();
		if( label == null ) {
			label = value;
		}
		return Map.of(Object.class.cast(value), label)
				.entrySet().stream()
				.findFirst().get();
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
		if( bean.getType().getValue() != null ) {
			bean.getType().setValueI18n(bean.getType().getValue().getLibelle());
		}
		bean.getStatus().setValue(dto.getStatus());
		if( bean.getStatus().getValue() != null ) {
			bean.getStatus().setValueI18n(bean.getStatus().getValue().getLibelle());
		}
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
