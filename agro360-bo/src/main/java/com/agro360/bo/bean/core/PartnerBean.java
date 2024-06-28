package com.agro360.bo.bean.core;

import org.apache.logging.log4j.util.Strings;

import com.agro360.bo.bean.common.AbstractStatusTrackingBean;
import com.agro360.bo.bean.common.IAssignmentBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.dto.core.PartnerDto;
import com.agro360.vd.core.PartnerStatusEnumVd;
import com.agro360.vd.core.PartnerTitleEnumVd;
import com.agro360.vd.core.PartnerTypeEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class PartnerBean extends AbstractStatusTrackingBean<PartnerStatusEnumVd> implements IAssignmentBean{

	private static final long serialVersionUID = 4953208601044344467L;

	private FieldMetadata<String> partnerCode = new FieldMetadata<>("Partenaire");

	private FieldMetadata<PartnerTypeEnumVd> type = new FieldMetadata<>("Type", PartnerTypeEnumVd.getAsMap());

	private FieldMetadata<PartnerStatusEnumVd> status = new FieldMetadata<>("Statut", PartnerStatusEnumVd.getAsMap());

	private FieldMetadata<String> partnerName = new FieldMetadata<>("Non du partenaire");

	private FieldMetadata<String> name = new FieldMetadata<>("Nom société");

	private FieldMetadata<String> firstName = new FieldMetadata<>("Prénom");

	private FieldMetadata<String> lastName = new FieldMetadata<>("Nom");

	private FieldMetadata<String> title = new FieldMetadata<>("Titre", PartnerTitleEnumVd.getAsMap());

	private FieldMetadata<String> phone = new FieldMetadata<>("Téléphone");

	private FieldMetadata<String> email = new FieldMetadata<>("Email");

	private FieldMetadata<String> address = new FieldMetadata<>("Adresse");

	private FieldMetadata<String> city = new FieldMetadata<>("Ville");

	private FieldMetadata<String> country = new FieldMetadata<>("Pays");
	
	private FieldMetadata<String> createAccountBtn = new FieldMetadata<>();
	
	private FieldMetadata<String> updatePasswordBtn = new FieldMetadata<>();
	
	private FieldMetadata<String> updateAccountBtn = new FieldMetadata<>();
	
	private FieldMetadata<String> activateAccountBtn = new FieldMetadata<>();
	
	private FieldMetadata<String> deactivateAccountBtn = new FieldMetadata<>();

	@Setter
	private PartnerCategoryBean categoriesHierarchie = new PartnerCategoryBean();
	
	public static final String partnerBean2String(PartnerBean bean) {
		var companyName = bean.getName().getValue();
		var firstName = bean.getFirstName().getValue();
		var lastName = bean.getLastName().getValue();
		
		return partner2String(companyName, firstName, lastName);
	}
	
	public static final String partnerDto2String(PartnerDto dto) {
		var companyName = dto.getName();
		var firstName = dto.getFirstName();
		var lastName = dto.getLastName();
		
		return partner2String(companyName, firstName, lastName);
	}
	
	private static final String partner2String(String companyName, String firstName, String lastName) {
		String name = null;
		companyName = Strings.isBlank(companyName) ? null : companyName;
		firstName = Strings.isBlank(firstName) ? null : firstName;
		lastName = Strings.isBlank(lastName) ? null : lastName;
		
		if( lastName == null ) {
			name = firstName;
		}else if( firstName == null ) {
			name = lastName;				
		}else {
			name = firstName + " " + lastName;
		}
		
		if( name == null ) {
			name = companyName;				
		}else if( companyName != null ) {
			name = companyName + " / " + name;
		}
		
		return name;
	}

	@Override
	public String getAssigneeCode() {
		return partnerCode.getValue();
	}

}
