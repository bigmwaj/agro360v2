package com.agro360.service.mapper.production.avicole;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.production.avicole.ICycleDao;
import com.agro360.dao.production.avicole.IProductionDao;
import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IUniteDao;
import com.agro360.dto.production.avicole.ProductionDto;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.VariantDto;
import com.agro360.service.bean.production.avicole.CycleBean;
import com.agro360.service.bean.production.avicole.ProductionBean;
import com.agro360.service.bean.production.avicole.ProductionSearchBean;
import com.agro360.service.mapper.common.AbstractMapper;
import com.agro360.service.mapper.stock.ArticleMapper;
import com.agro360.service.mapper.stock.StockSharedMapperHelper;
import com.agro360.service.mapper.stock.UniteMapper;
import com.agro360.service.mapper.stock.VariantMapper;
import com.agro360.vd.production.avicole.ProductionCategoryEnumVd;

@Component(value = "production/avicole/ProductionMapper")
public class ProductionMapper extends AbstractMapper  {
	
	@Autowired
	private IProductionDao dao;
	
	@Autowired
	private UniteMapper uniteMapper;

	@Autowired
	private IUniteDao uniteDao;

	@Autowired
	private ArticleMapper articleMapper;

	@Autowired
	private VariantMapper variantMapper;

	@Autowired
	private IArticleDao articleDao;
	
	@Autowired
	private ICycleDao cycleDao;
	
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
			bean.setUnite(uniteMapper.mapToBean(dto.getUnite()));
		}

		if (dto.getArticle() != null) {
			bean.setArticle(articleMapper.mapToBean(dto.getArticle()));
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
		
		bean.setVariant(variantMapper.mapToBean(dto));

		bean.setUnite(uniteMapper.mapToBean(dto.getArticle().getUnite()));
		bean.setArticle(articleMapper.mapToBean(dto.getArticle()));
		
		return bean;
	}
	
	private ProductionBean mapToBean(ArticleDto dto) {
		var bean = new ProductionBean();		
		bean.setUnite(uniteMapper.mapToBean(dto.getUnite()));
		bean.setArticle(articleMapper.mapToBean(dto));
		
		return bean;
	}
	
	public ProductionDto mapToDto(ProductionBean bean) {
		var id = bean.getProductionId().getValue();
		ProductionDto dto = null;
		if ( null != id && dao.existsById(id)) {
			dto = dao.getReferenceById(id);
		} else {
			dto = new ProductionDto();
			dto.setProductionId(bean.getProductionId().getValue());
		}

		setDtoValue(dto::setCommentaire, bean.getCommentaire());
		setDtoValue(dto::setQuantite, bean.getQuantite());
		setDtoValue(dto::setCategory, bean.getCategory());

		dto.setUnite(StockSharedMapperHelper.mapToDto(uniteDao, bean.getUnite()));
		dto.setArticle(StockSharedMapperHelper.mapToDto(articleDao, bean.getArticle()));
		return dto;
	}

	public ProductionDto mapToDto(CycleBean cycleBean, ProductionBean bean) {
		var dto = mapToDto(bean);
		dto.setCycle(AvicoleSharedMapperHelper.mapToDto(cycleDao, cycleBean));

		return dto;
	}

	public ProductionSearchBean mapToSearchBean() {
		return new ProductionSearchBean();
	}
}
