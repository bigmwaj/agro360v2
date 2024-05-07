package com.agro360.operation.logic.finance;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.finance.RubriqueBean;
import com.agro360.bo.bean.finance.RubriqueSearchBean;
import com.agro360.bo.mapper.FinanceMapper;
import com.agro360.dao.common.IDao;
import com.agro360.dao.finance.IRubriqueDao;
import com.agro360.dto.finance.RubriqueDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

@Service
public class RubriqueOperation extends AbstractOperation<RubriqueDto, String> {

	@Autowired
	IRubriqueDao dao;

	@Override
	protected IDao<RubriqueDto, String> getDao() {
		return dao;
	}
	
	@RuleNamespace("stock/rubrique/create")
	public void createRubrique(ClientContext ctx, RubriqueBean bean) {
		var dto = new RubriqueDto();
		
		setDtoValue(dto::setRubriqueCode, bean.getRubriqueCode());
		setDtoValue(dto::setLibelle, bean.getLibelle());
		setDtoValue(dto::setType, bean.getType());
		setDtoValue(dto::setDescription, bean.getDescription());
		
		dto = super.save(dto);
	}
	
	@RuleNamespace("stock/rubrique/update")
	public void updateRubrique(ClientContext ctx, RubriqueBean bean) {
		var dto = dao.getReferenceById(bean.getRubriqueCode().getValue());
		
		setDtoChangedValue(dto::setDescription, bean.getDescription());
		setDtoChangedValue(dto::setLibelle, bean.getLibelle());
		
		dto = super.save(dto);	
	}
	
	@RuleNamespace("stock/rubrique/delete")
	public void deleteRubrique(ClientContext ctx, RubriqueBean bean) {
		var dto = dao.getReferenceById(bean.getRubriqueCode().getValue());
		dao.delete(dto);
	}
	
	public RubriqueBean findRubriqueByCode(ClientContext ctx, String rubriqueCode) {
		var dto = dao.getReferenceById(rubriqueCode);
		return FinanceMapper.map(dto);		
	}
	
	public List<RubriqueBean> findRubriquesByCriteria(ClientContext ctx, RubriqueSearchBean searchBean) {
			var rubrique = searchBean.getRubrique().getValue();
			if( rubrique != null ) {
				rubrique = rubrique.toUpperCase();
			}
			
			var type = searchBean.getType().getValue();
			
			var length = dao.countRubriquesByCriteria(rubrique, type);
	        searchBean.setLength(length);
	        return dao.findRubriquesByCriteria(searchBean.getOffset(), searchBean.getLimit(), 
	        		rubrique, type)
	        		.stream().map(FinanceMapper::map)
	        		.collect(Collectors.toList());
	}
}
