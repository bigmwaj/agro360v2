package com.agro360.service.mapper.tiers;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.agro360.dao.tiers.ITiersCategoryDao;
import com.agro360.dao.tiers.ITiersDao;
import com.agro360.dto.tiers.TiersCategoryDto;
import com.agro360.dto.tiers.TiersDto;
import com.agro360.service.bean.tiers.TiersBean;
import com.agro360.service.mapper.common.AbstractMapper;
import com.agro360.service.utils.Constants;
import com.agro360.vd.common.EditActionEnumVd;
import com.agro360.vd.tiers.TiersTypeEnumVd;

@Component
public class TiersMapper extends AbstractMapper {
	
	public final static String OPTION_CATEGORY_KEY = "CATEGORY";

	@Autowired
	private ITiersDao tiersDao;

	@Autowired
	private ITiersCategoryDao tiersCategoryDao;

	@Autowired
	private TiersCategoryHierarchieMapper tiersCategoryMapper;

	public TiersBean mapToBean(TiersDto dto) {
		return mapToBean(dto, null);
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
		bean.getName().setValue(dto.getName());
		bean.getTiersCode().setValue(dto.getTiersCode());
		bean.getTiersType().setValue(dto.getTiersType());
		bean.getTitle().setValue(dto.getTitle());

		if (TiersTypeEnumVd.COMPANY.equals(dto.getTiersType())) {
			bean.getTiersName().setValue(dto.getName());
		} else {
			bean.getTiersName().setValue(Constants.FULL_NAME_FN.apply(dto.getLastName(), dto.getFirstName()));
		}

		if (options.containsKey(OPTION_CATEGORY_KEY) 
				&& Objects.equals(Boolean.TRUE, options.get(OPTION_CATEGORY_KEY))) {
			bean.setCategoriesHierarchie(tiersCategoryMapper.mapToBean(getTiersCategories(dto)));
		}

		return bean;
	}

	private List<TiersCategoryDto> getTiersCategories(TiersDto tiers) {
		if (tiers == null || tiers.getTiersCode() == null || tiers.getTiersCode().isBlank()) {
			return Collections.emptyList();
		}

		Example<TiersCategoryDto> ex = Example.of(new TiersCategoryDto());
		ex.getProbe().setTiersCode(tiers.getTiersCode());

		return tiersCategoryDao.findAll(ex);
	}

	public TiersDto mapToDto(TiersBean bean) {
		TiersDto dto = new TiersDto();
		dto.setTiersCode(bean.getTiersCode().getValue());
		boolean shouldExist = Arrays.stream(EditActionEnumVd.values()).filter(e -> !EditActionEnumVd.CREATE.equals(e))
				.filter(e -> e.equals(bean.getAction())).findAny().isPresent();
		if (shouldExist) {
			if (tiersDao.existsById(dto.getTiersCode())) {
				dto = tiersDao.getById(dto.getTiersCode());
			} else {
				throw new RuntimeException(String.format("Le tiers de code %s doit exister", dto.getTiersCode()));
			}
		}

		dto.setAddress(bean.getAddress().getValue());
		dto.setCity(bean.getCity().getValue());
		dto.setCountry(bean.getCountry().getValue());
		dto.setEmail(bean.getEmail().getValue());
		dto.setPhone(bean.getPhone().getValue());
		dto.setFirstName(bean.getFirstName().getValue());
		dto.setLastName(bean.getLastName().getValue());
		dto.setStatus(bean.getStatus().getValue());
		dto.setName(bean.getName().getValue());
		dto.setTiersType(bean.getTiersType().getValue());
		dto.setTitle(bean.getTitle().getValue());

		return dto;
	}
}
