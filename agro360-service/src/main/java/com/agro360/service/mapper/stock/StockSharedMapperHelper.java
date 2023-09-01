package com.agro360.service.mapper.stock;

import java.util.Map;
import java.util.stream.Collectors;

import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.ICaisseDao;
import com.agro360.dao.stock.IMagasinDao;
import com.agro360.dao.stock.IUniteDao;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.CaisseDto;
import com.agro360.dto.stock.CaissePk;
import com.agro360.dto.stock.MagasinDto;
import com.agro360.dto.stock.UniteDto;
import com.agro360.dto.tiers.TiersDto;
import com.agro360.service.bean.stock.ArticleBean;
import com.agro360.service.bean.stock.CaisseBean;
import com.agro360.service.bean.stock.MagasinBean;
import com.agro360.service.bean.stock.UniteBean;

public class StockSharedMapperHelper {

	public static CaisseDto mapToDto(ICaisseDao caisseDao, CaisseBean bean) {
		var magasinCode = bean.getMagasin().getMagasinCode().getValue();
		var tiersCode = bean.getAgent().getTiersCode().getValue();
		var journee = bean.getJournee().getValue();

		CaissePk pk;
		CaisseDto caisse;
		
		if ( null != magasinCode && null != tiersCode  && null != journee 
				&& caisseDao.existsById(pk = new CaissePk(magasinCode, tiersCode, journee))) {
			caisse = caisseDao.getReferenceById(pk);
		} else {
			caisse = new CaisseDto();
			
			caisse.setMagasin(new MagasinDto());
			caisse.getMagasin().setMagasinCode(magasinCode);
			
			caisse.setAgent(new TiersDto());
			caisse.getAgent().setTiersCode(tiersCode);
			
			caisse.setJournee(journee);
		}

		return caisse;
	}

	public static UniteDto mapToDto(IUniteDao uniteDao, UniteBean bean) {
		var uniteCode = bean.getUniteCode().getValue();
		UniteDto unite;
		if (null != uniteCode && uniteDao.existsById(uniteCode)) {
			unite = uniteDao.getReferenceById(uniteCode);
		} else {
			unite = new UniteDto();
			unite.setUniteCode(uniteCode);
		}

		return unite;
	}
	
	public static Map<Object, String> getAllAsValueOptions(IUniteDao uniteDao) {
		return uniteDao.findAll().stream()
				.collect(Collectors.toMap(UniteDto::getUniteCode, StockSharedMapperHelper::getLibelle));
	}
	
	public static Map<Object, String> getAllAsValueOptions(IArticleDao articleDao) {
		return articleDao.findAll().stream()
				.collect(Collectors.toMap(ArticleDto::getArticleCode, StockSharedMapperHelper::getLibelle));
	}

	public static ArticleDto mapToDto(IArticleDao articleDao, ArticleBean bean) {
		var articleCode = bean.getArticleCode().getValue();
		ArticleDto article;
		if (null != articleCode && articleDao.existsById(articleCode)) {
			article = articleDao.getReferenceById(articleCode);
		} else {
			article = new ArticleDto();
			article.setArticleCode(articleCode);
		}

		return article;
	}

	public static MagasinDto mapToDto(IMagasinDao magasinDao, MagasinBean bean) {
		return mapToDto(magasinDao, bean, false);
	}

	public static MagasinDto mapToDto(IMagasinDao magasinDao, MagasinBean bean, boolean returnsNullIfPkIsNull) {
		var magasinCode = bean.getMagasinCode().getValue();
		if (magasinCode == null && returnsNullIfPkIsNull) {
			return null;
		}

		MagasinDto magasin;
		if (null != magasinCode && magasinDao.existsById(magasinCode)) {
			magasin = magasinDao.getReferenceById(magasinCode);
		} else {
			magasin = new MagasinDto();
			magasin.setMagasinCode(magasinCode);
		}

		return magasin;
	}

	
	private static String getLibelle(UniteDto dto) {
		return String.format("%s - %s", dto.getUniteCode(), dto.getDescription());
	}
	
	private static String getLibelle(ArticleDto dto) {
		return String.format("%s - %s", dto.getArticleCode(), dto.getDescription());
	}
}
