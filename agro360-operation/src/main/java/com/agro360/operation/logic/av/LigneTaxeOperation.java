package com.agro360.operation.logic.av;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.bean.av.LigneTaxeBean;
import com.agro360.bo.mapper.AchatVenteMapper;
import com.agro360.dao.av.ILigneTaxeDao;
import com.agro360.dao.common.IDao;
import com.agro360.dao.finance.ITaxeDao;
import com.agro360.dto.av.LigneTaxeDto;
import com.agro360.dto.av.LigneTaxePk;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

@Service
public class LigneTaxeOperation extends AbstractOperation<LigneTaxeDto, LigneTaxePk> {

	@Autowired
	private ILigneTaxeDao dao;
	
	@Autowired
	private ITaxeDao taxeDao;

	@Override
	protected IDao<LigneTaxeDto, LigneTaxePk> getDao() {
		return dao;
	}
	
	@RuleNamespace("av/commande/ligne/taxe/create")
	public void createLigne(ClientContext ctx, CommandeBean commande, LigneBean ligne, LigneTaxeBean bean) {
		var dto = new LigneTaxeDto();
		dto.setCommandeCode(commande.getCommandeCode().getValue());		
		dto.setLigneId(ligne.getLigneId().getValue());	
		
		var taxe = taxeDao.getReferenceById(bean.getTaxe().getTaxeCode().getValue());
		dto.setTaxe(taxe);
		dto.setTaux(taxe.getTaux());
		
		super.save(dto);
	}

	@RuleNamespace("av/commande/ligne/ligne/delete")
	public void deleteLigne(ClientContext ctx, CommandeBean commande, LigneBean ligne, LigneTaxeBean bean) {
		var dto = findLigneTaxeDto(commande, ligne, bean);
		dao.delete(dto);
	}

	public LigneTaxeBean findLigneTaxeByCode(ClientContext ctx, String commandeCode, Long ligneId, String taxeCode) {
		return AchatVenteMapper.map(findLigneTaxeDto(commandeCode, ligneId, taxeCode));
	}

	public List<LigneTaxeBean> findLigneTaxesLigne(ClientContext ctx, String commandeCode, Long ligneId) {
		return dao.findAllByCommandeCodeAndLigneId(commandeCode, ligneId)
				.stream().map(AchatVenteMapper::map)
				.collect(Collectors.toList());
	}
	
	private LigneTaxeDto findLigneTaxeDto(String commandeCode, Long ligneId, String taxeCode) {
		return dao.findOneByCommandeCodeAndLigneIdAndTaxeTaxeCode(commandeCode, ligneId, taxeCode)
				.orElseThrow();
	}
	
	private LigneTaxeDto findLigneTaxeDto(CommandeBean commande, LigneBean ligne, LigneTaxeBean bean) {
		var commandeCode = commande.getCommandeCode().getValue();
		var ligneId = ligne.getLigneId().getValue();
		var taxeCode = bean.getTaxe().getTaxeCode().getValue();
		return findLigneTaxeDto(commandeCode, ligneId, taxeCode);
	}
}
