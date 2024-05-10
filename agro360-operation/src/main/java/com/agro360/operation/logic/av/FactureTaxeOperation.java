package com.agro360.operation.logic.av;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.av.FactureBean;
import com.agro360.bo.bean.av.FactureTaxeBean;
import com.agro360.bo.mapper.AchatVenteMapper;
import com.agro360.dao.av.IFactureTaxeDao;
import com.agro360.dao.common.IDao;
import com.agro360.dao.finance.ITaxeDao;
import com.agro360.dto.av.FactureTaxeDto;
import com.agro360.dto.av.FactureTaxePk;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

@Service
public class FactureTaxeOperation extends AbstractOperation<FactureTaxeDto, FactureTaxePk> {

	@Autowired
	private IFactureTaxeDao dao;
	
	@Autowired
	private ITaxeDao taxeDao;

	@Override
	protected IDao<FactureTaxeDto, FactureTaxePk> getDao() {
		return dao;
	}
	
	@RuleNamespace("av/facture/taxe/create")
	public void createFacture(ClientContext ctx, FactureBean facture, FactureTaxeBean bean) {
		var dto = new FactureTaxeDto();
		dto.setFactureCode(facture.getFactureCode().getValue());	
		
		var taxe = taxeDao.getReferenceById(bean.getTaxe().getTaxeCode().getValue());
		dto.setTaxe(taxe);
		dto.setTaux(taxe.getTaux());
		dto.setMontant(bean.getMontant().getValue());
		
		super.save(dto);
	}

	@RuleNamespace("av/facture/taxe/delete")
	public void deleteFacture(ClientContext ctx, FactureBean facture, FactureTaxeBean bean) {
		var dto = findFactureTaxeDto(facture, bean);
		dao.delete(dto);
	}

	public FactureTaxeBean findFactureTaxeByCode(ClientContext ctx, String factureCode, String taxeCode) {
		return AchatVenteMapper.map(findFactureTaxeDto(factureCode, taxeCode));
	}

	public List<FactureTaxeBean> findFactureTaxesFacture(ClientContext ctx, String factureCode) {
		return dao.findAllByFactureCode(factureCode)
				.stream().map(AchatVenteMapper::map)
				.collect(Collectors.toList());
	}
	
	private FactureTaxeDto findFactureTaxeDto(String factureCode, String taxeCode) {
		return dao.findOneByFactureCodeAndTaxeTaxeCode(factureCode, taxeCode)
				.orElseThrow();
	}
	
	private FactureTaxeDto findFactureTaxeDto(FactureBean facture, FactureTaxeBean bean) {
		var factureCode = facture.getFactureCode().getValue();
		var taxeCode = bean.getTaxe().getTaxeCode().getValue();
		return findFactureTaxeDto(factureCode, taxeCode);
	}
}
