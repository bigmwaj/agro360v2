package com.agro360.service.mapper.tiers;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.tiers.ITiersDao;
import com.agro360.dto.tiers.TiersDto;
import com.agro360.service.bean.tiers.TiersBean;
import com.agro360.service.bean.tiers.TiersSearchBean;
import com.agro360.service.mapper.common.AbstractMapper;
import com.agro360.service.utils.Constants;
import com.agro360.vd.tiers.TiersStatusEnumVd;
import com.agro360.vd.tiers.TiersTypeEnumVd;

@Component
public class TiersMapper extends AbstractMapper {

	public final static String OPTION_MAP_TIERS_CATEGORY_KEY = "MAP_TIERS_CATEGORY";

	@Autowired
	private ITiersDao tiersDao;

	@Autowired
	private TiersCategoryMapper tiersCategoryMapper;

	public TiersSearchBean mapToSearchBean() {
		var bean = new TiersSearchBean();
		setMap(bean.getStatus()::setValueOptions, TiersStatusEnumVd.values(), TiersStatusEnumVd::getLibelle);
		setMap(bean.getTiersType()::setValueOptions, TiersTypeEnumVd.values(), TiersTypeEnumVd::getLibelle);
		return bean;
	}

	public TiersBean mapToBean(TiersDto dto) {
		return mapToBean(dto, Collections.emptyMap());
	}

	public TiersBean mapToBean(TiersDto dto, Map<String, Object> options) {
		TiersBean bean = new TiersBean();
		bean.getAddress().setValue(dto.getAddress());
		bean.getCity().setValue(dto.getCity());
		bean.getCountry().setValue(dto.getCountry());
		bean.getEmail().setValue(dto.getEmail());
		bean.getPhone().setValue(dto.getPhone());
		bean.getFirstName().setValue(dto.getFirstName());
		bean.getLastName().setValue(dto.getLastName());

		bean.getStatus().setValue(dto.getStatus());
		setMap(bean.getStatus()::setValueOptions, TiersStatusEnumVd.values(), TiersStatusEnumVd::getLibelle);

		bean.getName().setValue(dto.getName());
		bean.getTiersCode().setValue(dto.getTiersCode());

		bean.getTiersType().setValue(dto.getTiersType());
		setMap(bean.getTiersType()::setValueOptions, TiersTypeEnumVd.values(), TiersTypeEnumVd::getLibelle);

		bean.getTitle().setValue(dto.getTitle());

		if (TiersTypeEnumVd.COMPANY.equals(dto.getTiersType())) {
			bean.getTiersName().setValue(dto.getName());
		} else {
			bean.getTiersName().setValue(Constants.FULL_NAME_FN.apply(dto.getLastName(), dto.getFirstName()));
		}

		Object mapTiersCategory = options.getOrDefault(OPTION_MAP_TIERS_CATEGORY_KEY, null);
		if (mapTiersCategory instanceof Boolean && (Boolean) mapTiersCategory) {
			bean.setCategoriesHierarchie(tiersCategoryMapper.mapToTiersCategoryHierarchieBean(dto));
		}

		return bean;
	}

	public TiersDto mapToDto(TiersBean bean) {

		TiersDto dto = TiersSharedMapperHelper.mapToDto(tiersDao, bean);

		setDtoValue(dto::setAddress, bean.getAddress());
		setDtoValue(dto::setCity, bean.getCity());
		setDtoValue(dto::setCountry, bean.getCountry());
		setDtoValue(dto::setEmail, bean.getEmail());
		setDtoValue(dto::setPhone, bean.getPhone());
		setDtoValue(dto::setFirstName, bean.getFirstName());
		setDtoValue(dto::setLastName, bean.getLastName());
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setName, bean.getName());
		setDtoValue(dto::setTiersType, bean.getTiersType());
		setDtoValue(dto::setTitle, bean.getTitle());

		return dto;
	}
}
