package com.agro360.operation.helper;

import java.util.List;

import com.agro360.vd.av.CommandeStatusEnumVd;
import com.agro360.vd.av.CommandeTypeEnumVd;
import com.agro360.vd.av.FactureStatusEnumVd;
import com.agro360.vd.av.FactureTypeEnumVd;
import com.agro360.vd.finance.TransactionStatusEnumVd;
import com.agro360.vd.finance.TransactionTypeEnumVd;

public class StatusFlowHelper {

	public static List<CommandeStatusEnumVd> getTargets(CommandeTypeEnumVd type, CommandeStatusEnumVd currentStatus) {
		
		switch (currentStatus) {
		case BRLN:
			return List.of(
				CommandeStatusEnumVd.ATAP, // Transfert au gestion ou paiement complet nécessitant la validation
				CommandeStatusEnumVd.RGLM, 
				CommandeStatusEnumVd.SOLD 	// Paiement complet
			);
			
		case ATAP:
			return List.of(
				CommandeStatusEnumVd.TERM,
				CommandeStatusEnumVd.RGLM, // Juste pour accepter le paiement sans changer de statut
				CommandeStatusEnumVd.SOLD, 	// Paiement complet
				CommandeStatusEnumVd.ANNL
			);
			
		case AANN:
			return List.of(CommandeStatusEnumVd.ANNL);
			
		case RGLM:
			return List.of(
				CommandeStatusEnumVd.RGLM, // Juste pour accepter le paiement sans changer de statut
				CommandeStatusEnumVd.ATAP, // Transfert au gestionnaire pour approbation et règlement
				CommandeStatusEnumVd.AANN, // Transfert au gestionnaire pour annulation
				CommandeStatusEnumVd.SOLD  // Paiement complet
			);
			
		case TERM:
		case SOLD:
			if( CommandeTypeEnumVd.VENTE.equals(type)) {
				return List.of(CommandeStatusEnumVd.CLOT);
			}
		case RECP:
			return List.of(
				CommandeStatusEnumVd.RECT, 
				CommandeStatusEnumVd.RECP, 
				CommandeStatusEnumVd.CLOT
			);
			
		case RECT:
			return List.of(CommandeStatusEnumVd.CLOT);

		default:
			break;
		}
		
		return List.of();
	}
	
	public static List<FactureStatusEnumVd> getTargets(FactureTypeEnumVd type, FactureStatusEnumVd currentStatus)  {
		
		switch (currentStatus) {
		case BRLN: 
			return List.of(
				FactureStatusEnumVd.RGLM, 	// Paiement partiel de la commande
				FactureStatusEnumVd.ATAP, 	// Transfert au gestionnaire ou paiement complet impliquant un moyen necissitant une approbation
				FactureStatusEnumVd.SOLD  	// Paiement complet
			);
			
		case ATAP:
			return List.of(
				FactureStatusEnumVd.RGLM, 	// Juste pour authoriser le paiement sans changer de statut en cours
				FactureStatusEnumVd.TERM, 
				FactureStatusEnumVd.SOLD, 	// Paiement complet
				FactureStatusEnumVd.REJT, 	// Retour au vendeur
				FactureStatusEnumVd.ANNL  	// Tous les paiements initiés sont annulés
			);
			
		case REJT:
			return List.of(
				FactureStatusEnumVd.ATAP, 
				FactureStatusEnumVd.RGLM,  	// Juste pour authoriser le paiement sans changer de statut en cours
				FactureStatusEnumVd.AANN,
				FactureStatusEnumVd.SOLD 	// Paiement complet
			);
			
		case RGLM:
			return List.of(
				FactureStatusEnumVd.RGLM,  	// Juste pour authoriser le paiement sans changer de statut en cours
				FactureStatusEnumVd.ATAP,
				FactureStatusEnumVd.ANNL,
				FactureStatusEnumVd.TERM, 
				FactureStatusEnumVd.AANN,
				FactureStatusEnumVd.SOLD	// Paiement complet
			);
			
		case TERM:
			return List.of(
				FactureStatusEnumVd.CLOT,
				FactureStatusEnumVd.RGLM, 	// Juste pour authoriser le paiement sans changer de statut en cours
				FactureStatusEnumVd.SOLD
			);
		case SOLD:
			return List.of(
				FactureStatusEnumVd.CLOT
			);

		default:
			break;
		}
		
		return List.of();
	}
	
	public static List<TransactionStatusEnumVd> getTargets(TransactionTypeEnumVd type,
			TransactionStatusEnumVd currentStatus)  {
		
		switch (currentStatus) {
		case ENCOURS:
			return List.of(TransactionStatusEnumVd.APPROUVEE);
			
		case RESERVEE:
			return List.of(
				TransactionStatusEnumVd.APPROUVEE,
				TransactionStatusEnumVd.ANNULEE
			);
			
		case APPROUVEE:
			return List.of(TransactionStatusEnumVd.CLOTUREE);
			
		default:
			break;
		}
		
		return List.of();
	}
}