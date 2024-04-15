package com.agro360.operation.logic.stock;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.stock.CasierBean;
import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.ICasierDao;
import com.agro360.dao.stock.IMagasinDao;
import com.agro360.dto.stock.CasierDto;
import com.agro360.dto.stock.CasierPk;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

@Service
public class CasierOperation extends AbstractOperation<CasierDto, CasierPk> {

	@Autowired
	ICasierDao dao;
	
	@Autowired
	IMagasinDao magasinDao;

	@Override
	protected IDao<CasierDto, CasierPk> getDao() {
		return dao;
	}
	
	@RuleNamespace("stock/magasin/casier/create")
	public void createCasier(ClientContext ctx, MagasinBean magasin, CasierBean bean) {
		var dto = new CasierDto();
		
		setDtoValue(dto::setCasierCode, bean.getCasierCode());
		setDtoValue(dto::setDescription, bean.getDescription());
		setDtoValue(dto::setMagasinCode, magasin.getMagasinCode());
		
		super.save(dto);		
	}
	
	@RuleNamespace("stock/magasin/casier/update")
	public void updateCasier(ClientContext ctx, MagasinBean magasin, CasierBean bean) {
		
		var dto = dao.getReferenceById(buildPK(magasin, bean));

		setDtoChangedValue(dto::setDescription, bean.getDescription());
		
		super.save(dto);
		
	}
	
	@RuleNamespace("stock/magasin/casier/delete")
	public void deleteCasier(ClientContext ctx, MagasinBean magasin, CasierBean bean) {
		var dto = dao.getReferenceById(buildPK(magasin, bean));
		
		super.delete(dto);
	}
	
	public List<CasierBean> findCasiersByMagasinCode(ClientContext ctx, String magasinCode) {
		return dao.findAllByMagasinCode(magasinCode).stream()
				.map(StockMapper::map)
				.collect(Collectors.toList());
	}
	
	private CasierPk buildPK(MagasinBean magasin, CasierBean bean) {
		var magasinCode = magasin.getMagasinCode().getValue();
		var casierCode = bean.getCasierCode().getValue();
		
		return new CasierPk(magasinCode, casierCode);
	}
}
