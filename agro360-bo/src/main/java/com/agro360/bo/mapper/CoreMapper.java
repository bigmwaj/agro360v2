package com.agro360.bo.mapper;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.core.PartnerCategoryBean;
import com.agro360.bo.bean.core.PartnerSearchBean;
import com.agro360.bo.bean.core.UserAccountBean;
import com.agro360.dto.core.CategoryDto;
import com.agro360.dto.core.PartnerCategoryDto;
import com.agro360.dto.core.PartnerDto;
import com.agro360.dto.core.UserAccountDto;
import com.agro360.vd.core.UserAccountStatusEnumVd;
import com.agro360.vd.core.UserRoleEnumVd;

public class CoreMapper {

	public static PartnerSearchBean buildPartnerSearchBean() {
		var bean = new PartnerSearchBean();
		return bean;
	}

	public static PartnerBean map(PartnerDto dto) {
		var bean = new PartnerBean();
		bean.getPartnerCode().setValue(dto.getPartnerCode());

		bean.getAddress().setValue(dto.getAddress());
		bean.getCity().setValue(dto.getCity());
		bean.getCountry().setValue(dto.getCountry());
		bean.getEmail().setValue(dto.getEmail());
		bean.getPhone().setValue(dto.getPhone());
		bean.getFirstName().setValue(dto.getFirstName());
		bean.getLastName().setValue(dto.getLastName());

		bean.getStatus().setValue(dto.getStatus());
		if (dto.getStatus() != null) {
			bean.getStatus().setValueI18n(dto.getStatus().getLibelle());
		}

		bean.getName().setValue(dto.getName());
		bean.getType().setValue(dto.getType());
		bean.getTitle().setValue(dto.getTitle());
		bean.getPartnerName().setValue(PartnerBean.partnerDto2String(dto));

		return bean;
	}

	public static Map.Entry<Object, String> asOption(PartnerBean bean) {
		var value = bean.getPartnerCode().getValue();
		var label = bean.getPartnerName().getValue();
		if (label == null) {
			label = value;
		}
		return Map.of(Object.class.cast(value), label).entrySet().stream().findFirst().get();
	}

	public static UserAccountBean map(UserAccountDto dto, PartnerDto partner) {
		var bean = new UserAccountBean();
		if (partner != null) {
			bean.setPartner(CoreMapper.map(partner));
		}
		var roles = Arrays.stream(dto.getRoles().split(",")).map(UserRoleEnumVd::valueOf).collect(Collectors.toList());
		bean.getPassword().setValue(dto.getPassword());
		bean.getStatus().setValue(dto.getStatus());
		bean.getLang().setValue(dto.getLang());
		bean.getMagasin().setValue(dto.getMagasin());
		bean.getRoles().setValue(roles);
		bean.getStatus().setValueOptions(UserAccountStatusEnumVd.getAsMap());
		bean.getPassword().setValue(dto.getPassword());

		return bean;
	}

	public static PartnerCategoryBean map(PartnerCategoryDto dto) {
		var bean = new PartnerCategoryBean();
		bean.getCategoryCode().setValue(dto.getCategory().getCategoryCode());
		bean.getDescription().setValue(dto.getCategory().getDescription());

		return bean;
	}

	public static PartnerCategoryBean map(CategoryDto dto) {
		var bean = new PartnerCategoryBean();
		bean.getCategoryCode().setValue(dto.getCategoryCode());
		bean.getDescription().setValue(dto.getDescription());

		return bean;
	}
}
