package com.agro360.service.mapper.achat;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.agro360.dao.achat.IBonCommandeDao;
import com.agro360.dao.achat.ILigneDao;
import com.agro360.dao.stock.IMagasinDao;
import com.agro360.dao.tiers.ITiersDao;
import com.agro360.dto.achat.BonCommandeDto;
import com.agro360.dto.achat.LigneDto;
import com.agro360.service.bean.achat.BonCommandeBean;
import com.agro360.service.bean.achat.BonCommandeSearchBean;
import com.agro360.service.mapper.common.AbstractMapper;
import com.agro360.service.mapper.stock.MagasinMapper;
import com.agro360.service.mapper.stock.StockSharedMapperHelper;
import com.agro360.service.mapper.tiers.TiersMapper;
import com.agro360.service.mapper.tiers.TiersSharedMapperHelper;
import com.agro360.service.message.Message;

@Component
public class BonCommandeMapper extends AbstractMapper {

	public final static String OPTION_MAP_LIGNE_KEY = "MAP_LIGNE";

	@Autowired
	private IBonCommandeDao dao;

	@Autowired
	private TiersMapper tiersMapper;

	@Autowired
	private ITiersDao tiersDao;

	@Autowired
	private ILigneDao ligneDao;

	@Autowired
	private LigneMapper ligneMapper;

	@Autowired
	private MagasinMapper magasinMapper;

	@Autowired
	private IMagasinDao magasinDao;
	
	public BonCommandeSearchBean mapToSearchBean() {
		var bean = new BonCommandeSearchBean();
		return bean;
	}

	public BonCommandeBean mapToBean(BonCommandeDto dto) {
		return mapToBean(dto, Collections.emptyMap());
	}

	public BonCommandeBean mapToBean(BonCommandeDto dto, Map<String, Object> options) {
		var bean = new BonCommandeBean();
		var bonCommandeCode = dto.getBonCommandeCode();

		bean.getBonCommandeCode().setValue(bonCommandeCode);
		
		// TODO Remove this
		bean.getBonCommandeCode().addMessage(Message.error("Message d'erreur"));
		bean.getBonCommandeCode().addMessage(Message.success("Message d'erreur"));
		bean.getBonCommandeCode().addMessage(Message.warn("Message d'erreur"));
		bean.getBonCommandeCode().addMessage(Message.info("Message d'erreur"));
		
		bean.getLivraison().setValue(dto.getLivraison());
		bean.getDateBonCommande().setValue(dto.getDateBonCommande());
		bean.getStatus().setValue(dto.getStatus());
		bean.getDescription().setValue(dto.getDescription());
		
		if (null != dto.getFournisseur()) {
			bean.setFournisseur(tiersMapper.mapToBean(dto.getFournisseur()));
		}

		if (dto.getMagasin() != null) {
			bean.setMagasin(magasinMapper.mapToBean(dto.getMagasin()));
		}

		var mapLigne = options.getOrDefault(OPTION_MAP_LIGNE_KEY, null);
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
		
		var dto = AchatSharedMapperHelper.mapToDto(dao, bean);

		setDtoValue(dto::setBonCommandeCode, bean.getBonCommandeCode());
		setDtoValue(dto::setLivraison, bean.getLivraison());
		setDtoValue(dto::setDateBonCommande, bean.getDateBonCommande());
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setDescription, bean.getDescription());

		dto.setFournisseur(TiersSharedMapperHelper.mapToDto(tiersDao, bean.getFournisseur()));
		dto.setMagasin(StockSharedMapperHelper.mapToDto(magasinDao, bean.getMagasin()));

		return dto;
	}
}
