package com.agro360.service.logic.stock;

import static com.agro360.service.mapper.stock.ArticleMapper.OPTION_MAP_CONVERSION_KEY;
import static com.agro360.service.mapper.stock.ArticleMapper.OPTION_MAP_VARIANT_KEY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IArticleDao;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.bean.stock.ArticleBean;
import com.agro360.service.bean.stock.ArticleSearchBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.stock.ArticleMapper;
import com.agro360.service.message.Message;
import com.agro360.vd.common.EditActionEnumVd;

@Service
public class ArticleService extends AbstractService<ArticleDto, String> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	private static final String DTO_NOT_FOUND = "Aucun n'article de code %s n'a été trouvé";

	@Autowired
	private IArticleDao dao;

	@Autowired
	private VariantService variantService;

	@Autowired
	private ConversionService conversionService;

	@Autowired
	private ArticleMapper mapper;

	@Override
	protected IDao<ArticleDto, String> getDao() {
		return dao;
	}
	
	public ArticleSearchBean initSearchFormBean() {
		return mapper.mapToSearchBean();
	}
	
	public ArticleBean initEditFormBean(String articleCode) {
		var dto = dao.findById(articleCode).orElseThrow(dtoNotFoundEx(articleCode));
		var bean = mapper.mapToBean(dto,  Map.of(OPTION_MAP_CONVERSION_KEY, true, OPTION_MAP_VARIANT_KEY, true));
		return bean;
	}
	
	public ArticleBean initDeleteFormBean(String articleCode) {
		var bean = dao.findById(articleCode).map(mapper::mapToBean).orElseThrow(dtoNotFoundEx(articleCode));
		bean.setAction(EditActionEnumVd.DELETE);
		return bean;
	}

	public ArticleBean initCreateFormBean(Optional<String> copyFrom) {
		var dto = copyFrom.map(dao::findById).flatMap(e -> e).orElseGet(ArticleDto::new);
		var bean = mapper.mapToBean(dto, Map.of(OPTION_MAP_CONVERSION_KEY, true, OPTION_MAP_VARIANT_KEY, true));
		bean.getArticleCode().setValue(null);
		
		AbstractBean.setActionToCreate.accept(bean);
		bean.getVariants().stream().forEach(AbstractBean.setActionToCreate);
		bean.getConversions().stream().forEach(AbstractBean.setActionToCreate);
		
		return bean;
	}

	public List<ArticleBean> search(ArticleSearchBean searchBean) {
		var example = Example.of(new ArticleDto());
		if( searchBean.getArticleCode().getValue() != null ) {
			example.getProbe().setArticleCode(searchBean.getArticleCode().getValue());
		}
		if( searchBean.getTypeArticle().getValue() != null ) {
			example.getProbe().setTypeArticle(searchBean.getTypeArticle().getValue());
		}
		return dao.findAll(example).stream().map(mapper::mapToBean).collect(Collectors.toList());
	}

	public List<Message> save(ArticleBean bean) {
		if( bean.getAction() == null ) {
			return Collections.singletonList(Message.error("Aucune action sélectionnée"));
		}
		
		var dto = mapper.mapToDto(bean);
		List<Message> messages = new ArrayList<>();

		switch (bean.getAction()) {
		case CREATE:
			save(dto);
			messages.add(Message.success(CREATE_SUCCESS));
			messages.addAll(variantService.synchVariants(bean));
			messages.addAll(conversionService.synchConversions(bean));
			break;

		case UPDATE:
			save(dto);
			messages.add(Message.success(UPDATE_SUCCESS));
			messages.addAll(variantService.synchVariants(bean));
			messages.addAll(conversionService.synchConversions(bean));
			break;

		case DELETE:
			messages.addAll(variantService.synchVariants(bean));
			messages.addAll(conversionService.synchConversions(bean));
			delete(dto);
			messages.add(Message.success(DELETE_SUCCESS));
			break;
			
		default:
			return Collections.singletonList(Message.warn("Aucune action à effectuer"));
		}
		return messages;
	}
	
	private Supplier<RuntimeException> dtoNotFoundEx(String articleCode){
		return () -> new RuntimeException(String.format(DTO_NOT_FOUND, articleCode));	
	}
}
