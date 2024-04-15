package com.agro360.bo.mapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.CommandeSearchBean;
import com.agro360.bo.bean.av.FactureBean;
import com.agro360.bo.bean.av.FactureSearchBean;
import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.bean.av.LigneTaxeBean;
import com.agro360.bo.bean.av.ReceptionLigneBean;
import com.agro360.bo.bean.av.RetourLigneBean;
import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.utils.Constants;
import com.agro360.dto.av.CommandeDto;
import com.agro360.dto.av.FactureDto;
import com.agro360.dto.av.LigneDto;
import com.agro360.dto.av.LigneTaxeDto;
import com.agro360.dto.av.ReceptionLigneDto;
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
		bean.getDescription().setValue(dto.getDescription());
		bean.getType().setValue(dto.getType());
		bean.getPaiementComptant().setValue(dto.getPaiementComptant());

		bean.getRemiseType().setValue(dto.getRemiseType());
		bean.getRemiseTaux().setValue(dto.getRemiseTaux());
		bean.getRemiseMontant().setValue(dto.getRemiseMontant());
		bean.getRemiseRaison().setValue(dto.getRemiseRaison());
		
		bean.getPrixTotal().setValue(dto.getPrixTotal());
		bean.getPrixTotalHT().setValue(dto.getPrixTotalHT());
		bean.getPrixTotalTTC().setValue(dto.getPrixTotalTTC());
		bean.getTaxe().setValue(dto.getTaxe());
		bean.getRemise().setValue(dto.getRemise());
		
		if (null != dto.getPartner()) {
			bean.setPartner(CoreMapper.map(dto.getPartner()));
		}

		if (dto.getMagasin() != null) {
			bean.setMagasin(StockMapper.map(dto.getMagasin()));
		}
		
		if( dto.getCompte() != null ) {
			bean.setCompte(FinanceMapper.map(dto.getCompte()));
		}

		return bean;
	}

	public static FactureSearchBean buildFactureSearchBean() {
		var bean = new FactureSearchBean();
		return bean;
	}

	public static FactureBean map(FactureDto dto) {
		var bean = new FactureBean();

		bean.getFactureCode().setValue(dto.getFactureCode());		
		bean.getDate().setValue(dto.getDate());
		bean.getStatus().setValue(dto.getStatus());
		bean.getDescription().setValue(dto.getDescription());
		bean.getMontant().setValue(dto.getMontant());
		bean.getType().setValue(dto.getType());
		
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
		bean.getPrixTotalTTC().setValue(dto.getPrixTotalTTC());
		bean.getTaxe().setValue(dto.getTaxe());
		
		if( dto.getArticle() != null ) {
			bean.setArticle(StockMapper.map(dto.getArticle()));
		}			
		if( dto.getUnite() != null ) {
			bean.setUnite(StockMapper.map(dto.getUnite()));			
		}
		
		bean.getType().setValue(dto.getType());

		return bean;
	}
	
	public static ReceptionLigneBean mapToBean(ReceptionLigneDto dto) {
		return mapToBean(dto, Collections.emptyMap());
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

	public static ReceptionLigneBean mapToBean(ReceptionLigneDto dto, Map<String, Object> options) {
		var bean = new ReceptionLigneBean();

		bean.getDescription().setValue(dto.getDescription());
		bean.getQuantite().setValue(dto.getQuantite());
		bean.getDate().setValue(dto.getDate());
		bean.getReceptionId().setValue(dto.getReceptionId());
		bean.getStatus().setValue(dto.getStatus());

		return bean;
	}

	public static RetourLigneBean map(RetourLigneDto dto) {
		var bean = new RetourLigneBean();

		bean.getQuantite().setValue(dto.getQuantite());
		bean.getDescription().setValue(dto.getDescription());
		
		if( dto.getLigne() != null ) {
			bean.setLigne(map(dto.getLigne()));			
		}

		return bean;
	}
	
	public static LigneTaxeBean map(LigneTaxeDto dto) {
		var bean = new LigneTaxeBean();

		bean.getTaux().setValue(dto.getTaux());
		if( dto.getTaxe() != null ) {
			bean.setTaxe(FinanceMapper.map(dto.getTaxe()));			
		}
		return bean;
	}
}
