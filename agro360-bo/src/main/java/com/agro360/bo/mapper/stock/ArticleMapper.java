package com.agro360.bo.mapper.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.ArticleSearchBean;
import com.agro360.bo.mapper.common.AbstractMapper;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.UniteDto;

@Component
public class ArticleMapper extends AbstractMapper {

	@Autowired
	UniteMapper uniteMapper;
	
	public ArticleSearchBean mapToSearchBean() {
		var bean = new ArticleSearchBean();
		return bean;
	}

	public ArticleBean map(ArticleDto dto) {
		var bean = new ArticleBean();
		bean.getArticleCode().setValue(dto.getArticleCode());
		bean.getDescription().setValue(dto.getDescription());
		bean.getType().setValue(dto.getType());
		
		var unite = dto.getUnite();
		if( unite == null ) {
			unite = new UniteDto();
		}
		bean.setUnite(uniteMapper.map(unite));
		return bean;
	}
}
