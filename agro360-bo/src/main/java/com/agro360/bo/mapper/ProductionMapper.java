package com.agro360.bo.mapper;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.agro360.bo.bean.production.avicole.CycleBean;
import com.agro360.bo.bean.production.avicole.CycleSearchBean;
import com.agro360.bo.bean.production.avicole.JourneeBean;
import com.agro360.bo.bean.production.avicole.JourneeSearchBean;
import com.agro360.bo.bean.production.avicole.MetadataBean;
import com.agro360.bo.bean.production.avicole.ProductionBean;
import com.agro360.bo.bean.production.avicole.ProductionSearchBean;
import com.agro360.dto.production.avicole.CycleDto;
import com.agro360.dto.production.avicole.MetadataDto;
import com.agro360.dto.production.avicole.ProductionDto;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.VariantDto;
import com.agro360.vd.production.avicole.ProductionCategoryEnumVd;

public class ProductionMapper {

	public static CycleSearchBean buildCycleSearchBean() {
		var bean = new CycleSearchBean();
		return bean;
	}

	public static CycleBean mapToBean(CycleDto dto) {
		var cycleCode = dto.getCycleCode();
		var bean = new CycleBean();

		bean.getCycleCode().setValue(cycleCode);
		bean.getDescription().setValue(dto.getDescription());
		bean.getRacePlanifiee().setValue(dto.getRacePlanifiee());
		bean.getQuantitePlanifiee().setValue(dto.getQuantitePlanifiee());
		bean.getDatePlanifiee().setValue(dto.getDatePlanifiee());
		bean.getRaceEffective().setValue(dto.getRaceEffective());
		bean.getQuantiteEffective().setValue(dto.getQuantiteEffective());
		bean.getDateEffective().setValue(dto.getDateEffective());
		bean.getStatus().setValue(dto.getStatus());
		bean.getStatusDate().setValue(dto.getStatusDate());

		if (dto.getMagasin() != null) {
			bean.setMagasin(StockMapper.map(dto.getMagasin()));
		}
		return bean;
	}

	public static JourneeSearchBean buildJourneeSearchBean() {
		var bean = new JourneeSearchBean();
		return bean;
	}

	public static JourneeBean mapToBean(CycleDto dto, LocalDate journee) {
		var cycleCode = dto.getCycleCode();
		var bean = new JourneeBean();
		bean.getCycle().getCycleCode().setValue(cycleCode);
		bean.getJournee().setValue(journee);

		return bean;
	}

	protected List<ProductionBean> getProductions(){

//		var pouleFilter = Example.of(new ArticleDto());
//		pouleFilter.getProbe().setArticleCode("POULE");
//		var poules = articleDao.findAll(pouleFilter).stream()
//		.map(mapper::mapPouleToBean);
//
//		var copeauxFilter = Example.of(new VariantDto());
//		copeauxFilter.getProbe().setArticle(new ArticleDto());
//		copeauxFilter.getProbe().getArticle().setArticleCode("COPEAUX");
//		var copeaux = variantDao.findAll(copeauxFilter).stream()
//		.map(mapper::mapCopeauxToBean);
//
//		var oeufFilter = Example.of(new VariantDto());
//		oeufFilter.getProbe().setArticle(new ArticleDto());
//		oeufFilter.getProbe().getArticle().setArticleCode("OEUF");
//
//		var oeufs = variantDao.findAll(oeufFilter).stream()
//			.map(mapper::mapOeufToBean);
//
//		var phytoFilter = Example.of(new VariantDto());
//		phytoFilter.getProbe().setArticle(new ArticleDto());
//		phytoFilter.getProbe().getArticle().setArticleCode("PHYTO");
//
//		var phytos = variantDao.findAll(phytoFilter).stream()
//			.map(mapper::mapPhytoEtVaccinToBean);
//
//		var vaccinFilter = Example.of(new VariantDto());
//		vaccinFilter.getProbe().setArticle(new ArticleDto());
//		vaccinFilter.getProbe().getArticle().setArticleCode("VACCIN");
//
//		var vaccins = variantDao.findAll(vaccinFilter).stream()
//			.map(mapper::mapPhytoEtVaccinToBean);

//		return Stream.of(oeufs, poules, copeaux, phytos, vaccins)
//			.flatMap(e -> e)
//			.collect(Collectors.toList());

		return null;

	}

