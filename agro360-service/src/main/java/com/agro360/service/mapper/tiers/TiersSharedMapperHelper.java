package com.agro360.service.mapper.tiers;

import com.agro360.dao.tiers.ITiersDao;
import com.agro360.dto.tiers.TiersDto;
import com.agro360.service.bean.tiers.TiersBean;

public class TiersSharedMapperHelper {

	public static TiersDto mapToDto(ITiersDao tiersDao, TiersBean bean) {
		var tiersCode = bean.getTiersCode().getValue();
		TiersDto tiers;
		if (null != tiersCode && tiersDao.existsById(tiersCode)) {
			tiers = tiersDao.getReferenceById(tiersCode);
		} else {
			tiers = new TiersDto();
			tiers.setTiersCode(tiersCode);
		}
		
		return tiers;
	}
}
