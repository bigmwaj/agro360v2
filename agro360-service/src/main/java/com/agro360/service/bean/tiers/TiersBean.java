package com.agro360.service.bean.tiers;

import com.agro360.service.bean.common.AbstractEditFormBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.tiers.TiersStatusEnumVd;
import com.agro360.vd.tiers.TiersTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class TiersBean extends AbstractEditFormBean{

	private static final long serialVersionUID = 4953208601044344467L;
	
	private FieldMetadata<String> tiersCode = new FieldMetadata<>();

	private FieldMetadata<TiersTypeEnumVd> tiersType = new FieldMetadata<>();

	private FieldMetadata<TiersStatusEnumVd> status = new FieldMetadata<>();

	private FieldMetadata<String> tiersName = new FieldMetadata<>();

	private FieldMetadata<String> name = new FieldMetadata<>();

	private FieldMetadata<String> firstName = new FieldMetadata<>();

	private FieldMetadata<String> lastName = new FieldMetadata<>();

	private FieldMetadata<String> title = new FieldMetadata<>();

	private FieldMetadata<String> phone = new FieldMetadata<>();

	private FieldMetadata<String> email = new FieldMetadata<>();

	private FieldMetadata<String> address = new FieldMetadata<>();

	private FieldMetadata<String> city = new FieldMetadata<>();

	private FieldMetadata<String> country = new FieldMetadata<>();

	private TiersCategoryBean categoriesHierarchie = new TiersCategoryBean();

	public void setCategoriesHierarchie(TiersCategoryBean categoriesHierarchie) {
		this.categoriesHierarchie = categoriesHierarchie;
	}
	
}
