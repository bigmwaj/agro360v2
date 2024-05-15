package com.agro360.operation.logic.stock;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.stock.OperationBean;
import com.agro360.bo.bean.stock.OperationSearchBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IOperationDao;
import com.agro360.dto.stock.OperationDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

@Service("stock/OperationOperation")
public class OperationOperation extends AbstractOperation<OperationDto, Long> {

	@Autowired
	private IOperationDao dao;

	@Override
	protected IDao<OperationDto, Long> getDao() {
		return dao;
	}

	@RuleNamespace("stock/operation/create")
	public void createOperation(ClientContext ctx, OperationBean bean) {
		var dto = new OperationDto();
		
		setDtoValue(dto::setMagasinCode, bean.getMagasinCode());
		setDtoValue(dto::setArticleCode, bean.getArticleCode());
		setDtoValue(dto::setVariantCode, bean.getVariantCode());
		setDtoValue(dto::setQuantite, bean.getQuantite());
		setDtoValue(dto::setPrixUnitaire, bean.getPrixUnitaire());
		setDtoValue(dto::setType, bean.getType());
		setDtoValue(dto::setDate, bean.getDate());
		setDtoValue(dto::setInventaireAvant, bean.getInventaireAvant());
		
		dto = super.save(ctx, dto);		
	}

	public OperationBean findOperationByCode(ClientContext ctx, Long operationId) {
		var dto = dao.getReferenceById(operationId);
		return StockMapper.map(dto);	
	}
	
	public List<OperationBean> findOperationsByCriteria(ClientContext ctx, OperationSearchBean searchBean) {
		var example = Example.of(new OperationDto());
		
		if( searchBean.getMagasinCode().getValue() != null ) {
			example.getProbe().setMagasinCode(searchBean.getMagasinCode().getValue());
		}
		
		if( searchBean.getArticleCode().getValue() != null ) {
			example.getProbe().setArticleCode(searchBean.getArticleCode().getValue());
		}
		
		if( searchBean.getVariantCode().getValue() != null ) {
			example.getProbe().setVariantCode(searchBean.getVariantCode().getValue());
		}
		return dao.findAll(example).stream().map(StockMapper::map).collect(Collectors.toList());
	}
}
