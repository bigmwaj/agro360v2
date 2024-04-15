package com.agro360.operation.logic.stock;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.bean.stock.UniteSearchBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IUniteDao;
import com.agro360.dto.stock.UniteDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

@Service
public class UniteOperation extends AbstractOperation<UniteDto, String> {

	@Autowired
	IUniteDao dao;

	@Override
	protected IDao<UniteDto, String> getDao() {
		return dao;
	}
	
	@RuleNamespace("stock/unite/create")
	public void createUnite(ClientContext ctx, UniteBean bean) {
		var dto = new UniteDto();
		
		setDtoValue(dto::setUniteCode, bean.getUniteCode());
		setDtoValue(dto::setDescription, bean.getDescription());
		
		dto = super.save(dto);
	}
	
	@RuleNamespace("stock/unite/update")
	public void updateUnite(ClientContext ctx, UniteBean bean) {
		var dto = dao.getReferenceById(bean.getUniteCode().getValue());
		
		setDtoChangedValue(dto::setDescription, bean.getDescription());
		
		dto = super.save(dto);	
	}
	
	@RuleNamespace("stock/unite/delete")
	public void deleteUnite(ClientContext ctx, UniteBean bean) {
		var dto = dao.getReferenceById(bean.getUniteCode().getValue());
		dao.delete(dto);
	}
	
	public UniteBean findUniteByCode(ClientContext ctx, String uniteCode) {
		var dto = dao.getReferenceById(uniteCode);
		return StockMapper.map(dto);		
	}
	
	public List<UniteBean> findUnitesByCriteria(ClientContext ctx, UniteSearchBean searchBean) {
		var example = Example.of(new UniteDto());
		if( searchBean.getUniteCode().getValue() != null ) {
			example.getProbe().setUniteCode(searchBean.getUniteCode().getValue());
		}
		return dao.findAll(example).stream()
				.map(StockMapper::map)
				.collect(Collectors.toList());
	}
}