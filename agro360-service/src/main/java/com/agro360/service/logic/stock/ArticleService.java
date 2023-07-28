package com.agro360.service.logic.stock;

import static com.agro360.service.mapper.stock.ArticleMapper.OPTION_MAP_CONVERSION_KEY;
import static com.agro360.service.mapper.stock.ArticleMapper.OPTION_MAP_VARIANT_KEY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IArticleDao;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.service.bean.stock.ArticleBean;
import com.agro360.service.bean.stock.ArticleSearchBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.stock.ArticleMapper;
import com.agro360.service.utils.Message;

@Service
public class ArticleService extends AbstractService<ArticleDto, String> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	@Autowired
	IArticleDao dao;

	@Autowired
	VariantService variantService;

	@Autowired
	ConversionService conversionService;

	@Autowired
	ArticleMapper mapper;

	@Override
	protected IDao<ArticleDto, String> getDao() {
		return dao;
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
		
		ArticleDto dto = mapper.mapToDto(bean);
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

	public ArticleBean findById(String id) {
		return dao.findById(id)
				.map(e -> mapper.mapToBean(e, Map.of(OPTION_MAP_CONVERSION_KEY, true, OPTION_MAP_VARIANT_KEY, true)))
				.orElseThrow();
	}

}
