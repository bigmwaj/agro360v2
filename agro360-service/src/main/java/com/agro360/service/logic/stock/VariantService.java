package com.agro360.service.logic.stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IUniteDao;
import com.agro360.dao.stock.IVariantDao;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.VariantDto;
import com.agro360.dto.stock.VariantPk;
import com.agro360.service.bean.stock.ArticleBean;
import com.agro360.service.bean.stock.VariantBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.stock.VariantMapper;
import com.agro360.service.message.Message;
import com.agro360.vd.common.EditActionEnumVd;

@Service
public class VariantService extends AbstractService<VariantDto, VariantPk> {

	private static final String CREATE_SUCCESS = "Variant <b>%s</b> de l'article <b>%s</b> créée avec succès!";

	private static final String UPDATE_SUCCESS = "Variant <b>%s</b> de l'article <b>%s</b> modifée avec succès!";

	private static final String DELETE_SUCCESS = "Variant <b>%s</b> de l'article <b>%s</b> supprimée avec succès!";

	@Autowired
	IVariantDao dao;
	
	@Autowired
	IUniteDao uniteDao;

	@Autowired
	VariantMapper mapper;

	@Override
	protected IDao<VariantDto, VariantPk> getDao() {
		return dao;
	}
	
	@Override
	protected String getRulePath() {
		return "stock/article/variant";
	}
	
	private List<Message> deleteVariants(ArticleBean articleBean, List<VariantDto> existingVariants) {
		List<Message> messages = new ArrayList<>();
		dao.deleteAll(existingVariants);
		return messages;
	}

	private List<Message> synchVariants(ArticleBean articleBean, VariantBean bean, List<VariantDto> existingVariants) {
		var dto = mapper.mapToDto(articleBean, bean);
		List<Message> messages = new ArrayList<>();
		var variant = bean.getVariantCode().getValue();
		var article = articleBean.getArticleCode().getValue();

		switch (bean.getAction()) {
		case CREATE:
			save(dto);
			var msg = String.format(CREATE_SUCCESS, variant, article);
			messages.add(Message.success(msg));
			break;
			
		case UPDATE:
			save(dto);
			msg = String.format(UPDATE_SUCCESS, variant, article);
			messages.add(Message.success(msg));
			break;

		case DELETE:
			delete(dto);
			msg = String.format(DELETE_SUCCESS, variant, article);
			messages.add(Message.success(msg));
			break;
		default:
		}

		return messages;
	}
	
	private List<Message> addVariants(ArticleBean articleBean, VariantBean bean, List<VariantDto> existingVariants) {
		var dto = mapper.mapToDto(articleBean, bean);
		var variant = bean.getVariantCode().getValue();
		var article = articleBean.getArticleCode().getValue();
		List<Message> messages = new ArrayList<>();
		
		switch (bean.getAction()) {
		case CREATE:
			save(dto);
			var msg = String.format(CREATE_SUCCESS, variant, article);
			messages.add(Message.success(msg));
			break;
			
		default:
			
		}
		
		return messages;
	}

	public List<Message> synchVariants(ArticleBean articleBean) {
		
		if( articleBean.getAction() == null ) {
			return Collections.emptyList();
		}
		
		List<Message> messages = new ArrayList<>();
		var existingVariants = findVariants(articleBean);
		
		switch (articleBean.getAction()) {
		case CREATE:
			var casiers = articleBean.getVariants();
			for (var bean : casiers) {
				messages.addAll(addVariants(articleBean, bean, existingVariants));
			}
			break;
			
		case UPDATE:
			casiers = articleBean.getVariants();
			for (var bean : casiers) {
				messages.addAll(synchVariants(articleBean, bean, existingVariants));
			}
			break;

		case DELETE:
			messages.addAll(deleteVariants(articleBean, existingVariants));
			break;
		default:
		}

		return messages;
	}

	private List<VariantDto> findVariants(ArticleBean articleBean) {
		var ex = Example.of(new VariantDto());
		ex.getProbe().setArticle(new ArticleDto());
		ex.getProbe().getArticle().setArticleCode(articleBean.getArticleCode().getValue());

		return dao.findAll(ex);
	}

	public VariantBean initCreateFormBean(String articleCode, Optional<String> copyFrom) {
		Objects.requireNonNull(articleCode);
		
		var dto = copyFrom.map(e -> new VariantPk(articleCode, e))
				.map(dao::findById)
				.flatMap(e -> e).orElseGet(VariantDto::new);
		var bean = mapper.mapToBean(dto);
		bean.setAction(EditActionEnumVd.CREATE);
		return applyInitRules(bean, "init-create-form");
	}
}
