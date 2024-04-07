package com.agro360.operation.logic.common;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.agro360.bo.bean.common.AbstractLigneBean;
import com.agro360.dao.stock.IArticleDao;
import com.agro360.dto.common.AbstractLigneDto;
import com.agro360.vd.av.LigneTypeEnumVd;

public abstract class AbstractLigneOperation<T extends AbstractLigneDto> extends AbstractOperation<T, Long> {
	
	@Autowired
	private IArticleDao articleDao;

	protected void initArticle(AbstractLigneBean<T> bean, Optional<String> articleCode) {
		var articleDto = articleCode.map(articleDao::findById).flatMap(e -> e).orElse(null);
		if( articleDto != null ) {
			bean.getArticle().getArticleCode().setValue(articleDto.getArticleCode());
			bean.getUnite().getUniteCode().setValue(articleDto.getUnite().getUniteCode());
			bean.getTypeLigne().setValue(LigneTypeEnumVd.valueOf(articleDto.getType().toString()));
		}
	}
}
