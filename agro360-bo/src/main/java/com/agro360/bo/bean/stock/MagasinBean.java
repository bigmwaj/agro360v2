package com.agro360.bo.bean.stock;

import java.util.ArrayList;
import java.util.List;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class MagasinBean extends AbstractBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<String> magasinCode = new FieldMetadata<>("Magasin");

	private FieldMetadata<String> description = new FieldMetadata<>("Description");

	private List<CasierBean> casiers = new ArrayList<>();
}
