package com.agro360.service.logic.common;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.agro360.dao.stock.IArticleDao;
import com.agro360.dto.common.AbstractLigneDto;
import com.agro360.service.bean.common.AbstractLigneBean;
import com.agro360.vd.stock.TypeLigneEnumVd;

public abstract class AbstractLigneService<T extends AbstractLigneDto> extends AbstractService<T, Long> {
	
	@Autowired
	private IArticleDao articleDao;

	protected void initArticle(AbstractLigneBean<T> bean, Optional<String> articleCode) {
		var articleDto = articleCode.map(articleDao::findById).flatMap(e -> e).orElse(null);
		if( articleDto != null ) {
			bean.getArticle().getArticleCode().setValue(articleDto.getArticleCode());
			bean.getUnite().getUniteCode().setValue(articleDto.getUnite().getUniteCode());
			bean.getTypeLigne().setValue(TypeLigneEnumVd.valueOf(articleDto.getTypeArticle().toString()));
		}
	}
}
