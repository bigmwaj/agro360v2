package com.agro360.bo.bean.av;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.bo.utils.TypeScriptInfos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * Mis en oeuvre uniquement pour afficher les règlements d'une commande
 */
@Getter
@EqualsAndHashCode(callSuper = false)
@TypeScriptInfos(igroreSuperClassParam = true)
public class ReglementBean extends AbstractBean {

	private static final long serialVersionUID = 5279094964444930033L;
	
	public ReglementBean(TransactionBean transaction) {
		this.type = TypeReglement.transaction;
		this.transaction = transaction;
	}
	
	public ReglementBean(FactureBean facture) {
		this.type = TypeReglement.facture;
		this.facture = facture;
	}
	
	enum TypeReglement {transaction, facture}
	
	private TypeReglement type;

	@Setter
	private TransactionBean transaction = new TransactionBean();
	
	@Setter
	private FactureBean facture = new FactureBean();
}
