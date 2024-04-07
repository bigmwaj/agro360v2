package com.agro360.operation.logic.stock;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.bean.stock.MagasinSearchBean;
import com.agro360.bo.mapper.stock.MagasinMapper;
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

	@Autowired
	MagasinMapper mapper;

	@Override
	protected IDao<MagasinDto, String> getDao() {
		return dao;
	}
	
	@Override
	protected String getRulePath() {
		return "stock/magasin";
	}

	@RuleNamespace("stock/magasin/create")
	public void createMagasin(ClientContext ctx, MagasinBean bean) {
		var dto = new MagasinDto();
		
		setDtoValue(dto::setMagasinCode, bean.getMagasinCode());
		setDtoValue(dto::setDescription, bean.getDescription());
		
		dto = super.save(dto);	
	}

	@RuleNamespace("stock/magasin/update")
	public void updateMagasin(ClientContext ctx, MagasinBean bean) {
		var dto = dao.getReferenceById(bean.getMagasinCode().getValue());

		setDtoChangedValue(dto::setDescription, bean.getDescription());
		
		dto = super.save(dto);	
	}

	@RuleNamespace("stock/magasin/delete")
	public void deleteMagasin(ClientContext ctx, MagasinBean bean) {
		var dto = dao.getReferenceById(bean.getMagasinCode().getValue());
		dao.delete(dto);
	}

	public MagasinBean findMagasinByCode(ClientContext ctx, String magasinCode) {
		var dto = dao.getReferenceById(magasinCode);
		return mapper.map(dto);	
	}
	
	public List<MagasinBean> findMagasinsByCriteria(ClientContext ctx, MagasinSearchBean searchBean) {
		return dao.findAll().stream().map(mapper::map).collect(Collectors.toList());
	}
}
