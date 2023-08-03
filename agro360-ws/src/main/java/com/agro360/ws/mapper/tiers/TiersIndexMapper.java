package com.agro360.ws.mapper.tiers;

import org.springframework.stereotype.Component;

import com.agro360.service.mapper.common.AbstractMapper;
import com.agro360.ws.bean.tiers.IndexBean;

@Component
public class TiersIndexMapper extends AbstractMapper {

	public IndexBean init() {
		IndexBean bean = new IndexBean();
		return bean;
	}
}
