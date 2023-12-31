package com.agro360.service.mapper.stock;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.ICaisseDao;
import com.agro360.dao.stock.IMagasinDao;
import com.agro360.dao.stock.IOperationCaisseDao;
import com.agro360.dao.tiers.ITiersDao;
import com.agro360.dto.stock.CaisseDto;
import com.agro360.dto.stock.CaissePk;
import com.agro360.dto.stock.MagasinDto;
import com.agro360.dto.stock.OperationCaisseDto;
import com.agro360.dto.tiers.TiersDto;
import com.agro360.service.bean.stock.CaisseBean;
import com.agro360.service.bean.stock.CaisseIdBean;
import com.agro360.service.bean.stock.CaisseSearchBean;
import com.agro360.service.mapper.common.AbstractMapper;
import com.agro360.service.mapper.tiers.TiersMapper;
import com.agro360.service.mapper.tiers.TiersSharedMapperHelper;
import com.agro360.vd.stock.StatusCaisseEnumVd;

@Component
public class CaisseMapper extends AbstractMapper {

	public final static String OPTION_MAP_OPERATION_KEY = "MAP_OPERATION";

	public final static String OPTION_MAP_PLUS_KEY = "MAP_PLUS";

	@Autowired
	private ICaisseDao dao;

	@Autowired
	private ITiersDao tiersDao;
	
	@Autowired
	private IArticleDao articleDao;

	@Autowired
	private OperationCaisseMapper operationCaisseMapper;

	@Autowired
	private IOperationCaisseDao operationCaisseDao;

	@Autowired
	private TiersMapper tiersMapper;

	@Autowired
	private MagasinMapper magasinMapper;
	
	@Autowired
	private IMagasinDao magasinDao;
	
	public CaisseSearchBean mapToSearchBean() {
		var bean = new CaisseSearchBean();
		setMap(bean.getStatus()::setValueOptions, StatusCaisseEnumVd.values(), StatusCaisseEnumVd::getLibelle);
		return bean;
	}

	public CaisseBean mapToBean(CaisseDto dto, Map<String, Object> options) {
		var bean = new CaisseBean();

		bean.getJournee().setValue(dto.getJournee());
		bean.getNote().setValue(dto.getNote());
		bean.getStatus().setValue(dto.getStatus());

		if (dto.getAgent() != null) {
			bean.setAgent(tiersMapper.mapToBean(dto.getAgent()));
		}

		if (dto.getMagasin() != null) {
			bean.setMagasin(magasinMapper.mapToBean(dto.getMagasin()));
		}

		var mapOperation = options.getOrDefault(OPTION_MAP_OPERATION_KEY, null);
		var isMapOperation = mapOperation instanceof Boolean && (Boolean) mapOperation;

		var magasinCode = dto.getMagasin() != null ? dto.getMagasin().getMagasinCode() : null;
		var tiersCode = dto.getAgent() != null ? dto.getAgent().getTiersCode() : null;
		var journee = dto.getJournee();

		if (null != tiersCode && null != magasinCode && null != journee && isMapOperation) {
			var ex = Example.of(new OperationCaisseDto());
			ex.getProbe().setCaisse(new CaisseDto());

			ex.getProbe().getCaisse().setMagasin(new MagasinDto());
			ex.getProbe().getCaisse().getMagasin().setMagasinCode(magasinCode);

			ex.getProbe().getCaisse().setAgent(new TiersDto());
			ex.getProbe().getCaisse().getAgent().setTiersCode(dto.getAgent().getTiersCode());

			ex.getProbe().getCaisse().setJournee(journee);

			var operationCaisseBeans = operationCaisseDao.findAll(ex).stream().map(operationCaisseMapper::mapToBean)
					.toList();
			bean.getOperations().addAll(operationCaisseBeans);
			
		}
		
		var mapPlus = options.getOrDefault(OPTION_MAP_PLUS_KEY, null);
		var isMapPlus = mapPlus instanceof Boolean && (Boolean) mapPlus;
		
		if( isMapPlus ) {
			bean.getPlusAchetes().setValueOptions(StockSharedMapperHelper.getAllAsValueOptions(articleDao));
			bean.getPlusVendus().setValueOptions(StockSharedMapperHelper.getAllAsValueOptions(articleDao));
		}

		return bean;
	}

	public CaisseBean mapToBean(CaisseDto dto) {
		return mapToBean(dto, Collections.emptyMap());
	}

	public CaisseDto mapToDto(CaisseBean bean) {
		CaisseDto dto = StockSharedMapperHelper.mapToDto(dao, bean);
		dto.setMagasin(StockSharedMapperHelper.mapToDto(magasinDao, bean.getMagasin()));
		dto.setAgent(TiersSharedMapperHelper.mapToDto(tiersDao, bean.getAgent()));

		setDtoValue(dto::setNote, bean.getNote());
		setDtoValue(dto::setStatus, bean.getStatus());

		return dto;
	}
	
	public CaissePk mapToId(CaisseIdBean bean) {
		return new CaissePk(bean.getMagasin(), bean.getAgent(), bean.getJournee());
	}
	
	public CaisseDto mapToDto(CaisseIdBean bean) {
		var dto = new CaisseDto();
		dto.setJournee(bean.getJournee());
		dto.setAgent(new TiersDto());
		dto.getAgent().setTiersCode(bean.getAgent());
		
		return dto;
	}
}
