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
		dto.setCommandeCode(bean.getCommandeCode().getValue());		
		dto.setLigneId(bean.getLigneId().getValue());	
		dto.setMontant(bean.getMontant().getValue());
		
		var taxe = taxeDao.getReferenceById(bean.getTaxe().getTaxeCode().getValue());
		dto.setTaxe(taxe);
		dto.setTaux(taxe.getTaux());
		
		super.save(ctx, dto);
	}
	
	public void deleteLignes(ClientContext ctx, CommandeBean commande, LigneBean ligne) {
		var dtos = findLigneTaxeDto(commande, ligne);
		dao.deleteAll(dtos);
	}

	public List<LigneTaxeBean> findLigneTaxes(ClientContext ctx, CommandeBean commande, LigneBean ligne) {
		var commandeCode = commande.getCommandeCode().getValue();
		var ligneId = ligne.getLigneId().getValue();
		return dao.findAllByCommandeCodeAndLigneId(commandeCode, ligneId)
				.stream().map(AchatVenteMapper::map)
				.collect(Collectors.toList());
	}
	
	public List<LigneTaxeBean> findLigneTaxes(ClientContext ctx, CommandeBean commande) {
		var commandeCode = commande.getCommandeCode().getValue();
		return dao.findAllByCommandeCode(commandeCode)
				.stream().map(AchatVenteMapper::map)
				.collect(Collectors.toList());
	}
	
	private List<LigneTaxeDto> findLigneTaxeDto(String commandeCode, Long ligneId) {
		return dao.findAllByCommandeCodeAndLigneId(commandeCode, ligneId);
	}
	
	private List<LigneTaxeDto> findLigneTaxeDto(CommandeBean commande, LigneBean ligne) {
		var commandeCode = commande.getCommandeCode().getValue();
		var ligneId = ligne.getLigneId().getValue();
		return findLigneTaxeDto(commandeCode, ligneId);
	}
}
