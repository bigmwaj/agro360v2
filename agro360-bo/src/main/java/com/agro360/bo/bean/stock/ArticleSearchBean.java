package com.agro360.bo.bean.stock;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.stock.ArticleTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ArticleSearchBean extends AbstractBean{

	private static final long serialVersionUID = 355487810396906725L;

	private FieldMetadata<String> articleCode = new FieldMetadata<>("Code");
	
	private FieldMetadata<String> description = new FieldMetadata<>("Description");

	private FieldMetadata<ArticleTypeEnumVd> type = new FieldMetadata<>("Type", ArticleTypeEnumVd.getAsMap()
		);
	
}
