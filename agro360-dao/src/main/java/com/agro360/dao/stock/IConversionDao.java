package com.agro360.dao.stock;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.stock.ConversionDto;
import com.agro360.dto.stock.ConversionPk;

@Repository
public interface IConversionDao extends IDao<ConversionDto, ConversionPk>{

	List<ConversionDto> findAllByArticleCode(String articleCode);

}
