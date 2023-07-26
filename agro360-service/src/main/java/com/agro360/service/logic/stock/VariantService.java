package com.agro360.service.logic.stock;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IVariantDao;
import com.agro360.dto.stock.VariantDto;
import com.agro360.dto.stock.VariantPk;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.service.bean.stock.VariantBean;
import com.agro360.service.bean.stock.ArticleBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.stock.VariantMapper;
import com.agro360.service.utils.Message;

@Service
public class VariantService extends AbstractService<VariantDto, VariantPk> {

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";

	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";

	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";

	@Autowired
	IVariantDao dao;

	@Autowired
	VariantMapper mapper;

	@Override
	protected IDao<VariantDto, VariantPk> getDao() {
		return dao;
	}

	private List<Message> deleteVariant(ArticleBean articleBean, List<VariantDto> existingVariants) {
		List<Message> messages = new ArrayList<>();
		dao.deleteAll(existingVariants);
		return messages;
	}

	private List<Message> synchVariants(ArticleBean articleBean, VariantBean bean, List<VariantDto> existingVariants) {
		VariantDto dto = mapper.mapToDto(articleBean, bean);
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
			if (existingVariants.contains(dto)) {
				delete(dto);
				messages.add(Message.success(DELETE_SUCCESS));
			}else {
				messages.add(Message.warn(String.format("Le casier %s n'existe pas!", bean.getVariantCode().getValue())));
			}
			break;
		default:
		}

		return messages;
	}

	public List<Message> synchVariants(ArticleBean articleBean) {
		List<Message> messages = new ArrayList<>();
		List<VariantDto> existingVariants = findVariants(articleBean);

		switch (articleBean.getAction()) {
		case CREATE:
		case UPDATE:
			List<VariantBean> casiers = articleBean.getVariants();
			for (VariantBean bean : casiers) {
				messages.addAll(synchVariants(articleBean, bean, existingVariants));
			}
			break;

		case DELETE:
			messages.addAll(deleteVariant(articleBean, existingVariants));
			break;
		default:
		}

		return messages;
	}

	private List<VariantDto> findVariants(ArticleBean articleBean) {
		Example<VariantDto> ex = Example.of(new VariantDto());
		ex.getProbe().setArticle(new ArticleDto());
		ex.getProbe().getArticle().setArticleCode(articleBean.getArticleCode().getValue());

		return dao.findAll(ex);
	}
}
