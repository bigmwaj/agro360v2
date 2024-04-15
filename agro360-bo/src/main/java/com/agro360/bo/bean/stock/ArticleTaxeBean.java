package com.agro360.bo.bean.stock;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.finance.TaxeBean;
import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class ArticleTaxeBean extends AbstractBean {

	private static final long serialVersionUID = -4263922620091472381L;

	private FieldMetadata<Boolean> selected = new FieldMetadata<>("Sélectionnée");

	@Setter
	private TaxeBean taxe = new TaxeBean();
}
