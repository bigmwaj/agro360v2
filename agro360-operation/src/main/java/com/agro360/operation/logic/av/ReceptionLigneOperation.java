package com.agro360.operation.logic.av;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.av.ReceptionLigneBean;
import com.agro360.bo.mapper.AchatVenteMapper;
import com.agro360.dao.av.ILigneDao;
import com.agro360.dao.av.IReceptionLigneDao;
import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IUniteDao;
import com.agro360.dto.av.ReceptionLigneDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;

@Service
public class ReceptionLigneOperation extends AbstractOperation<ReceptionLigneDto, Long> {

	@Autowired
	private IReceptionLigneDao dao;

	@Autowired
	private ILigneDao ligneDao;
	
	@Autowired
	private IUniteDao uniteDao;

	@Override
	protected IDao<ReceptionLigneDto, Long> getDao() {
		return dao;
	}
	
	public List<ReceptionLigneBean> findCommandeReceptions(ClientContext ctx, String commandeCode) {
		return dao.findAllByCommandeCode(commandeCode).stream()
				.map(AchatVenteMapper::map).collect(Collectors.toList());
	}

	public void createReceptionLigne(ClientContext ctx, String commandeCode, ReceptionLigneBean bean) {
		var ligneId = bean.getLigne().getLigneId().getValue();
		var dto = new ReceptionLigneDto();
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
		super.save(ctx, dto);
		
	}

	public void updateReceptionLigne(ClientContext ctx, String commandeCode, ReceptionLigneBean bean) {
		var ligneId = bean.getLigne().getLigneId().getValue();
		var receptionId = bean.getReceptionId().getValue();
		var dto = dao.findOndByCommandeCodeAndLigneLigneIdAndReceptionId(commandeCode, ligneId, receptionId).orElseThrow();
		
		setDtoChangedValue(dto::setDescription, bean.getDescription());
		setDtoChangedValue(dto::setQuantite, bean.getQuantite());
		
		if( bean.getUnite().getUniteCode().isValueChanged() ) {
			var uniteCode = bean.getUnite().getUniteCode().getValue();
			var unite = uniteDao.getReferenceById(uniteCode);
			dto.setUnite(unite);
		}
		super.save(ctx, dto);
	}

	public void deleteReceptionLigne(ClientContext ctx, String commandeCode, ReceptionLigneBean bean) {
		var ligneId = bean.getLigne().getLigneId().getValue();
		var receptionId = bean.getReceptionId().getValue();
		var dto = dao.findOndByCommandeCodeAndLigneLigneIdAndReceptionId(commandeCode, ligneId, receptionId).orElseThrow();
		
		super.delete(ctx, dto);
	}

}
