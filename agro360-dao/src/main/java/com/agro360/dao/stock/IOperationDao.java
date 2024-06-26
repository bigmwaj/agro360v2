package com.agro360.dao.stock;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.stock.OperationDto;

@Repository("stock/IOperationDao")
public interface IOperationDao extends IDao<OperationDto, Long>{

}
