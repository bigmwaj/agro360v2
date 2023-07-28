package com.agro360.service.mapper.achat;

import com.agro360.dao.achat.IBonCommandeDao;
import com.agro360.dao.achat.ILigneDao;
import com.agro360.dto.achat.BonCommandeDto;
import com.agro360.dto.achat.LigneDto;
import com.agro360.service.bean.achat.BonCommandeBean;
import com.agro360.service.bean.achat.LigneBean;

public class AchatSharedMapperHelper {

	public static BonCommandeDto mapToDto(IBonCommandeDao bonCommandeDao, BonCommandeBean bean) {
		var bonCommandeCode = bean.getBonCommandeCode().getValue();
		BonCommandeDto bonCommande;
		if (null != bonCommandeCode && bonCommandeDao.existsById(bonCommandeCode)) {
			bonCommande = bonCommandeDao.getById(bonCommandeCode);
		} else {
			bonCommande = new BonCommandeDto();
			bonCommande.setBonCommandeCode(bonCommandeCode);
		}

		return bonCommande;
	}

	public static LigneDto mapToDto(ILigneDao ligneDao, LigneBean bean) {
		var ligneId = bean.getLigneId().getValue();
		LigneDto ligne;
		if (null != ligneId && ligneDao.existsById(ligneId)) {
			ligne = ligneDao.getById(ligneId);
		} else {
			ligne = new LigneDto();
			ligne.setLigneId(ligneId);
		}

		return ligne;
	}

}
