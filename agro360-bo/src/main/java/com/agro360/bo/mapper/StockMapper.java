package com.agro360.bo.mapper;

import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.ArticleSearchBean;
import com.agro360.bo.bean.stock.ArticleTaxeBean;
import com.agro360.bo.bean.stock.CasierBean;
import com.agro360.bo.bean.stock.ConversionBean;
import com.agro360.bo.bean.stock.InventaireBean;
import com.agro360.bo.bean.stock.InventaireSearchBean;
import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.bean.stock.MagasinSearchBean;
import com.agro360.bo.bean.stock.OperationBean;
import com.agro360.bo.bean.stock.OperationSearchBean;
import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.bean.stock.UniteSearchBean;
import com.agro360.bo.bean.stock.VariantBean;
import com.agro360.dto.finance.TaxeDto;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.ArticleTaxeDto;
import com.agro360.dto.stock.CasierDto;
import com.agro360.dto.stock.ConversionDto;
import com.agro360.dto.stock.InventaireDto;
import com.agro360.dto.stock.MagasinDto;
import com.agro360.dto.stock.OperationDto;
import com.agro360.dto.stock.UniteDto;
import com.agro360.dto.stock.VariantDto;

public class StockMapper {

	public static ArticleSearchBean buildArticleSearchBean() {
		var bean = new ArticleSearchBean();
		return bean;
	}

	public static ArticleBean map(ArticleDto dto) {
		var bean = new ArticleBean();
		bean.getArticleCode().setValue(dto.getArticleCode());
		bean.getDescription().setValue(dto.getDescription());
		bean.getType().setValue(dto.getType());

		var unite = dto.getUnite();
		if( unite == null ) {
			unite = new UniteDto();
		}
		bean.setUnite(map(unite));
		return bean;
	}

	public static ArticleTaxeBean map(ArticleTaxeDto dto) {
		var bean = new ArticleTaxeBean();
		var taxe = dto.getTaxe();
		if( taxe == null ) {
			taxe = new TaxeDto();
		}
		bean.setTaxe(FinanceMapper.map(taxe));
		return bean;
	}

	public static CasierBean map(CasierDto dto) {
		var bean = new CasierBean();
		bean.getCasierCode().setValue(dto.getCasierCode());
		bean.getDescription().setValue(dto.getDescription());
		return bean;
	}

	public static ConversionBean map(ConversionDto dto) {
		var bean = new ConversionBean();
		bean.getFacteur().setValue(dto.getFacteur());

		if (dto.getUnite() != null) {
			bean.setUnite(map(dto.getUnite()));
		}
		return bean;
	}

	public static MagasinSearchBean buildMagasinSearchBean() {
		var bean = new MagasinSearchBean();
		return bean;
	}

	public static MagasinBean map(MagasinDto dto) {
		var bean = new MagasinBean();

		bean.getMagasinCode().setValue(dto.getMagasinCode());
		bean.getDescription().setValue(dto.getDescription());

		return bean;
	}

	public static VariantBean map(VariantDto dto) {
		var bean = new VariantBean();
		bean.getArticleCode().setValue(dto.getArticleCode());
		bean.getVariantCode().setValue(dto.getVariantCode());
		bean.getDescription().setValue(dto.getDescription());
		bean.getAlias().setValue(dto.getAlias());
		return bean;
	}

	public static UniteSearchBean buildUniteSearchBean() {
		var bean = new UniteSearchBean();
		return bean;
	}

	public static UniteBean map(UniteDto dto) {
		var bean = new UniteBean();
		bean.getUniteCode().setValue(dto.getUniteCode());
		bean.getDescription().setValue(dto.getDescription());
		return bean;
	}

	public static InventaireSearchBean buildInventaireSearchBean() {
		var bean = new InventaireSearchBean();
		return bean;
	}

	public static InventaireBean map(InventaireDto dto) {
		var bean = new InventaireBean();
		bean.getVariantCode().setValue(dto.getVariantCode());
		bean.getQuantite().setValue(dto.getQuantite());
		bean.getPrixUnitaireAchat().setValue(dto.getPrixUnitaireAchat());
		bean.getPrixUnitaireVente().setValue(dto.getPrixUnitaireVente());

		var magasin = dto.getMagasin();
		if( magasin == null ) {
			magasin = new MagasinDto();
		}
		bean.setMagasin(map(magasin));

		var article = dto.getArticle();
		if( article == null ) {
			article = new ArticleDto();
		}
		bean.setArticle(map(article));
		return bean;
	}

	public static OperationSearchBean buildOperationSearchBean() {
		var bean = new OperationSearchBean();
		return bean;
	}

	public static OperationBean map(OperationDto dto) {
		var bean = new OperationBean();
		bean.getArticleCode().setValue(dto.getArticleCode());
		bean.getType().setValue(dto.getType());
		bean.getDate().setValue(dto.getDate());
		bean.getQuantite().setValue(dto.getQuantite());
		bean.getInventaireAvant().setValue(dto.getInventaireAvant());
		bean.getPrixUnitaire().setValue(dto.getPrixUnitaire());
		return bean;
	}
}
