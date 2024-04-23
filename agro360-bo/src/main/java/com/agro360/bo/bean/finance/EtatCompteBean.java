package com.agro360.bo.bean.finance;

import java.math.BigDecimal;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class EtatCompteBean extends AbstractBean{

	private static final long serialVersionUID = 4981433034450973275L;

	private FieldMetadata<BigDecimal> solde = new FieldMetadata<>("Solde");

	private CompteBean compte = new CompteBean();

	public EtatCompteBean(CompteBean compte) {
		this.compte = compte;
	}

}
