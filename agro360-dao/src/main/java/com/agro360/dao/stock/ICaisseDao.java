package com.agro360.dao.stock;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.stock.CaisseDto;
import com.agro360.dto.stock.CaissePk;

@Repository
public interface ICaisseDao extends IDao<CaisseDto, CaissePk>{

}
