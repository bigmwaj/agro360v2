package com.agro360.service.bean.production.avicole;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class MetadataBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;
	
	private FieldMetadata<String> metadataCode = new FieldMetadata<>("Code");
	
	private FieldMetadata<String> value = new FieldMetadata<>("Valeur");
	
	private FieldMetadata<Short> ordre = new FieldMetadata<>("Numero");
	
	private FieldMetadata<String> type = new FieldMetadata<>("Type");
	
	private FieldMetadata<String> libelle = new FieldMetadata<>("Libelle");
	
	private FieldMetadata<String> description = new FieldMetadata<>("Description");
	
}
