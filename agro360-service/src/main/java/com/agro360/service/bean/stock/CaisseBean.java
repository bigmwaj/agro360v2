package com.agro360.service.bean.stock;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.agro360.service.bean.common.AbstractEditFormBean;
import com.agro360.service.bean.tiers.TiersBean;
import com.agro360.service.metadata.FieldMetadata;
import com.agro360.vd.stock.StatutCaisseEnumVd;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class CaisseBean extends AbstractEditFormBean {

	private static final long serialVersionUID = 1647090333349627006L;

	private FieldMetadata<LocalDate> journee = new FieldMetadata<>();

	private FieldMetadata<StatutCaisseEnumVd> statut = new FieldMetadata<>();

	private FieldMetadata<String> note = new FieldMetadata<>();
	
	private TiersBean agent = new TiersBean();

	private MagasinBean magasin = new MagasinBean();

	private List<OperationCaisseBean> operationsCaisse = new ArrayList<>();
	
	public void setMagasin(MagasinBean magasin) {
		Objects.requireNonNull(magasin);
		this.magasin = magasin;
	}
	
	public void setAgent(TiersBean agent) {
		Objects.requireNonNull(agent);
		this.agent = agent;
	}
}
