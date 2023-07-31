package com.agro360.service.mapper.vente;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IMagasinDao;
import com.agro360.dao.vente.ICommandeDao;
import com.agro360.dao.vente.ILigneDao;
import com.agro360.dto.vente.LigneDto;
import com.agro360.service.bean.vente.CommandeBean;
import com.agro360.service.bean.vente.LigneBean;
import com.agro360.service.mapper.common.AstractLigneMapper;
import com.agro360.service.mapper.stock.MagasinMapper;
import com.agro360.service.mapper.stock.StockSharedMapperHelper;

@Component(value = "vente/LigneMapper")
public class LigneMapper extends AstractLigneMapper<LigneDto> {

	@Autowired
	private ILigneDao dao;

	@Autowired
	private MagasinMapper magasinMapper;

	@Autowired
	private IMagasinDao magasinDao;

	@Autowired
	private ICommandeDao commandeDao;

	@Override
	protected IDao<LigneDto, Long> getDao() {
		return dao;
	}

	public LigneBean mapToBean(LigneDto dto) {
		return mapToBean(dto, Collections.emptyMap());
	}

	public LigneBean mapToBean(LigneDto dto, Map<String, Object> options) {
		LigneBean bean = (LigneBean) super.mapToBean(dto, new LigneBean(), options);

		bean.getNonFacturable().setValue(dto.getNonFacturable());
		bean.getNonEmballee().setValue(dto.getNonEmballee());
		bean.getNonCartonnee().setValue(dto.getNonCartonnee());
		bean.getCasierCode().setValue(dto.getCasierCode());

		if (null != dto.getMagasin()) {
			bean.setMagasin(magasinMapper.mapToBean(dto.getMagasin()));
		}

		return bean;
	}

	public LigneDto mapToDto(LigneBean bean) {
		var dto = super.mapToDto(bean, LigneDto::new);
		setDtoValue(dto::setNonFacturable, bean.getNonFacturable());
		setDtoValue(dto::setNonEmballee, bean.getNonEmballee());
		setDtoValue(dto::setNonCartonnee, bean.getNonCartonnee());
		setDtoValue(dto::setCasierCode, bean.getCasierCode());

		dto.setMagasin(StockSharedMapperHelper.mapToDto(magasinDao, bean.getMagasin(), true));

		return dto;
	}

	public LigneDto mapToDto(CommandeBean commandeBean, LigneBean bean) {
		LigneDto dto = mapToDto(bean);
		dto.setCommande(VenteSharedMapperHelper.mapToDto(commandeDao, commandeBean));
		return dto;
	}
}
