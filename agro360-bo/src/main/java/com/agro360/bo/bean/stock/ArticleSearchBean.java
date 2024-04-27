package com.agro360.bo.bean.stock;

import com.agro360.bo.bean.common.AbstractSearchBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.stock.ArticleTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ArticleSearchBean extends AbstractSearchBean{

	private static final long serialVersionUID = 355487810396906725L;

	private FieldMetadata<String> articleCode = new FieldMetadata<>("Code");

	private FieldMetadata<ArticleTypeEnumVd> type = new FieldMetadata<>("Type", ArticleTypeEnumVd.getAsMap());
	
	private FieldMetadata<String> uniteBtn = new FieldMetadata<>();

}
