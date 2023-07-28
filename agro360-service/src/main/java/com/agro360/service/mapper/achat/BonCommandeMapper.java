package com.agro360.service.mapper.achat;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.agro360.dao.achat.IBonCommandeDao;
import com.agro360.dao.achat.ILigneDao;
import com.agro360.dao.tiers.ITiersDao;
import com.agro360.dto.achat.BonCommandeDto;
import com.agro360.dto.achat.LigneDto;
import com.agro360.service.bean.achat.BonCommandeBean;
import com.agro360.service.mapper.common.AbstractMapper;
import com.agro360.service.mapper.tiers.TiersMapper;
import com.agro360.service.mapper.tiers.TiersSharedMapperHelper;

@Component
public class BonCommandeMapper extends AbstractMapper {

	public final static String OPTION_MAP_LIGNE_KEY = "MAP_LIGNE";

	@Autowired
	IBonCommandeDao dao;

	@Autowired
	TiersMapper tiersMapper;

	@Autowired
	ITiersDao tiersDao;

	@Autowired
	ILigneDao ligneDao;

	@Autowired
	LigneMapper ligneMapper;

	public BonCommandeBean mapToBean(BonCommandeDto dto) {
		return mapToBean(dto, Collections.emptyMap());
	}

	public BonCommandeBean mapToBean(BonCommandeDto dto, Map<String, Object> options) {
		var bean = new BonCommandeBean();

		bean.getBonCommandeCode().setValue(dto.getBonCommandeCode());
		bean.getLivraison().setValue(dto.getLivraison());
		bean.getDateBonCommande().setValue(dto.getDateBonCommande());
		bean.getStatut().setValue(dto.getStatut());
		bean.getDescription().setValue(dto.getDescription());
		bean.getVille().setValue(dto.getVille());
		bean.getAdresse().setValue(dto.getAdresse());
		if (null != dto.getFournisseur()) {
			bean.setFournisseur(tiersMapper.mapToBean(dto.getFournisseur()));
		}

		var mapLigne = options.getOrDefault(OPTION_MAP_LIGNE_KEY, null);
		var bonCommandeCode = dto.getBonCommandeCode();
		if (null != bonCommandeCode && mapLigne instanceof Boolean && (Boolean) mapLigne) {
			var ex = Example.of(new LigneDto());
			ex.getProbe().setBonCommande(new BonCommandeDto());
			ex.getProbe().getBonCommande().setBonCommandeCode(bonCommandeCode);

			var ligneBeans = ligneDao.findAll(ex).stream().map(ligneMapper::mapToBean).toList();
			bean.getLignes().addAll(ligneBeans);
		}

		return bean;
	}

	public BonCommandeDto mapToDto(BonCommandeBean bean) {
		
		BonCommandeDto dto = AchatSharedMapperHelper.mapToDto(dao, bean);

		setDtoValue(dto::setBonCommandeCode, bean.getBonCommandeCode());
		setDtoValue(dto::setLivraison, bean.getLivraison());
		setDtoValue(dto::setDateBonCommande, bean.getDateBonCommande());
		setDtoValue(dto::setStatut, bean.getStatut());
		setDtoValue(dto::setDescription, bean.getDescription());
		setDtoValue(dto::setVille, bean.getVille());
		setDtoValue(dto::setAdresse, bean.getAdresse());

		dto.setFournisseur(TiersSharedMapperHelper.mapToDto(tiersDao, bean.getFournisseur()));

		return dto;
	}
}
