package com.agro360.service.mapper.achat;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.achat.IBonCommandeDao;
import com.agro360.dao.achat.ILigneDao;
import com.agro360.dao.common.IDao;
import com.agro360.dto.achat.LigneDto;
import com.agro360.service.bean.achat.BonCommandeBean;
import com.agro360.service.bean.achat.LigneBean;
import com.agro360.service.mapper.common.AstractLigneMapper;

@Component(value = "achat/LigneMapper")
public class LigneMapper extends AstractLigneMapper<LigneDto> {

	@Autowired
	private ILigneDao dao;
	
	@Autowired
	private IBonCommandeDao bonCommandeDao;

	@Override
	protected IDao<LigneDto, Long> getDao() {
		return dao;
	}
	
	public LigneBean mapToBean(LigneDto dto) {
		return mapToBean(dto, Collections.emptyMap());
	}

	public LigneBean mapToBean(LigneDto dto, Map<String, Object> options) {
		LigneBean bean = (LigneBean) super.mapToBean(dto, new LigneBean(), options);
		return bean;
	}

	public LigneDto mapToDto(LigneBean bean) {
		var dto = super.mapToDto(bean, LigneDto::new);
		return dto;
	}

	public LigneDto mapToDto(BonCommandeBean bonCommandeBean, LigneBean bean) {
		var dto = mapToDto(bean);
		dto.setBonCommande(AchatSharedMapperHelper.mapToDto(bonCommandeDao, bonCommandeBean));
		return dto;
	}
}
