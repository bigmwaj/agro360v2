package com.agro360.operation.logic.finance;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.finance.CompteBean;
import com.agro360.bo.bean.finance.CompteSearchBean;
import com.agro360.bo.bean.finance.EtatCompteBean;
import com.agro360.bo.mapper.FinanceMapper;
import com.agro360.dao.common.IDao;
import com.agro360.dao.finance.ICompteDao;
import com.agro360.dto.finance.CompteDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

@Service
public class CompteOperation extends AbstractOperation<CompteDto, String> {

	@Autowired
	ICompteDao dao;

	@Override
	protected IDao<CompteDto, String> getDao() {
		return dao;
	}
	
	@RuleNamespace("stock/compte/create")
	public void createCompte(ClientContext ctx, CompteBean bean) {
		var dto = new CompteDto();
		
		setDtoValue(dto::setCompteCode, bean.getCompteCode());
		setDtoValue(dto::setDescription, bean.getDescription());
		
		dto = super.save(dto);
	}
	
	@RuleNamespace("stock/compte/update")
	public void updateCompte(ClientContext ctx, CompteBean bean) {
		var dto = dao.getReferenceById(bean.getCompteCode().getValue());
		
		setDtoChangedValue(dto::setDescription, bean.getDescription());
		
		dto = super.save(dto);
	}
	
	@RuleNamespace("stock/compte/delete")
	public void deleteCompte(ClientContext ctx, CompteBean bean) {
		var dto = dao.getReferenceById(bean.getCompteCode().getValue());
		dao.delete(dto);
	}
	
	public CompteBean findCompteByCode(ClientContext ctx, String compteCode) {
		var dto = dao.getReferenceById(compteCode);
		return FinanceMapper.map(dto);		
	}
	
	public List<CompteBean> findComptesByCriteria(ClientContext ctx, CompteSearchBean searchBean) {
		var example = Example.of(new CompteDto());
		var compteCode = searchBean.getCompteCode().getValue();
		if( compteCode != null ) {
			example.getProbe().setCompteCode(compteCode);
		}
		return dao.findAll(example).stream()
				.map(FinanceMapper::map)
				.collect(Collectors.toList());
	}
	
	private EtatCompteBean initSolde(EtatCompteBean bean) {
		var compteCode = bean.getCompte().getCompteCode().getValue();
		var solde = dao.calculateSolde(compteCode);
		bean.getSolde().setValue(solde != null ? solde : BigDecimal.ZERO);
		return bean;
	}
	
	public List<EtatCompteBean> generateEtatCompte(ClientContext ctx) {		
		return dao.findAll().stream()
				.map(FinanceMapper::map)
				.map(EtatCompteBean::new)
				.map(this::initSolde)
				.collect(Collectors.toList());
	}
}
