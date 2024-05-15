package com.agro360.operation.logic.stock;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.bean.stock.MagasinSearchBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IMagasinDao;
import com.agro360.dto.stock.MagasinDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;


@Service
public class MagasinOperation extends AbstractOperation<MagasinDto, String> {

	@Autowired
	IMagasinDao dao;

	@Override
	protected IDao<MagasinDto, String> getDao() {
		return dao;
	}

	@RuleNamespace("stock/magasin/create")
	public void createMagasin(ClientContext ctx, MagasinBean bean) {
		var dto = new MagasinDto();
		
		setDtoValue(dto::setMagasinCode, bean.getMagasinCode());
		setDtoValue(dto::setDescription, bean.getDescription());
		
		dto = super.save(ctx, dto);	
	}

	@RuleNamespace("stock/magasin/update")
	public void updateMagasin(ClientContext ctx, MagasinBean bean) {
		var dto = dao.getReferenceById(bean.getMagasinCode().getValue());

		setDtoChangedValue(dto::setDescription, bean.getDescription());
		
		dto = super.save(ctx, dto);	
	}

	@RuleNamespace("stock/magasin/delete")
	public void deleteMagasin(ClientContext ctx, MagasinBean bean) {
		var dto = dao.getReferenceById(bean.getMagasinCode().getValue());
		dao.delete(dto);
	}

	public MagasinBean findMagasinByCode(ClientContext ctx, String magasinCode) {
		var dto = dao.getReferenceById(magasinCode);
		return StockMapper.map(dto);	
	}
	
	public List<MagasinBean> findMagasinsByCriteria(ClientContext ctx, MagasinSearchBean searchBean) {
		var code = searchBean.getMagasinCode().getValue();
		if( code != null ) {
			code = code.toUpperCase();
		}
        var length = dao.countMagasinsByCriteria( code);
        searchBean.setLength(length);
        return dao.findMagasinsByCriteria(searchBean.getOffset(), searchBean.getLimit(), code)
        		.stream().map(StockMapper::map)
        		.collect(Collectors.toList());
	}
}
