package com.agro360.bo.mapper.production.avicole;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.production.avicole.ProductionBean;
import com.agro360.bo.bean.production.avicole.ProductionSearchBean;
import com.agro360.bo.mapper.common.AbstractMapper;
import com.agro360.bo.mapper.stock.ArticleMapper;
import com.agro360.bo.mapper.stock.UniteMapper;
import com.agro360.bo.mapper.stock.VariantMapper;
import com.agro360.dto.production.avicole.ProductionDto;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.VariantDto;
import com.agro360.vd.production.avicole.ProductionCategoryEnumVd;

@Component(value = "production/avicole/ProductionMapper")
public class ProductionMapper extends AbstractMapper  {
	
	@Autowired
	private UniteMapper uniteMapper;

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private VariantMapper variantMapper;

	public ProductionBean mapToBean(ProductionDto dto) {
		return mapToBean(dto, Collections.emptyMap());
	}

	public ProductionBean mapToBean(ProductionDto dto, Map<String, Object> options) {
		var bean = new ProductionBean();
		
		bean.getProductionId().setValue(dto.getProductionId());
		bean.getCommentaire().setValue(dto.getCommentaire());
		bean.getQuantite().setValue(dto.getQuantite());
		bean.getCategory().setValue(dto.getCategory());

		if (dto.getUnite() != null) {
			bean.setUnite(uniteMapper.map(dto.getUnite()));
		}

		if (dto.getArticle() != null) {
			bean.setArticle(articleMapper.map(dto.getArticle()));
		}

		return bean;
	}
	
	public ProductionBean mapOeufToBean(VariantDto dto) {
		return mapVariantBean(dto, ProductionCategoryEnumVd.PRODUIT, "Oeuf");
	}
	
	public ProductionBean mapPouleToBean(ArticleDto dto) {
		var bean = mapToBean(dto);
		bean.getCategory().setValue(ProductionCategoryEnumVd.REBUS);
		bean.getUniteLibelle().setValue("Poule");
		return bean;
	}
	
	public ProductionBean mapCopeauxToBean(VariantDto dto) {
		return mapVariantBean(dto, ProductionCategoryEnumVd.INTRANT, "Sac");
	}

	private ProductionBean mapVariantBean(VariantDto dto, ProductionCategoryEnumVd category, String libelleUnite) {
		var bean = mapToBean(dto);
		bean.getCategory().setValue(category);
		bean.getUniteLibelle().setValue(libelleUnite);
		return bean;
	}
	
	public ProductionBean mapPhytoEtVaccinToBean(VariantDto dto) {
		var bean = mapToBean(dto);
		bean.getCategory().setValue(ProductionCategoryEnumVd.INTRANT);
		bean.getUniteLibelle().setValue("Unité");
		return bean;
	}
	
	private ProductionBean mapToBean(VariantDto dto) {
		var bean = new ProductionBean();
		
		bean.setVariant(variantMapper.map(dto));

		bean.setUnite(uniteMapper.map(dto.getArticle().getUnite()));
		bean.setArticle(articleMapper.map(dto.getArticle()));
		
		return bean;
	}
	
	private ProductionBean mapToBean(ArticleDto dto) {
		var bean = new ProductionBean();		
		bean.setUnite(uniteMapper.map(dto.getUnite()));
		bean.setArticle(articleMapper.map(dto));
		
		return bean;
	}

	public ProductionSearchBean mapToSearchBean() {
		return new ProductionSearchBean();
	}
}
