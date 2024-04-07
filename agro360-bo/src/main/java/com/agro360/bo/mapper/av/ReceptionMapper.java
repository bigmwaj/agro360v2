package com.agro360.bo.mapper.av;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.ReceptionBean;
import com.agro360.bo.mapper.common.AbstractMapper;
import com.agro360.dto.av.LigneDto;
import com.agro360.dto.av.ReceptionDto;

@Component(value = "av/ReceptionMapper")
public class ReceptionMapper extends AbstractMapper {

	
	public ReceptionBean mapToBean(ReceptionDto dto) {
		return mapToBean(dto, Collections.emptyMap());
	}

	public ReceptionBean mapToBean(LigneDto dto, Double quantite) {
		var bean = new ReceptionBean();
		bean.getDescription().setValue(dto.getDescription());
		bean.getPrixUnitaire().setValue(dto.getPrixUnitaire());
		bean.getQuantite().setValue(quantite);
		bean.getDateReception().setValue(LocalDateTime.now());
		return bean;
	}

	public ReceptionBean mapToBean(LigneDto dto) {
		return mapToBean(dto, dto.getQuantite());
	}

	public ReceptionBean mapToBean(ReceptionDto dto, Map<String, Object> options) {
		var bean = new ReceptionBean();

		bean.getDescription().setValue(dto.getDescription());
		bean.getQuantite().setValue(dto.getQuantite());
		bean.getDateReception().setValue(dto.getDateReception());
		bean.getReceptionId().setValue(dto.getReceptionId());
		bean.getStatus().setValue(dto.getStatus());

		return bean;
	}
}
