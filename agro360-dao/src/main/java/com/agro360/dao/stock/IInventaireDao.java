package com.agro360.dao.stock;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.InventaireDto;
import com.agro360.dto.stock.InventairePk;
import com.agro360.dto.stock.VariantDto;

@Repository
public interface IInventaireDao extends IDao<InventaireDto, InventairePk>{

	@Query("select a from com.agro360.dto.stock.ArticleDto a"
			+ " where a.articleCode in(select v.articleCode from com.agro360.dto.stock.VariantDto v\r\n"
			+ " where (:magasinCode, v.articleCode, v.variantCode) not in (select i.magasin.magasinCode, i.article.articleCode, i.variantCode from com.agro360.dto.stock.InventaireDto i))")
	List<ArticleDto> findNonStockedArticles(
			@Param("magasinCode") String magasinCode);
	
	@Query("select v1 from com.agro360.dto.stock.VariantDto v1 "
			+ " where v1.articleCode=:articleCode and (v1.articleCode, v1.variantCode) in("
			+ " select articleCode, variantCode from com.agro360.dto.stock.VariantDto v\r\n"
			+ " where (:magasinCode, :articleCode, v.variantCode) not in (select i.magasin.magasinCode, i.article.articleCode, i.variantCode from com.agro360.dto.stock.InventaireDto i)"
			+ ")")
	List<VariantDto> findNonStockedArticleVariants(
			@Param("magasinCode")String magasinCode, 
			@Param("articleCode")String articleCode);
}
