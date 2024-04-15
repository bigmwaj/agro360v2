package com.agro360.form.stock;

import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.ArticleSearchBean;
import com.agro360.bo.bean.stock.InventaireBean;
import com.agro360.bo.bean.stock.InventaireSearchBean;
import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.bean.stock.MagasinSearchBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.form.common.AbstractForm;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.ArticleOperation;
import com.agro360.operation.logic.stock.MagasinOperation;

@Component
public class InventaireForm extends AbstractForm{
	
	@Autowired
	private MagasinOperation magasinOperation;
	
	@Autowired
	private ArticleOperation articleService;

	public InventaireSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = StockMapper.buildInventaireSearchBean();
		initMagasinOption(ctx, bean.getMagasinCode()::setValueOptions);
		initArticleOption(ctx, bean.getArticleCode()::setValueOptions);
		return bean;
	}

	public InventaireBean initCreateFormBean(ClientContext ctx) {
		var bean = new InventaireBean();

		initMagasinOption(ctx, bean.getMagasin().getMagasinCode()::setValueOptions);
		return bean;
	}
	
	private void initMagasinOption(ClientContext ctx, Consumer<Map<Object, String>> optionsSetter) {
		Function<MagasinBean, Object> codeFn = e -> e.getMagasinCode().getValue();
		Function<MagasinBean, String> libelleFn = e -> e.getDescription().getValue();
		
		var options = magasinOperation.findMagasinsByCriteria(ctx, new MagasinSearchBean())
				.stream().collect(Collectors.toMap(codeFn, libelleFn));
		optionsSetter.accept(options);
	}
	
	private void initArticleOption(ClientContext ctx, Consumer<Map<Object, String>> optionsSetter) {
		Function<ArticleBean, Object> codeFn = e -> e.getArticleCode().getValue();
		Function<ArticleBean, String> libelleFn = e -> e.getDescription().getValue();
		
		var options = articleService.findArticlesByCriteria(ctx, new ArticleSearchBean())
				.stream().collect(Collectors.toMap(codeFn, libelleFn));
		
		optionsSetter.accept(options);
	}

}
