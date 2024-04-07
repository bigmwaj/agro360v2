package com.agro360.bo.bean.core;

import java.time.LocalDateTime;

import com.agro360.bo.bean.common.AbstractStatusTrackingBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.vd.common.EditActionEnumVd;
import com.agro360.vd.core.PartnerStatusEnumVd;
import com.agro360.vd.core.PartnerTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class PartnerBean extends AbstractStatusTrackingBean<PartnerStatusEnumVd>{

	private static final long serialVersionUID = 4953208601044344467L;
	
	private FieldMetadata<String> partnerCode = new FieldMetadata<>("Partner");

	private FieldMetadata<PartnerTypeEnumVd> partnerType = new FieldMetadata<>("Type");

	private FieldMetadata<PartnerStatusEnumVd> status = new FieldMetadata<>("Statut");

	private FieldMetadata<String> partnerName = new FieldMetadata<>("Non du Partner");

	private FieldMetadata<String> name = new FieldMetadata<>("Nom Société");

	private FieldMetadata<String> firstName = new FieldMetadata<>("Prénom");

	private FieldMetadata<String> lastName = new FieldMetadata<>("Nom");

	private FieldMetadata<String> title = new FieldMetadata<>("Titre");

	private FieldMetadata<String> phone = new FieldMetadata<>("Téléphone");

	private FieldMetadata<String> email = new FieldMetadata<>("Email");

	private FieldMetadata<String> address = new FieldMetadata<>("Adresse");

	private FieldMetadata<String> city = new FieldMetadata<>("Ville");

	private FieldMetadata<String> country = new FieldMetadata<>("Pays");

	private PartnerCategoryBean categoriesHierarchie = new PartnerCategoryBean();

	public void setCategoriesHierarchie(PartnerCategoryBean categoriesHierarchie) {
		this.categoriesHierarchie = categoriesHierarchie;
	}
	
	public void initForCreateForm() {
		setAction(EditActionEnumVd.CREATE);
		getPartnerCode().setValue(null);
		getStatus().setValue(PartnerStatusEnumVd.ACTIVE);
		getStatusDate().setValue(LocalDateTime.now());
	}
	
}
