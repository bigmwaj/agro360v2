package com.agro360.operation.logic.finance;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.finance.TaxeBean;
import com.agro360.bo.mapper.FinanceMapper;
import com.agro360.dao.common.IDao;
import com.agro360.dao.finance.ITaxeDao;
import com.agro360.dto.finance.TaxeDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

@Service
public class TaxeOperation extends AbstractOperation<TaxeDto, String> {

	@Autowired
	private ITaxeDao dao;
	
	@Override
	protected IDao<TaxeDto, String> getDao() {
		return dao;
	}
	
	public List<TaxeBean> findAllTaxes(ClientContext ctx) {
		return dao.findAll()
				.stream().map(FinanceMapper::map)
				.collect(Collectors.toList());
	}

	@RuleNamespace("av/taxe/create")
	public void createTaxe(ClientContext ctx, TaxeBean bean) {
		var dto = new TaxeDto();
		
		setDtoValue(dto::setTaxeCode, bean.getTaxeCode());
		setDtoValue(dto::setTaux, bean.getTaux());
		setDtoValue(dto::setDescription, bean.getDescription());
		
		dto = super.save(ctx, dto);	
	}

	@RuleNamespace("av/taxe/update")
	public void updateTaxe(ClientContext ctx, TaxeBean bean) {
		var dto = dao.getReferenceById(bean.getTaxeCode().getValue());

		setDtoChangedValue(dto::setTaux, bean.getTaux());
		setDtoChangedValue(dto::setDescription, bean.getDescription());
		
		super.save(ctx, dto);		
	}

	@RuleNamespace("av/taxe/delete")
	public void deleteTaxe(ClientContext ctx, TaxeBean bean) {
		var dto = dao.getReferenceById(bean.getTaxeCode().getValue());
		dao.delete(dto);
	}

	public TaxeBean findTaxeByCode(ClientContext ctx, String taxeCode) {
		var dto = dao.getReferenceById(taxeCode);
		return FinanceMapper.map(dto);	
	}

}
