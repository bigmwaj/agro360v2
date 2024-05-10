package com.agro360.bo.mapper;

import java.time.LocalDateTime;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.CommandeSearchBean;
import com.agro360.bo.bean.av.FactureBean;
import com.agro360.bo.bean.av.FactureSearchBean;
import com.agro360.bo.bean.av.FactureTaxeBean;
import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.bean.av.LigneTaxeBean;
import com.agro360.bo.bean.av.ReceptionLigneBean;
import com.agro360.bo.bean.av.ReglementCommandeBean;
import com.agro360.bo.bean.av.ReglementFactureBean;
import com.agro360.bo.bean.av.RetourLigneBean;
import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.utils.Constants;
import com.agro360.dto.av.CommandeDto;
import com.agro360.dto.av.FactureDto;
import com.agro360.dto.av.FactureTaxeDto;
import com.agro360.dto.av.LigneDto;
import com.agro360.dto.av.LigneTaxeDto;
import com.agro360.dto.av.ReceptionLigneDto;
import com.agro360.dto.av.ReglementCommandeDto;
import com.agro360.dto.av.ReglementFactureDto;
import com.agro360.dto.av.RetourLigneDto;

public class AchatVenteMapper {

	public static CommandeSearchBean buildCommandeSearchBean() {
		var bean = new CommandeSearchBean();
		return bean;
	}

	public static CommandeBean map(CommandeDto dto) {
		var bean = new CommandeBean();

		bean.getCommandeCode().setValue(dto.getCommandeCode());
		bean.getDate().setValue(dto.getDate());
		bean.getStatus().setValue(dto.getStatus());
		if( bean.getStatus().getValue() != null ) {
			bean.getStatus().setValueI18n(bean.getStatus().getValue().getLibelle());
		}
		bean.getType().setValue(dto.getType());
		if( bean.getType().getValue() != null ) {
			bean.getType().setValueI18n(bean.getType().getValue().getLibelle());
		}
		bean.getDescription().setValue(dto.getDescription());
		bean.getCumulPaiement().setValue(dto.getCumulPaiement());
		bean.getPrixTotal().setValue(dto.getPrixTotal());
		bean.getPrixTotalHT().setValue(dto.getPrixTotalHT());
		bean.getTaxe().setValue(dto.getTaxe());
		bean.getRemise().setValue(dto.getRemise());

		if (null != dto.getPartner()) {
			bean.setPartner(CoreMapper.map(dto.getPartner()));
		}

		if (dto.getMagasin() != null) {
			bean.setMagasin(StockMapper.map(dto.getMagasin()));
		}

		return bean;
	}
	
	public static ReglementCommandeBean map(ReglementCommandeDto dto) {
		var bean = new ReglementCommandeBean();
		
		bean.getCommandeCode().setValue(dto.getCommandeCode());
		bean.getReglementId().setValue(dto.getReglementId());
		if (dto.getTransaction() != null) {
			bean.setTransaction(FinanceMapper.map(dto.getTransaction()));
		}
		return bean;
	}
	
	public static ReglementFactureBean map(ReglementFactureDto dto) {
		var bean = new ReglementFactureBean();
		
		bean.getFactureCode().setValue(dto.getFactureCode());
		bean.getReglementId().setValue(dto.getReglementId());
		if (dto.getTransaction() != null) {
			bean.setTransaction(FinanceMapper.map(dto.getTransaction()));
		}
		return bean;
	}

	public static FactureSearchBean buildFactureSearchBean() {
		var bean = new FactureSearchBean();
		return bean;
	}

	public static FactureBean map(FactureDto dto) {
		var bean = new FactureBean();

		bean.getStatus().setValue(dto.getStatus());
		if( bean.getStatus().getValue() != null ) {
			bean.getStatus().setValueI18n(bean.getStatus().getValue().getLibelle());
		}
		bean.getType().setValue(dto.getType());
		if( bean.getType().getValue() != null ) {
			bean.getType().setValueI18n(bean.getType().getValue().getLibelle());
		}
		bean.getFactureCode().setValue(dto.getFactureCode());
		bean.getDate().setValue(dto.getDate());
		bean.getDescription().setValue(dto.getDescription());
		bean.getCumulPaiement().setValue(dto.getCumulPaiement());
		bean.getPrixTotal().setValue(dto.getPrixTotal());
		bean.getPrixTotalHT().setValue(dto.getPrixTotalHT());
		bean.getTaxe().setValue(dto.getTaxe());
		bean.getRemise().setValue(dto.getRemise());

		if (null != dto.getPartner()) {
			var partner = new PartnerBean();
			partner.getPartnerName().setValue(Constants.PARTNER_DTO2STR.apply(dto.getPartner()));
			partner.getPartnerCode().setValue(dto.getPartner().getPartnerCode());
			bean.setPartner(partner);
		}

		if (null != dto.getCommande()) {
			var commande = new CommandeBean();
			commande.getCommandeCode().setValue(dto.getCommande().getCommandeCode());
		}

		return bean;
	}

