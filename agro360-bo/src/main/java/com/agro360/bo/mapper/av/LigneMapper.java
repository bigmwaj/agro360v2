package com.agro360.bo.mapper.av;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.mapper.common.AbstractMapper;
import com.agro360.bo.mapper.stock.ArticleMapper;
import com.agro360.bo.mapper.stock.UniteMapper;
import com.agro360.dto.av.LigneDto;

@Component(value = "av/LigneMapper")
public class LigneMapper extends AbstractMapper {
	
	@Autowired
	private ArticleMapper articleMapper;
	
	@Autowired
	private UniteMapper uniteMapper;

	public LigneBean map(LigneDto dto) {
		var bean = new LigneBean();
		bean.getLigneId().setValue(dto.getLigneId());
		bean.getDescription().setValue(dto.getDescription());
		bean.getQuantite().setValue(dto.getQuantite());
		bean.getPrixUnitaire().setValue(dto.getPrixUnitaire());
		bean.getVariantCode().setValue(dto.getVariantCode());
		
		if( dto.getArticle() != null ) {
			bean.setArticle(articleMapper.map(dto.getArticle()));
		}			
		if( dto.getUnite() != null ) {
			bean.setUnite(uniteMapper.map(dto.getUnite()));			
		}
		
		bean.getType().setValue(dto.getType());

		return bean;
	}

}
