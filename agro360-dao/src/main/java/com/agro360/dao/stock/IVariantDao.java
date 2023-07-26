package com.agro360.dao.stock;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.stock.VariantDto;
import com.agro360.dto.stock.VariantPk;

@Repository
public interface IVariantDao extends IDao<VariantDto, VariantPk>{

}
