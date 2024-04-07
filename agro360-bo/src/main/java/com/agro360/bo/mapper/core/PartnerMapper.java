package com.agro360.bo.mapper.core;

import org.springframework.stereotype.Component;

import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.core.PartnerSearchBean;
import com.agro360.bo.mapper.common.AbstractMapper;
import com.agro360.bo.utils.Constants;
import com.agro360.dto.core.PartnerDto;
import com.agro360.vd.core.PartnerStatusEnumVd;
import com.agro360.vd.core.PartnerTypeEnumVd;

@Component
public class PartnerMapper extends AbstractMapper {

	public final static String OPTION_MAP_PARTNER_CATEGORY_KEY = "MAP_PARTNER_CATEGORY";

	public PartnerSearchBean mapToSearchBean() {
		var bean = new PartnerSearchBean();
		setMap(bean.getStatus()::setValueOptions, PartnerStatusEnumVd.values(), PartnerStatusEnumVd::getLibelle);
		setMap(bean.getPartnerType()::setValueOptions, PartnerTypeEnumVd.values(), PartnerTypeEnumVd::getLibelle);
		return bean;
	}

	public PartnerBean map(PartnerDto dto) {
		PartnerBean bean = new PartnerBean();
		bean.getPartnerCode().setValue(dto.getPartnerCode());
		
		bean.getAddress().setValue(dto.getAddress());
		bean.getCity().setValue(dto.getCity());
		bean.getCountry().setValue(dto.getCountry());
		bean.getEmail().setValue(dto.getEmail());
		bean.getPhone().setValue(dto.getPhone());
		bean.getFirstName().setValue(dto.getFirstName());
		bean.getLastName().setValue(dto.getLastName());

		bean.getStatus().setValue(dto.getStatus());
		setMap(bean.getStatus()::setValueOptions, PartnerStatusEnumVd.values(), PartnerStatusEnumVd::getLibelle);

		bean.getName().setValue(dto.getName());

		bean.getPartnerType().setValue(dto.getPartnerType());
		setMap(bean.getPartnerType()::setValueOptions, PartnerTypeEnumVd.values(), PartnerTypeEnumVd::getLibelle);

		bean.getTitle().setValue(dto.getTitle());

		if (PartnerTypeEnumVd.COMPANY.equals(dto.getPartnerType())) {
			bean.getPartnerName().setValue(dto.getName());
		} else {
			bean.getPartnerName().setValue(Constants.FULL_NAME_FN.apply(dto.getLastName(), dto.getFirstName()));
		}

		return bean;
	}
}
