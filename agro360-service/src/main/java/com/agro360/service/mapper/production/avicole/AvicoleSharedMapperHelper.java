package com.agro360.service.mapper.production.avicole;

import com.agro360.dao.production.avicole.ICycleDao;
import com.agro360.dto.production.avicole.CycleDto;
import com.agro360.service.bean.production.avicole.CycleBean;

public class AvicoleSharedMapperHelper {

	public static CycleDto mapToDto(ICycleDao cycleDao, CycleBean bean) {
		return mapToDto(cycleDao, bean, false);
	}

	public static CycleDto mapToDto(ICycleDao cycleDao, CycleBean bean, boolean returnsNullIfPkIsNull) {
		var cycleCode = bean.getCycleCode().getValue();
		if (cycleCode == null && returnsNullIfPkIsNull) {
			return null;
		}

		CycleDto cycle;
		if (null != cycleCode && cycleDao.existsById(cycleCode)) {
			cycle = cycleDao.getReferenceById(cycleCode);
		} else {
			cycle = new CycleDto();
			cycle.setCycleCode(cycleCode);
		}

		return cycle;
	}
}
