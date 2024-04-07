package com.agro360.operation.logic.production.avicole;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.bo.bean.production.avicole.ProductionBean;
import com.agro360.bo.bean.production.avicole.ProductionSearchBean;
import com.agro360.bo.mapper.production.avicole.ProductionMapper;
import com.agro360.bo.message.Message;
import com.agro360.dao.common.IDao;
import com.agro360.dao.production.avicole.IProductionDao;
import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IVariantDao;
import com.agro360.dto.production.avicole.CycleDto;
import com.agro360.dto.production.avicole.ProductionDto;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.VariantDto;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.vd.common.EditActionEnumVd;

@Service
public class ProductionOperation extends AbstractOperation<ProductionDto, Long> {

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
		return applyInitSearchRules(bean);
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
		
		List<Message> messages = new ArrayList<>();
//		var dto = mapper.mapToDto(bean);
//
//		switch (bean.getAction()) {
//		case CREATE:
//			save(dto);
//			messages.add(Message.success(CREATE_SUCCESS));
//			break;
//
//		case UPDATE:
//			save(dto);
//			messages.add(Message.success(UPDATE_SUCCESS));
//			break;
//
//		case DELETE:
//			delete(dto);
//			messages.add(Message.success(DELETE_SUCCESS));
//			break;
//			
//		default:
//			return Collections.singletonList(Message.warn("Aucune action à effectuer"));
//		}

		return messages;
	}

	public ProductionBean initCreateFormBean(Optional<Long> copyFrom) {
		var dto = copyFrom.map(dao::findById).flatMap(e -> e).orElseGet(ProductionDto::new);
		var bean = mapper.mapToBean(dto);
		bean.getProductionId().setValue(null);
		
		AbstractBean.setActionToCreate.accept(bean);
		
		return applyInitCreateRules(bean);
	}
}
