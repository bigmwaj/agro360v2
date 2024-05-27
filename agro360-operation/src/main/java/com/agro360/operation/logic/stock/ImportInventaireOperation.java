package com.agro360.operation.logic.stock;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.ConversionBean;
import com.agro360.bo.bean.stock.InventaireBean;
import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.bean.stock.VariantBean;
import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IConversionDao;
import com.agro360.dao.stock.IInventaireDao;
import com.agro360.dao.stock.IMagasinDao;
import com.agro360.dao.stock.IUniteDao;
import com.agro360.dao.stock.IVariantDao;
import com.agro360.dto.stock.ConversionPk;
import com.agro360.dto.stock.InventairePk;
import com.agro360.dto.stock.VariantPk;
import com.agro360.operation.context.ClientContext;
import com.agro360.vd.stock.ArticleTypeEnumVd;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Service
public class ImportInventaireOperation {

	@Autowired
	private IInventaireDao dao;
	
	@Autowired
	private InventaireOperation operation;
	
	@Autowired
	private IMagasinDao magasinDao;
	
	@Autowired
	private IArticleDao articleDao;
	
	@Autowired
	private MagasinOperation magasinOperation;
	
	@Autowired
	private ArticleOperation articleOperation;
	
	@Autowired
	private IUniteDao uniteDao;
	
	@Autowired
	private UniteOperation uniteOperation;
	
	@Autowired
	private IVariantDao variantDao;
	
	@Autowired
	private VariantOperation variantOperation;
	
	@Autowired
	private IConversionDao conversionDao;
	
	@Autowired
	private ConversionOperation conversionOperation;
	
	protected Logger getLogger() {
		return LoggerFactory.getLogger(getClass());
	}
	
	@JsonIgnore
	public String getNullOrUpperCase(String value) {
		if( value != null && !value.isBlank() ) {
			return value.toUpperCase();
		}
		
		return null;
	}

	public void importInventory(ClientContext ctx, InputStream excelData) {
		try(var workbook = new XSSFWorkbook(excelData)) {
			var sheet = workbook.getSheetAt(0);
			var i = 0;
			for (var row : sheet) {
				if( i++ < 2 ) {
					continue;
				}
				
				var magasinCode = getNullOrUpperCase(row.getCell(0).toString());
				
				if( magasinCode == null || magasinCode.isEmpty() ) {
					break;
				}
				
				var magasinDesc = row.getCell(1).toString();
				
				var articleCode = getNullOrUpperCase(row.getCell(2).toString());
				var articleDesc = row.getCell(3).toString();
				var unitBaseCode = getNullOrUpperCase(row.getCell(4).toString());
				
				var variantCode = getNullOrUpperCase(row.getCell(5).toString());
				var variantDesc = row.getCell(6).toString();
				var alias = getNullOrUpperCase(row.getCell(7).toString());
				
				var ventePU = row.getCell(8).getNumericCellValue();
				var uniteVenteCode = getNullOrUpperCase(row.getCell(9).toString());
				var uniteVenteFacteur = row.getCell(10).getNumericCellValue();
				
				var uniteAchatCode = getNullOrUpperCase(row.getCell(11).toString());
				var uniteAchatFacteur = row.getCell(12).getNumericCellValue();
				
				var uniteStockCode = getNullOrUpperCase(row.getCell(13).toString());
				var uniteStockFacteur = row.getCell(14).getNumericCellValue();
				
				var unitBase = createUniteIfNotExists(ctx, unitBaseCode);

				var article = createArticleIfNotExists(ctx, articleCode, articleDesc, unitBase);
				
				var uniteVente = unitBase;
				var uniteAchat = unitBase;
				var uniteStock = unitBase;
				
				if( uniteVenteCode != null && !uniteVenteCode.isEmpty() && !unitBaseCode.equals(uniteVenteCode) ) {
					uniteVente = createUniteIfNotExists(ctx, uniteVenteCode);
					createConversionIfNotExists(ctx, article, uniteVente, uniteVenteFacteur);
				}
				
				if( uniteAchatCode != null && !uniteAchatCode.isEmpty() && !unitBaseCode.equals(uniteAchatCode) ) {
					uniteAchat = createUniteIfNotExists(ctx, uniteAchatCode);
					createConversionIfNotExists(ctx, article, uniteAchat, uniteAchatFacteur);
				}
				
				if( uniteStockCode != null && !uniteStockCode.isEmpty() && !unitBaseCode.equals(uniteStockCode) ) {
					uniteStock = createUniteIfNotExists(ctx, uniteStockCode);
					createConversionIfNotExists(ctx, article, uniteStock, uniteStockFacteur);
				}
				
				var magasin = createMagasinIfNotExists(ctx, magasinCode, magasinDesc);
				var variant = createVarianIfNotExists(ctx, article, variantCode, variantDesc, alias);
				
				var inventaire = new InventaireBean();
				inventaire.setUniteAchat(uniteAchat);
				inventaire.setUniteVente(uniteVente);
				inventaire.setUniteStock(uniteStock);
				inventaire.setMagasin(magasin);
				inventaire.setArticle(article);
				
				inventaire.getVariantCode().setValue(variant.getVariantCode().getValue());
				inventaire.getPrixUnitaireVente().setValue(BigDecimal.valueOf(ventePU));
				inventaire.getPrixUnitaireAchat().setValue(BigDecimal.ZERO);
				inventaire.getQuantite().setValue(Double.valueOf(0));
				
				createInventaireIfNotExists(ctx, inventaire);
				
			}
			ctx.info(String.format("Nombre de ligne traitées %d", i - 2));
		} catch (IOException e) {
			ctx.error(e.getMessage());
			getLogger().error(e.getLocalizedMessage(), e);
		}
	}
	
