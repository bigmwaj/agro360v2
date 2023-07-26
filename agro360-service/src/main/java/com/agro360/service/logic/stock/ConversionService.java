package com.agro360.service.logic.stock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IConversionDao;
import com.agro360.dto.stock.ConversionDto;
import com.agro360.dto.stock.ConversionPk;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.service.bean.stock.ConversionBean;
import com.agro360.service.bean.stock.ArticleBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.stock.ConversionMapper;
import com.agro360.service.utils.Message;

@Service
public class ConversionService extends AbstractService<ConversionDto, ConversionPk> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	@Autowired
	IConversionDao dao;

	@Autowired
	ConversionMapper mapper;

	@Override
	protected IDao<ConversionDto, ConversionPk> getDao() {
		return dao;
	}

	private List<Message> deleteConversion(ArticleBean articleBean, List<ConversionDto> existingConversions) {
		List<Message> messages = new ArrayList<>();
		dao.deleteAll(existingConversions);
		return messages;
	}

	private List<Message> synchConversions(ArticleBean articleBean, ConversionBean bean, List<ConversionDto> existingConversions) {
		ConversionDto dto = mapper.mapToDto(articleBean, bean);
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
			if (existingConversions.contains(dto)) {
				delete(dto);
				messages.add(Message.success(DELETE_SUCCESS));
			}else {
				messages.add(Message.warn(String.format("La conversion de l'unité %s n'existe pas!", bean.getUnite().getUniteCode().getValue())));
			}
			break;
		default:
		}

		return messages;
	}

	public List<Message> synchConversions(ArticleBean articleBean) {
		List<Message> messages = new ArrayList<>();
		List<ConversionDto> existingConversions = findConversions(articleBean);

		switch (articleBean.getAction()) {
		case CREATE:
		case UPDATE:
			List<ConversionBean> casiers = articleBean.getConversions();
			for (ConversionBean bean : casiers) {
				messages.addAll(synchConversions(articleBean, bean, existingConversions));
			}
			break;

		case DELETE:
			messages.addAll(deleteConversion(articleBean, existingConversions));
			break;
		default:
		}

		return messages;
	}

	private List<ConversionDto> findConversions(ArticleBean articleBean) {
		Example<ConversionDto> ex = Example.of(new ConversionDto());
		ex.getProbe().setArticle(new ArticleDto());
		ex.getProbe().getArticle().setArticleCode(articleBean.getArticleCode().getValue());

		return dao.findAll(ex);
	}
}
