package com.agro360.service.mapper.achat;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.achat.ILigneDao;
import com.agro360.dao.achat.IReceptionDao;
import com.agro360.dao.stock.IMagasinDao;
import com.agro360.dto.achat.LigneDto;
import com.agro360.dto.achat.ReceptionDto;
import com.agro360.service.bean.achat.ReceptionBean;
import com.agro360.service.mapper.common.AbstractMapper;
import com.agro360.service.mapper.stock.MagasinMapper;
import com.agro360.service.mapper.stock.StockSharedMapperHelper;

@Component
public class ReceptionMapper extends AbstractMapper {

	@Autowired
	private IReceptionDao dao;

	@Autowired
	private IMagasinDao magasinDao;

	@Autowired
	private MagasinMapper magasinMapper;

	@Autowired
	private ILigneDao ligneDao;

	@Autowired
	private LigneMapper ligneMapper;
	
	public ReceptionBean mapToBean(ReceptionDto dto) {
		return mapToBean(dto, Collections.emptyMap());
	}

	public ReceptionBean mapToBean(LigneDto dto, Double quantite) {
		var bean = new ReceptionBean();
		bean.getDescription().setValue(dto.getDescription());
		bean.getPrixUnitaire().setValue(dto.getPrixUnitaire());
		bean.getQuantite().setValue(quantite);
		bean.getDateReception().setValue(LocalDateTime.now());
		bean.getCasierCode().setValue(null);
		bean.setLigne(ligneMapper.mapToBean(dto));
		return bean;
	}

	public ReceptionBean mapToBean(LigneDto dto) {
		return mapToBean(dto, dto.getQuantite());
	}

	public ReceptionBean mapToBean(ReceptionDto dto, Map<String, Object> options) {
		var bean = new ReceptionBean();

		bean.getDescription().setValue(dto.getDescription());
		bean.getPrixUnitaire().setValue(dto.getPrixUnitaire());
		bean.getQuantite().setValue(dto.getQuantite());
		bean.getDateReception().setValue(dto.getDateReception());
		bean.getCasierCode().setValue(dto.getCasierCode());
		bean.getReceptionId().setValue(dto.getReceptionId());
		bean.getStatut().setValue(dto.getStatut());

		bean.setLigne(ligneMapper.mapToBean(dto.getLigne()));
		bean.setMagasin(magasinMapper.mapToBean(dto.getMagasin()));
		return bean;
	}

	public ReceptionDto mapToDto(ReceptionBean bean) {
		ReceptionDto dto;
		var receptionId = bean.getReceptionId().getValue();
		if (null != receptionId && dao.existsById(receptionId)) {
			dto = dao.getById(receptionId);
		} else {
			dto = new ReceptionDto();
			dto.setReceptionId(receptionId);
		}
		setDtoValue(dto::setStatut, bean.getStatut());
		setDtoValue(dto::setDescription, bean.getDescription());
		setDtoValue(dto::setPrixUnitaire, bean.getPrixUnitaire());
		setDtoValue(dto::setQuantite, bean.getQuantite());
		setDtoValue(dto::setDateReception, bean.getDateReception());
		setDtoValue(dto::setCasierCode, bean.getCasierCode());

		dto.setMagasin(StockSharedMapperHelper.mapToDto(magasinDao, bean.getMagasin()));
		dto.setLigne(AchatSharedMapperHelper.mapToDto(ligneDao, bean.getLigne()));
		return dto;
	}
}
