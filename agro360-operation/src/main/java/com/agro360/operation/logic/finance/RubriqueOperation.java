package com.agro360.operation.logic.finance;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.finance.RubriqueBean;
import com.agro360.bo.bean.finance.RubriqueSearchBean;
import com.agro360.bo.mapper.finance.RubriqueMapper;
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

	@Autowired
	RubriqueMapper mapper;

	@Override
	protected IDao<RubriqueDto, String> getDao() {
		return dao;
	}
	
	@Override
	protected String getRulePath() {
		return "stock/rubrique";
	}
	
	@RuleNamespace("stock/rubrique/create")
	public void createRubrique(ClientContext ctx, RubriqueBean bean) {
		var dto = new RubriqueDto();
		
		setDtoValue(dto::setRubriqueCode, bean.getRubriqueCode());
		setDtoValue(dto::setNom, bean.getNom());
		setDtoValue(dto::setType, bean.getType());
		setDtoValue(dto::setDescription, bean.getDescription());
		
		dto = super.save(dto);
	}
	
	@RuleNamespace("stock/rubrique/update")
	public void updateRubrique(ClientContext ctx, RubriqueBean bean) {
		var dto = dao.getReferenceById(bean.getRubriqueCode().getValue());
		
		setDtoChangedValue(dto::setDescription, bean.getDescription());
		setDtoChangedValue(dto::setNom, bean.getNom());
		
		dto = super.save(dto);	
	}
	
	@RuleNamespace("stock/rubrique/delete")
	public void deleteRubrique(ClientContext ctx, RubriqueBean bean) {
		var dto = dao.getReferenceById(bean.getRubriqueCode().getValue());
		dao.delete(dto);
	}
	
	public RubriqueBean findRubriqueByCode(ClientContext ctx, String rubriqueCode) {
		var dto = dao.getReferenceById(rubriqueCode);
		return mapper.map(dto);		
	}
	
	public List<RubriqueBean> findRubriquesByCriteria(ClientContext ctx, RubriqueSearchBean searchBean) {
		var example = Example.of(new RubriqueDto());
		if( searchBean.getRubriqueCode().getValue() != null ) {
			example.getProbe().setRubriqueCode(searchBean.getRubriqueCode().getValue());
		}
		if( searchBean.getType().getValue() != null ) {
			example.getProbe().setType(searchBean.getType().getValue());
		}
		return dao.findAll(example).stream().map(mapper::map).collect(Collectors.toList());
	}
}
