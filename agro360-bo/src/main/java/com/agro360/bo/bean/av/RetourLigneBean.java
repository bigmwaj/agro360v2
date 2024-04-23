package com.agro360.bo.bean.av;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.bo.utils.TypeScriptInfos;
import com.agro360.vd.av.RetourStatusEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@EqualsAndHashCode(callSuper = false)
@TypeScriptInfos(igroreSuperClassParam = true)
public class RetourLigneBean extends AbstractBean {

private static final long serialVersionUID = -16328407145183398L;

	private FieldMetadata<Long> retourId = new FieldMetadata<>();

	private FieldMetadata<String> description = new FieldMetadata<>();

	private FieldMetadata<BigDecimal> prixUnitaire = new FieldMetadata<>();

	private FieldMetadata<Double> quantite = new FieldMetadata<>();

	private FieldMetadata<LocalDateTime> date = new FieldMetadata<>();

	private FieldMetadata<RetourStatusEnumVd> status = new FieldMetadata<>();

	@Setter
	private LigneBean ligne = new LigneBean();

	@Setter
	private UniteBean unite = new UniteBean();
}
