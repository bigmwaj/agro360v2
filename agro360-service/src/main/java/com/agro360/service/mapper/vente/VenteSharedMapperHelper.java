package com.agro360.service.mapper.vente;

import com.agro360.dao.vente.ICommandeDao;
import com.agro360.dto.vente.CommandeDto;
import com.agro360.service.bean.vente.CommandeBean;

public class VenteSharedMapperHelper {

	public static CommandeDto mapToDto(ICommandeDao commandeDao, CommandeBean bean) {
		var commandeCode = bean.getCommandeCode().getValue();
		CommandeDto commande;
		if (null != commandeCode && commandeDao.existsById(commandeCode)) {
			commande = commandeDao.getReferenceById(commandeCode);
		} else {
			commande = new CommandeDto();
			commande.setCommandeCode(commandeCode);
		}
		
		return commande;
	}
	
}