	private void createInventaireIfNotExists(ClientContext ctx, InventaireBean bean) {
		var magasinCode = bean.getMagasin().getMagasinCode().getValue();
		var articleCode = bean.getArticle().getArticleCode().getValue();
		var variantCode = bean.getVariantCode().getValue();
		if( !dao.existsById(new InventairePk(magasinCode, articleCode, variantCode))) {
			operation.createInventaire(ctx, bean);
			ctx.success(String.format("Ajout à l'inventaire Magasin = %s, Article = %s, Variant = %s ", 
					articleCode, articleCode, variantCode));
		}
	}
	
	private ConversionBean createConversionIfNotExists(ClientContext ctx, ArticleBean article, UniteBean unite, Double facteur) {
		var articleCode = article.getArticleCode().getValue();
		var uniteCode = unite.getUniteCode().getValue();
		var conversion = new ConversionBean();
		conversion.setUnite(unite);
		conversion.getFacteur().setValue(facteur);
		
		if( !conversionDao.existsById(new ConversionPk(articleCode, uniteCode))) {
			conversionOperation.createConversion(ctx, article, conversion);
			
			ctx.success(String.format("Ajout du facteur de conversion. Article = %s, Unite = %s", articleCode, uniteCode));
		}
		
		return conversion;
		
	}
	
	private VariantBean createVarianIfNotExists(ClientContext ctx, ArticleBean article, String variantCode, 
			String variantDesc, String alias) {
		
		var articleCode = article.getArticleCode().getValue();
		
		if( variantCode == null || variantCode.isEmpty() ) {
			variantCode = article.getArticleCode().getValue();
		}
		
		if( variantDesc == null || variantDesc.isEmpty() ) {
			variantDesc = article.getDescription().getValue();
		}
		
		if( alias == null || alias.isEmpty() ) {
			alias = article.getArticleCode().getValue();
		}
		
		var variant = new VariantBean();		
		variant.getVariantCode().setValue(variantCode);
		variant.getDescription().setValue(variantDesc);
		variant.getAlias().setValue(alias);
		if( !variantDao.existsById(new VariantPk(articleCode, variantCode)) ) {
			variantOperation.createVariant(ctx, article, variant);
			ctx.success(String.format("Ajout d'un nouvel variant. Article = %s, Variant = %s", articleCode, variantCode));
		}
		
		return variant;
	}
	
	private ArticleBean createArticleIfNotExists(ClientContext ctx, String articleCode, String articleDesc, UniteBean uniteBase) {
		var article = new ArticleBean();
		article.getArticleCode().setValue(articleCode);
		article.getDescription().setValue(articleDesc);
		article.setUnite(uniteBase);
		article.getType().setValue(ArticleTypeEnumVd.ARTC);
		
		if( !articleDao.existsById(articleCode) ) {
			articleOperation.createArticle(ctx, article);
			
			ctx.success(String.format("Ajout d'un nouvel article. Article = %s", articleCode));
		}
		
		return article;
	}
	
	private UniteBean createUniteIfNotExists(ClientContext ctx, String unit) {
		var unite = new UniteBean();
		unite.getUniteCode().setValue(unit);
		unite.getDescription().setValue(unit);
		
		if( ! uniteDao.existsById(unit) ) {
			uniteOperation.createUnite(ctx, unite);
			
			ctx.success(String.format("Ajout d'une nouvelle unité. Unité = %s", unit));
		}
		
		return unite;
	}
	
	private MagasinBean createMagasinIfNotExists(ClientContext ctx, String magasinCode, String magasinDesc) {
		var magasin = new MagasinBean();
		magasin.getMagasinCode().setValue(magasinCode);
		magasin.getDescription().setValue(magasinDesc);
		if( !magasinDao.existsById(magasinCode) ) {
			magasinOperation.createMagasin(ctx, magasin);
			
			ctx.success(String.format("Ajout d'un nouveau magasin. Magasin = %s", magasinCode));
		}
		
		return magasin;
	}
	
}
