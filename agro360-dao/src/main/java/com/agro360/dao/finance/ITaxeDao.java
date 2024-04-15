package com.agro360.dao.finance;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.finance.TaxeDto;

@Repository
public interface ITaxeDao extends IDao<TaxeDto, String>{

}
