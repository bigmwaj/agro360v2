package com.agro360.dao.stock;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.stock.ArticleTaxeDto;
import com.agro360.dto.stock.ArticleTaxePk;

@Repository
public interface IArticleTaxeDao extends IDao<ArticleTaxeDto, ArticleTaxePk>{

	List<ArticleTaxeDto> findAllByArticleCode(String articleCode);

}
