package com.agro360.service.mapper.production.avicole;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;

import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IVariantDao;
import com.agro360.dto.production.avicole.CycleDto;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.VariantDto;
import com.agro360.service.bean.production.avicole.JourneeBean;
import com.agro360.service.bean.production.avicole.JourneeSearchBean;
import com.agro360.service.bean.production.avicole.ProductionBean;
import com.agro360.service.mapper.common.AbstractMapper;

@Component
public class JourneeMapper extends AbstractMapper {

	public final static String OPTION_MAP_PRODUCTION_KEY = "MAP_PRODUCTION";
	
	@Autowired
	private IVariantDao variantDao;
	
	@Autowired
	private IArticleDao articleDao;

	@Autowired
	private ProductionMapper mapper;
	
	public JourneeSearchBean mapToSearchBean() {
		var bean = new JourneeSearchBean();
		return bean;
	}

	public JourneeBean mapToBean(CycleDto dto, LocalDate journee, Map<String, Object> options) {
		var cycleCode = dto.getCycleCode();
		var bean = new JourneeBean();		
		bean.getCycle().getCycleCode().setValue(cycleCode);
		bean.getJournee().setValue(journee);
		
		var mapProductions = options.getOrDefault(OPTION_MAP_PRODUCTION_KEY, null);
		
		if (mapProductions != null) {
			var productions = getProductions();
			bean.getProductions().addAll(productions);
		}
		

		return bean;
	}
	
	private List<ProductionBean> getProductions(){
		
		var pouleFilter = Example.of(new ArticleDto());
		pouleFilter.getProbe().setArticleCode("POULE");
		var poules = articleDao.findAll(pouleFilter).stream()
		.map(mapper::mapPouleToBean);

		var copeauxFilter = Example.of(new ArticleDto());
		copeauxFilter.getProbe().setArticleCode("COPEAUX");
		var copeaux = articleDao.findAll(copeauxFilter).stream()
		.map(mapper::mapCopeauxToBean);
		
		var oeufFilter = Example.of(new VariantDto());
		oeufFilter.getProbe().setArticle(new ArticleDto());
		oeufFilter.getProbe().getArticle().setArticleCode("OEUF");
		
		var oeufs = variantDao.findAll(oeufFilter).stream()
			.map(mapper::mapOeufToBean);
		
		var phytoFilter = Example.of(new VariantDto());
		phytoFilter.getProbe().setArticle(new ArticleDto());
		phytoFilter.getProbe().getArticle().setArticleCode("PHYTO");
		
		var phytos = variantDao.findAll(phytoFilter).stream()
			.map(mapper::mapPhytoEtVaccinToBean);
		
		var vaccinFilter = Example.of(new VariantDto());
		vaccinFilter.getProbe().setArticle(new ArticleDto());
		vaccinFilter.getProbe().getArticle().setArticleCode("VACCIN");
		
		var vaccins = variantDao.findAll(vaccinFilter).stream()
			.map(mapper::mapPhytoEtVaccinToBean);
		
		return Stream.of(oeufs, poules, copeaux, phytos, vaccins)
			.flatMap(e -> e)
			.collect(Collectors.toList());

	}

}
