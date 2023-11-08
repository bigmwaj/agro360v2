package com.agro360.service.logic.production.avicole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.production.avicole.IProductionDao;
import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IVariantDao;
import com.agro360.dto.production.avicole.CycleDto;
import com.agro360.dto.production.avicole.ProductionDto;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.VariantDto;
import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.bean.production.avicole.ProductionBean;
import com.agro360.service.bean.production.avicole.ProductionSearchBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.production.avicole.ProductionMapper;
import com.agro360.service.message.Message;
import com.agro360.vd.common.EditActionEnumVd;

@Service
public class ProductionService extends AbstractService<ProductionDto, Long> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	@Autowired
	private IProductionDao dao;
	
	@Autowired
	private IVariantDao variantDao;
	
	@Autowired
	private IArticleDao articleDao;

	@Autowired
	private ProductionMapper mapper;

	@Override
	protected IDao<ProductionDto, Long> getDao() {
		return dao;
	}
	
	@Override
	protected String getRulePath() {
		return "production/avicole/production";
	}
	
	public ProductionSearchBean initSearchFormBean() {
		var bean = mapper.mapToSearchBean();
		return applyInitRules(bean, "init-search-form");
	}
	
	public List<ProductionBean> search(ProductionSearchBean searchBean) {
		var example = Example.of(new ProductionDto());
		if( searchBean.getCycleCode().getValue() != null ) {
			example.getProbe().setCycle(new CycleDto());
			example.getProbe().getCycle().setCycleCode(searchBean.getCycleCode().getValue());
		}
		
		var pouleFilter = Example.of(new ArticleDto());
		pouleFilter.getProbe().setArticleCode("POULE");
		var poules = articleDao.findAll(pouleFilter).stream()
		.map(mapper::mapPouleToBean);

		var copeauxFilter = Example.of(new VariantDto());
		copeauxFilter.getProbe().setArticle(new ArticleDto());
		copeauxFilter.getProbe().getArticle().setArticleCode("COPEAUX");
		var copeaux = variantDao.findAll(copeauxFilter).stream()
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
			.map(this::applyInitEditRules)
			.collect(Collectors.toList());
	}
	
	public List<Message> save(List<ProductionBean> beans) {
		return beans.stream().map(this::save).flatMap(List::stream).collect(Collectors.toList());
	}
	
	private List<Message> save(ProductionBean bean) {
		if( bean.getAction() == null ) {
			return Collections.singletonList(Message.error("Aucune action sélectionnée"));
		}
		
		if(  EditActionEnumVd.SYNC.equals(bean.getAction())) {
			return Collections.emptyList();
		}
		
		var dto = mapper.mapToDto(bean);
		List<Message> messages = new ArrayList<>();

		switch (bean.getAction()) {
		case CREATE:
			save(dto);
			messages.add(Message.success(CREATE_SUCCESS));
			break;

		case UPDATE:
			save(dto);
			messages.add(Message.success(UPDATE_SUCCESS));
			break;

		case DELETE:
			delete(dto);
			messages.add(Message.success(DELETE_SUCCESS));
			break;
			
		default:
			return Collections.singletonList(Message.warn("Aucune action à effectuer"));
		}

		return messages;
	}

	public ProductionBean initCreateFormBean(Optional<Long> copyFrom) {
		var dto = copyFrom.map(dao::findById).flatMap(e -> e).orElseGet(ProductionDto::new);
		var bean = mapper.mapToBean(dto);
		bean.getProductionId().setValue(null);
		
		AbstractBean.setActionToCreate.accept(bean);
		
		return applyInitRules(bean, "init-create-form");
	}
}
