package com.agro360.dao.stock;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.stock.CasierDto;
import com.agro360.dto.stock.CasierPk;

@Repository
public interface ICasierDao extends IDao<CasierDto, CasierPk>{

}
