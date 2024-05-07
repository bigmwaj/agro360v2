package com.agro360.operation.logic.av;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.av.RetourLigneBean;
import com.agro360.bo.mapper.AchatVenteMapper;
import com.agro360.dao.av.ILigneDao;
import com.agro360.dao.av.IRetourLigneDao;
import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IUniteDao;
import com.agro360.dto.av.RetourLigneDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;

@Service
public class RetourLigneOperation extends AbstractOperation<RetourLigneDto, Long> {

	@Autowired
	private IRetourLigneDao dao;

	@Autowired
	private ILigneDao ligneDao;
	
	@Autowired
	private IUniteDao uniteDao;

	@Override
	protected IDao<RetourLigneDto, Long> getDao() {
		return dao;
	}
	
	public List<RetourLigneBean> findCommandeRetours(ClientContext ctx, String commandeCode) {
		return dao.findAllByCommandeCode(commandeCode).stream()
				.map(AchatVenteMapper::map).collect(Collectors.toList());
	}

	public void createRetourLigne(ClientContext ctx, String commandeCode, RetourLigneBean bean) {
		var ligneId = bean.getLigne().getLigneId().getValue();
		var dto = new RetourLigneDto();
		setDtoValue(dto::setDescription, bean.getDescription());
		setDtoValue(dto::setQuantite, bean.getQuantite());
		setDtoValue(dto::setDate, bean.getDate());
		setDtoValue(dto::setStatus, bean.getStatus());
		
		var ligne = ligneDao.findOneByCommandeCodeAndLigneId(commandeCode, ligneId).orElseThrow();
		dto.setLigne(ligne);
		
		var uniteCode = bean.getUnite().getUniteCode().getValue();
		var unite = uniteDao.getReferenceById(uniteCode);
		dto.setUnite(unite);
		
		dto.setCommandeCode(ligne.getCommandeCode());
		super.save(dto);
		
	}

	public void updateRetourLigne(ClientContext ctx, String commandeCode, RetourLigneBean bean) {
		var ligneId = bean.getLigne().getLigneId().getValue();
		var retourId = bean.getRetourId().getValue();
		var dto = dao.findOndByCommandeCodeAndLigneLigneIdAndRetourId(commandeCode, ligneId, retourId).orElseThrow();
		
		setDtoChangedValue(dto::setDescription, bean.getDescription());
		setDtoChangedValue(dto::setQuantite, bean.getQuantite());
		
		if( bean.getUnite().getUniteCode().isValueChanged() ) {
			var uniteCode = bean.getUnite().getUniteCode().getValue();
			var unite = uniteDao.getReferenceById(uniteCode);
			dto.setUnite(unite);
		}
		super.save(dto);
	}

	public void deleteRetourLigne(ClientContext ctx, String commandeCode, RetourLigneBean bean) {
		var ligneId = bean.getLigne().getLigneId().getValue();
		var retourId = bean.getRetourId().getValue();
		var dto = dao.findOndByCommandeCodeAndLigneLigneIdAndRetourId(commandeCode, ligneId, retourId).orElseThrow();
		
		super.delete(dto);
	}

}
