package com.agro360.service.bean.tiers;

import java.time.LocalDateTime;

import com.agro360.service.bean.common.AbstractStatusTrackingBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.common.EditActionEnumVd;
import com.agro360.vd.tiers.TiersStatusEnumVd;
import com.agro360.vd.tiers.TiersTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class TiersBean extends AbstractStatusTrackingBean<TiersStatusEnumVd>{

	private static final long serialVersionUID = 4953208601044344467L;
	
	private FieldMetadata<String> tiersCode = new FieldMetadata<>("Code");

	private FieldMetadata<TiersTypeEnumVd> tiersType = new FieldMetadata<>("Type");

	private FieldMetadata<TiersStatusEnumVd> status = new FieldMetadata<>("Statut");

	private FieldMetadata<String> tiersName = new FieldMetadata<>("Non du Tiers");

	private FieldMetadata<String> name = new FieldMetadata<>("Nom Société");

	private FieldMetadata<String> firstName = new FieldMetadata<>("Prénom");

	private FieldMetadata<String> lastName = new FieldMetadata<>("Nom");

	private FieldMetadata<String> title = new FieldMetadata<>("Titre");

	private FieldMetadata<String> phone = new FieldMetadata<>("Téléphone");

	private FieldMetadata<String> email = new FieldMetadata<>("Email");

	private FieldMetadata<String> address = new FieldMetadata<>("Adresse");

	private FieldMetadata<String> city = new FieldMetadata<>("Ville");

	private FieldMetadata<String> country = new FieldMetadata<>("Pays");

	private TiersCategoryBean categoriesHierarchie = new TiersCategoryBean();

	public void setCategoriesHierarchie(TiersCategoryBean categoriesHierarchie) {
		this.categoriesHierarchie = categoriesHierarchie;
	}
	
	public void initForCreateForm() {
		setAction(EditActionEnumVd.CREATE);
		getTiersCode().setValue(null);
		getStatus().setValue(TiersStatusEnumVd.ACTIVE);
		getStatusDate().setValue(LocalDateTime.now());
	}
	
}
