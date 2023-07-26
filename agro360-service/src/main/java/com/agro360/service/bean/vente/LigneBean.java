package com.agro360.service.bean.vente;

import com.agro360.service.bean.common.AbstractLigneBean;
import com.agro360.service.bean.stock.MagasinBean;
import com.agro360.service.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class LigneBean extends AbstractLigneBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private MagasinBean magasin = new MagasinBean();

	private FieldMetadata<String> casierCode = new FieldMetadata<>();

	private FieldMetadata<Boolean> nonFacturable = new FieldMetadata<>();

	private FieldMetadata<Boolean> nonEmballee = new FieldMetadata<>();

	private FieldMetadata<Boolean> nonCartonnee = new FieldMetadata<>();
}
