package com.agro360.service.logic.stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IConversionDao;
import com.agro360.dao.stock.IUniteDao;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.ConversionDto;
import com.agro360.dto.stock.ConversionPk;
import com.agro360.service.bean.stock.ArticleBean;
import com.agro360.service.bean.stock.ConversionBean;
import com.agro360.service.exception.ServiceLogicException;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.stock.ConversionMapper;
import com.agro360.service.mapper.stock.StockSharedMapperHelper;
import com.agro360.service.message.Message;
import com.agro360.vd.common.EditActionEnumVd;

@Service
public class ConversionService extends AbstractService<ConversionDto, ConversionPk> {

	private static final String CREATE_SUCCESS = "La conversion de l'unité <b>%s</b> pour l'article <b>%s</b> créée avec succès!";

	private static final String UPDATE_SUCCESS = "La conversion de l'unité <b>%s</b> pour l'article <b>%s</b> modifée avec succès!";

	private static final String DELETE_SUCCESS = "La conversion de l'unité <b>%s</b> pour l'article <b>%s</b> supprimée avec succès!";

	private static final String ERROR_MSG1 = "Aucune conversion disponible pour l'article <b>%s</b>!";
	
	private static final String ERROR_MSG3 = "Toutes les conversions de l'article <b>%s</b> ont été supprimées!";
	
	private static final String ERROR_MSG2 = "Concernant une conversion de l'unité <b>%s</b>, l'opération <b>%s</b> n'est pas autorisée pour l'article <b>%s</b>!";

	@Autowired
	IConversionDao dao;

	@Autowired
	IUniteDao uniteDao;

	@Autowired
	ConversionMapper mapper;
	
	@Override
	protected String getRulePath() {
		return "stock/article/conversion";
	}

	@Override
	protected IDao<ConversionDto, ConversionPk> getDao() {
		return dao;
	}

	private List<Message> deleteConversions(ArticleBean articleBean) {
		var article = articleBean.getArticleCode().getValue();
		
		if( article == null ) {
			throw new ServiceLogicException(Message.error("Article inconnu!"));
		}
		
		var ex = Example.of(new ConversionDto());
		ex.getProbe().setArticle(new ArticleDto());
		ex.getProbe().getArticle().setArticleCode(article);
		
		var conversions = dao.findAll(ex);
		dao.deleteAll(conversions);
		
		if( conversions.isEmpty() ) {
			var msg = String.format(ERROR_MSG1, article);
			return Collections.singletonList(Message.warn(msg));
		}
		
		var msg = String.format(ERROR_MSG3, article);
		return Collections.singletonList(Message.success(msg));
	}

	private List<Message> addConversions(ArticleBean articleBean, 
			ConversionBean bean, List<ConversionDto> existingConversions) {
		List<Message> messages = new ArrayList<>();
		var dto = mapper.mapToDto(articleBean, bean);
		var unite = bean.getUnite().getUniteCode().getValue();
		var article = articleBean.getArticleCode().getValue();

		switch (bean.getAction()) {
		case CREATE:
			save(dto);
			var msg = String.format(CREATE_SUCCESS, unite, article);
			messages.add(Message.success(msg));
			break;
			
		default:
			msg = String.format(ERROR_MSG2, bean.getAction(), unite, article);
			throw new ServiceLogicException(Message.error(msg));
		}

		return messages;
	}

	private List<Message> synchConversions(ArticleBean articleBean, 
			ConversionBean bean, List<ConversionDto> existingConversions) {
		
		var dto = mapper.mapToDto(articleBean, bean);
		var unite = bean.getUnite().getUniteCode().getValue();
		var article = articleBean.getArticleCode().getValue();
		List<Message> messages = new ArrayList<>();

		switch (bean.getAction()) {
		case CREATE:
			save(dto);
			var msg = String.format(CREATE_SUCCESS, unite, article);
			messages.add(Message.success(msg));
			break;
			
		case UPDATE:
			save(dto);
			msg = String.format(UPDATE_SUCCESS, unite, article);
			messages.add(Message.success(msg));
			break;

		case DELETE:
			delete(dto);
			msg = String.format(DELETE_SUCCESS, unite, article);
			messages.add(Message.success(msg));
			break;
		default:
		}

		return messages;
	}

	public List<Message> synchConversions(ArticleBean articleBean) {
		if( articleBean.getAction() == null ) {
			return Collections.emptyList();
		}
		
		var existingConversions = findConversions(articleBean);
		List<Message> messages = new ArrayList<>();

		switch (articleBean.getAction()) {
		case CREATE:
			var conversions = articleBean.getConversions();
			for (var bean : conversions) {
				messages.addAll(addConversions(articleBean, bean, existingConversions));
			}
			break;
			
		case UPDATE:
			conversions = articleBean.getConversions();
			for (var bean : conversions) {
				messages.addAll(synchConversions(articleBean, bean, existingConversions));
			}
			break;

		case DELETE:
			messages.addAll(deleteConversions(articleBean));
			break;
		default:
		}

		return messages;
	}

	private List<ConversionDto> findConversions(ArticleBean articleBean) {
		var ex = Example.of(new ConversionDto());
		var article = articleBean.getArticleCode().getValue();
		
		ex.getProbe().setArticle(new ArticleDto());
		ex.getProbe().getArticle().setArticleCode(article);

		return dao.findAll(ex);
	}

	public ConversionBean initCreateFormBean(String articleCode, Optional<String> copyFrom) {
		var dto = copyFrom.map(e -> new ConversionPk(articleCode, e))
				.map(dao::findById)
				.flatMap(e -> e).orElseGet(ConversionDto::new);
		var bean = mapper.mapToBean(dto);
		bean.setAction(EditActionEnumVd.CREATE);
		bean.getUnite().getUniteCode().setValueOptions(StockSharedMapperHelper.getAllAsValueOptions(uniteDao));
		return applyInitRules(bean, "init-create-form");
	}
}
