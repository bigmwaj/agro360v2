package com.agro360.service.mapper.vente;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IMagasinDao;
import com.agro360.dao.tiers.ITiersDao;
import com.agro360.dao.vente.ICommandeDao;
import com.agro360.dao.vente.ILigneDao;
import com.agro360.dto.vente.CommandeDto;
import com.agro360.dto.vente.LigneDto;
import com.agro360.service.bean.vente.CommandeBean;
import com.agro360.service.bean.vente.CommandeSearchBean;
import com.agro360.service.mapper.common.AbstractMapper;
import com.agro360.service.mapper.stock.MagasinMapper;
import com.agro360.service.mapper.stock.StockSharedMapperHelper;
import com.agro360.service.mapper.tiers.TiersMapper;
import com.agro360.service.mapper.tiers.TiersSharedMapperHelper;
import com.agro360.vd.vente.StatusCommandeEnumVd;

@Component
public class CommandeMapper extends AbstractMapper {

	public final static String OPTION_MAP_LIGNE_KEY = "MAP_LIGNE";

	public final static String OPTION_MAP_PLUS_KEY = "MAP_PLUS";

	@Autowired
	private ICommandeDao dao;

	@Autowired
	private ITiersDao tiersDao;
	
	@Autowired
	private IArticleDao articleDao;

	@Autowired
	private TiersMapper tiersMapper;

	@Autowired
	private MagasinMapper magasinMapper;

	@Autowired
	private IMagasinDao magasinDao;

	@Autowired
	private ILigneDao ligneDao;

	@Autowired
	private LigneMapper ligneMapper;
	
	public CommandeSearchBean mapToSearchBean() {
		var bean = new CommandeSearchBean();
		setMap(bean.getStatusIn()::setValueOptions, StatusCommandeEnumVd.values(), StatusCommandeEnumVd::getLibelle);
		return bean;
	}

	public CommandeBean mapToBean(CommandeDto dto) {
		return mapToBean(dto, Collections.emptyMap());
	}

	public CommandeBean mapToBean(CommandeDto dto, Map<String, Object> options) {
		var bean = new CommandeBean();
		bean.getCommandeCode().setValue(dto.getCommandeCode());
		bean.getDateCommande().setValue(dto.getDateCommande());
		bean.getTransportRequis().setValue(dto.getTransportRequis());
		bean.getLivree().setValue(dto.getLivree());
		bean.getDescription().setValue(dto.getDescription());
		bean.getVille().setValue(dto.getVille());
		bean.getAdresse().setValue(dto.getAdresse());
		
		setMap(bean.getStatus()::setValueOptions, StatusCommandeEnumVd.values(),StatusCommandeEnumVd::getLibelle);
		bean.getStatus().setValue(dto.getStatus());

		if (dto.getClient() != null) {
			bean.setClient(tiersMapper.mapToBean(dto.getClient()));
		}

		if (dto.getMagasin() != null) {
			bean.setMagasin(magasinMapper.mapToBean(dto.getMagasin()));
		}

		var mapLigne = options.getOrDefault(OPTION_MAP_LIGNE_KEY, null);
		var bonCommandeCode = dto.getCommandeCode();
		if (null != bonCommandeCode && mapLigne instanceof Boolean && (Boolean) mapLigne) {
			var ex = Example.of(new LigneDto());
			ex.getProbe().setCommande(new CommandeDto());
			ex.getProbe().getCommande().setCommandeCode(bonCommandeCode);

			var ligneBeans = ligneDao.findAll(ex).stream().map(ligneMapper::mapToBean).toList();
			bean.getLignes().addAll(ligneBeans);
		}
		
		var mapPlus = options.getOrDefault(OPTION_MAP_PLUS_KEY, null);
		var isMapPlus = mapPlus instanceof Boolean && (Boolean) mapPlus;
		
		if( isMapPlus ) {
			bean.getPlusVendus().setValueOptions(StockSharedMapperHelper.getAllAsValueOptions(articleDao));
		}		

		return bean;
	}

	public CommandeDto mapToDto(CommandeBean bean) {
		CommandeDto dto = VenteSharedMapperHelper.mapToDto(dao, bean);

		setDtoValue(dto::setCommandeCode, bean.getCommandeCode());
		setDtoValue(dto::setDateCommande, bean.getDateCommande());
		setDtoValue(dto::setTransportRequis, bean.getTransportRequis());
		setDtoValue(dto::setLivree, bean.getLivree());
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setDescription, bean.getDescription());
		setDtoValue(dto::setVille, bean.getVille());
		setDtoValue(dto::setAdresse, bean.getAdresse());

		dto.setClient(TiersSharedMapperHelper.mapToDto(tiersDao, bean.getClient()));
		dto.setMagasin(StockSharedMapperHelper.mapToDto(magasinDao, bean.getMagasin()));

		return dto;
	}
}
