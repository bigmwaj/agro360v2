package com.agro360.dao.stock;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.stock.ArticleDto;

@Repository
public interface IArticleDao extends IDao<ArticleDto, String>{

}
