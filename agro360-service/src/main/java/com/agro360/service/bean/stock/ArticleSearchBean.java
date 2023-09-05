package com.agro360.service.bean.stock;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.stock.TypeArticleEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ArticleSearchBean extends AbstractBean{

	private static final long serialVersionUID = 355487810396906725L;

	private FieldMetadata<String> articleCode = new FieldMetadata<>("Code");

	private FieldMetadata<TypeArticleEnumVd> typeArticle = new FieldMetadata<>("Type", 
			getOptionsMap(TypeArticleEnumVd.values(), 
			TypeArticleEnumVd::getLibelle)
		);
	
}
