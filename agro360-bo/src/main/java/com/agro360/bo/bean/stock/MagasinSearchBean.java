package com.agro360.bo.bean.stock;

import com.agro360.bo.bean.common.AbstractSearchBean;
import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class MagasinSearchBean extends AbstractSearchBean{

	public MagasinSearchBean(Short pageSize) {
		super(pageSize);
	}
	
	public MagasinSearchBean() {
		super();
	}

	private static final long serialVersionUID = 355487810396906725L;

	private FieldMetadata<String> magasinCode = new FieldMetadata<>("Magasin");
	
	private FieldMetadata<String> createMagasinBtn = new FieldMetadata<>();
	
	private FieldMetadata<String> uniteBtn = new FieldMetadata<>();
	
	

}
