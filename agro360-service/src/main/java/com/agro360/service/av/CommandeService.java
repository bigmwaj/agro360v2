package com.agro360.service.av;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.CommandeSearchBean;
import com.agro360.bo.bean.av.FactureBean;
import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.bean.av.ReglementCommandeBean;
import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.CommandeOperation;
import com.agro360.operation.logic.av.FactureOperation;
import com.agro360.operation.logic.av.LigneOperation;
import com.agro360.operation.logic.av.ReglementCommandeOperation;
import com.agro360.operation.logic.finance.TransactionOperation;
import com.agro360.service.common.AbstractService;
import com.agro360.vd.av.FactureStatusEnumVd;
import com.agro360.vd.av.FactureTypeEnumVd;
import com.agro360.vd.finance.TransactionStatusEnumVd;
import com.agro360.vd.finance.TransactionTypeEnumVd;

@Service
public class CommandeService extends AbstractService {

	@Autowired
	private CommandeOperation service;
	
	@Autowired
	private TransactionOperation transactionOperation;
	
	@Autowired
	private ReglementCommandeOperation reglementCommandeOperation ;
	
	@Autowired
	private FactureOperation factureOperation;
	
	@Autowired
	private LigneOperation ligneOperation;
	
	public List<CommandeBean> searchAction(ClientContext ctx, Optional<CommandeSearchBean> searchBean) {
		var q = searchBean.orElse(new CommandeSearchBean());
		return service.findCommandesByCriteria(ctx, q);
	}

	
	private void createLignesCommande(ClientContext ctx, CommandeBean bean) {
		for (LigneBean ligne : bean.getLignes()) {
			ligneOperation.createLigne(ctx, bean, ligne);
		}
	}
	
	private void syncLignesCommande(ClientContext ctx, CommandeBean bean) {
		for (LigneBean ligne : bean.getLignes()) {
			switch (ligne.getAction()) {
			case CREATE:
				ligneOperation.createLigne(ctx, bean, ligne);
				break;
				
			case UPDATE:
				ligneOperation.updateLigne(ctx, bean, ligne);
				break;
				
			case DELETE:
				ligneOperation.deleteLigne(ctx, bean, ligne);
				break;

			default:
				break;
			}
		}
	}
	
	private void deleteLignesCommande(ClientContext ctx, CommandeBean bean) {
		for (LigneBean ligne : bean.getLignes()) {
			ligneOperation.deleteLigne(ctx, bean, ligne);
		}
	}
	
	public void saveAction(ClientContext ctx, CommandeBean bean) {
		
		switch (bean.getAction()) {
		case CREATE:
			service.createCommande(ctx, bean);
			createLignesCommande(ctx, bean);
			break;
			
		case UPDATE:
			service.updateCommande(ctx, bean);
			syncLignesCommande(ctx, bean);
			break;

		case CHANGE_STATUS:
			switch (bean.getStatus().getValue()) {
			case ANNL:
				service.annulerCommande(ctx, bean);				
				break;
				
			case APPR:
				var val = bean.getPaiementComptant().getValue();
				if( !Objects.isNull(val) && !BigDecimal.ZERO.equals(val) ) {
					reglerCommandeComptant(ctx, bean);
				}
				
				if( val == null ) {
					val = BigDecimal.ZERO;
				}
				
				var total = new BigDecimal(600000);
				var reste = total.subtract(val);
				if( reste.compareTo(BigDecimal.ZERO) > 0 ) {
					reglerCommandeACredit(ctx, bean, reste);
				}
				
				service.approuverCommande(ctx, bean);
				break;
				
			case ATAP:
				service.demanderApprobationCommande(ctx, bean);
				break;
				
			case RECP:
				service.receptionnerCommande(ctx, bean);
				break;
				
			case REJT:
				service.rejeterCommande(ctx, bean);
				break;
				
			case RECT:
				service.receptionnerCommande(ctx, bean);
				break;

			default:
				break;
			}
			break;

		case DELETE:
			deleteLignesCommande(ctx, bean);
			service.deleteCommande(ctx, bean);
			break;
			
		default:
			
		}
	}

	private void reglerCommandeComptant(ClientContext clientContext, CommandeBean bean) {
		var tx = new TransactionBean();
		
		var rubriqueCode = "VENTE";
		tx.getRubrique().getRubriqueCode().setValue(rubriqueCode);
		
		tx.setCompte(bean.getCompte());
		tx.setPartner(bean.getPartner());
		
		tx.getTransactionCode().setValue(transactionOperation.generateTransactionCode());
		tx.getStatus().setValue(TransactionStatusEnumVd.APPROUVEE);
		tx.getType().setValue(TransactionTypeEnumVd.RECETTE);
		tx.getNote().setValue(bean.getDescription().getValue());
		tx.getDate().setValue(bean.getDate().getValue());
		tx.getMontant().setValue(bean.getPaiementComptant().getValue());
		
		transactionOperation.createTransaction(clientContext, tx);
		
		var rg = new ReglementCommandeBean();
		rg.setTransaction(tx);
		rg.getCommandeCode().setValue(bean.getCommandeCode().getValue());
		rg.getMontant().setValue(bean.getPaiementComptant().getValue());
		
		reglementCommandeOperation.createCommande(clientContext, rg);
	
	}

	private void reglerCommandeACredit(ClientContext clientContext, CommandeBean bean, BigDecimal reste) {
		var facture = new FactureBean();
		facture.getFactureCode().setValue(factureOperation.generateFactureCode());
		facture.setCommande(bean);
		facture.setPartner(bean.getPartner());
		facture.getStatus().setValue(FactureStatusEnumVd.APPR);
		facture.getStatusDate().setValue(LocalDateTime.now());
		facture.getDate().setValue(bean.getDate().getValue());
		facture.getType().setValue(FactureTypeEnumVd.VENTE);
		facture.getMontant().setValue(reste);
		facture.getDescription().setValue(bean.getDescription().getValue());

		factureOperation.createFacture(clientContext, facture);
	}
}
