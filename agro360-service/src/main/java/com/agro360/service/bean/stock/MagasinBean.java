package com.agro360.service.bean.stock;

import java.util.ArrayList;
import java.util.List;

import com.agro360.service.bean.common.AbstractEditFormBean;
import com.agro360.service.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class MagasinBean extends AbstractEditFormBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> magasinCode = new FieldMetadata<>();

	private FieldMetadata<String> description = new FieldMetadata<>();

	private List<CasierBean> casiers = new ArrayList<>();

	public void setCasiers(List<CasierBean> casiers) {
		this.casiers = casiers;
	}
}