	public static LigneBean map(LigneDto dto) {
		var bean = new LigneBean();
		bean.getLigneId().setValue(dto.getLigneId());
		bean.getDescription().setValue(dto.getDescription());
		bean.getQuantite().setValue(dto.getQuantite());
		bean.getPrixUnitaire().setValue(dto.getPrixUnitaire());
		bean.getVariantCode().setValue(dto.getVariantCode	());

		bean.getRemiseType().setValue(dto.getRemiseType());
		bean.getRemiseTaux().setValue(dto.getRemiseTaux());
		bean.getRemiseMontant().setValue(dto.getRemiseMontant());
		bean.getRemiseRaison().setValue(dto.getRemiseRaison());

		bean.getPrixTotal().setValue(dto.getPrixTotal());
		bean.getPrixTotalHT().setValue(dto.getPrixTotalHT());
		bean.getTaxe().setValue(dto.getTaxe());
		bean.getRemise().setValue(dto.getRemise());

		if( dto.getArticle() != null ) {
			bean.setArticle(StockMapper.map(dto.getArticle()));
		}
		if( dto.getUnite() != null ) {
			bean.setUnite(StockMapper.map(dto.getUnite()));
		}

		bean.getType().setValue(dto.getType());

		return bean;
	}

	public static ReceptionLigneBean mapToBean(LigneDto dto, Double quantite) {
		var bean = new ReceptionLigneBean();
		bean.getDescription().setValue(dto.getDescription());
		bean.getPrixUnitaire().setValue(dto.getPrixUnitaire());
		bean.getQuantite().setValue(quantite);
		bean.getDate().setValue(LocalDateTime.now());
		return bean;
	}

	public static ReceptionLigneBean mapToBean(LigneDto dto) {
		return mapToBean(dto, dto.getQuantite());
	}

	public static ReceptionLigneBean map(ReceptionLigneDto dto) {
		var bean = new ReceptionLigneBean();
		bean.getReceptionId().setValue(dto.getReceptionId());
		bean.getDescription().setValue(dto.getDescription());
		bean.getQuantite().setValue(dto.getQuantite());
		bean.getDate().setValue(dto.getDate());
		bean.getStatus().setValue(dto.getStatus());

		if( dto.getLigne() != null ) {
			bean.setLigne(map(dto.getLigne()));
			bean.getPrixUnitaire().setValue(dto.getLigne().getPrixUnitaire());
		}

		if( dto.getUnite() != null ) {
			bean.setUnite(StockMapper.map(dto.getUnite()));
		}

		return bean;
	}

	public static RetourLigneBean map(RetourLigneDto dto) {
		var bean = new RetourLigneBean();
		bean.getRetourId().setValue(dto.getRetourId());
		bean.getQuantite().setValue(dto.getQuantite());
		bean.getDescription().setValue(dto.getDescription());
		bean.getStatus().setValue(dto.getStatus());
		bean.getDate().setValue(dto.getDate());

		if( dto.getLigne() != null ) {
			bean.setLigne(map(dto.getLigne()));
			bean.getPrixUnitaire().setValue(dto.getLigne().getPrixUnitaire());
		}

		if( dto.getUnite() != null ) {
			bean.setUnite(StockMapper.map(dto.getUnite()));
		}

		return bean;
	}

	public static LigneTaxeBean map(LigneTaxeDto dto) {
		var bean = new LigneTaxeBean();

		bean.getTaux().setValue(dto.getTaux());
		bean.getMontant().setValue(dto.getMontant());
		if( dto.getTaxe() != null ) {
			bean.setTaxe(FinanceMapper.map(dto.getTaxe()));
		}
		return bean;
	}
	
	public static FactureTaxeBean map(FactureTaxeDto dto) {
		var bean = new FactureTaxeBean();
		
		bean.getTaux().setValue(dto.getTaux());
		bean.getMontant().setValue(dto.getMontant());
		if( dto.getTaxe() != null ) {
			bean.setTaxe(FinanceMapper.map(dto.getTaxe()));
		}
		return bean;
	}
}