	public static MetadataBean mapToBean(MetadataDto dto) {
		var bean = new MetadataBean();
		bean.getMetadataCode().setValue(dto.getMetadataCode());
		bean.getValue().setValue(dto.getValue());
		return bean;
	}

	public static MetadataDto mapToDto(CycleDto cycle, CycleBean cycleBean, MetadataBean bean) {
//		var cycleCode = cycleBean.getCycleCode().getValue();
//		var metadataCode = bean.getMetadataCode().getValue();

		MetadataDto dto = new MetadataDto();
//		MetadataPk pk;
//
//		if (null != cycleCode && null != metadataCode && dao.existsById(pk = new MetadataPk(cycleCode, metadataCode))) {
//			dto = dao.getReferenceById(pk);
//		} else {
//			dto = new MetadataDto();
//			dto.setCycle(cycle);
//			dto.setMetadataCode(metadataCode);
//		}

		dto.setValue(bean.getValue().getValue());
		return dto;
	}

	public static ProductionBean mapToBean(ProductionDto dto) {
		return mapToBean(dto, Collections.emptyMap());
	}

	public static ProductionBean mapToBean(ProductionDto dto, Map<String, Object> options) {
		var bean = new ProductionBean();

		bean.getProductionId().setValue(dto.getProductionId());
		bean.getCommentaire().setValue(dto.getCommentaire());
		bean.getQuantite().setValue(dto.getQuantite());
		bean.getCategory().setValue(dto.getCategory());

		if (dto.getUnite() != null) {
			bean.setUnite(StockMapper.map(dto.getUnite()));
		}

		if (dto.getArticle() != null) {
			bean.setArticle(StockMapper.map(dto.getArticle()));
		}

		return bean;
	}

	public static ProductionBean mapOeufToBean(VariantDto dto) {
		return mapVariantBean(dto, ProductionCategoryEnumVd.PRODUIT, "Oeuf");
	}

	public static ProductionBean mapPouleToBean(ArticleDto dto) {
		var bean = mapToBean(dto);
		bean.getCategory().setValue(ProductionCategoryEnumVd.REBUS);
		bean.getUniteLibelle().setValue("Poule");
		return bean;
	}

	public static ProductionBean mapCopeauxToBean(VariantDto dto) {
		return mapVariantBean(dto, ProductionCategoryEnumVd.INTRANT, "Sac");
	}

	private static ProductionBean mapVariantBean(VariantDto dto, ProductionCategoryEnumVd category, String libelleUnite) {
		var bean = mapToBean(dto);
		bean.getCategory().setValue(category);
		bean.getUniteLibelle().setValue(libelleUnite);
		return bean;
	}

	public static ProductionBean mapPhytoEtVaccinToBean(VariantDto dto) {
		var bean = mapToBean(dto);
		bean.getCategory().setValue(ProductionCategoryEnumVd.INTRANT);
		bean.getUniteLibelle().setValue("Unité");
		return bean;
	}

	private static ProductionBean mapToBean(VariantDto dto) {
		var bean = new ProductionBean();

		bean.setVariant(StockMapper.map(dto));

//		bean.setUnite(StockMapper.map(dto.getArticle().getUnite()));
//		bean.setArticle(StockMapper.map(dto.getArticle()));

		return bean;
	}

	private static ProductionBean mapToBean(ArticleDto dto) {
		var bean = new ProductionBean();
		bean.setUnite(StockMapper.map(dto.getUnite()));
		bean.setArticle(StockMapper.map(dto));

		return bean;
	}

	public static ProductionSearchBean mapToSearchBean() {
		return new ProductionSearchBean();
	}

}
