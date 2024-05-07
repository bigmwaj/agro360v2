package com.agro360.form.stock;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.stock.InventaireBean;
import com.agro360.bo.bean.stock.InventaireSearchBean;
import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.bean.stock.MagasinSearchBean;
import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.form.common.AbstractForm;
import com.agro360.form.common.MetadataBeanName;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.ArticleOperation;
import com.agro360.operation.logic.stock.InventaireOperation;
import com.agro360.operation.logic.stock.MagasinOperation;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class InventaireForm extends AbstractForm{
	
	@Autowired
	private InventaireOperation operation;
	
	@Autowired
	private MagasinOperation magasinOperation;

	@Autowired
	private ArticleOperation articleOperation;

	@MetadataBeanName("stock/inventaire-search")
	public InventaireSearchBean initSearchFormBean(ClientContext ctx) {
		var bean = StockMapper.buildInventaireSearchBean();
		initMagasinOption(ctx, bean.getMagasinCode()::setValueOptions);
		
		return bean;
	}

	public InventaireBean initCreateFormBean(ClientContext ctx) {
		var bean = new InventaireBean();
		initMagasinOption(ctx, bean.getMagasin().getMagasinCode()::setValueOptions);
		return initCreateFormBean(ctx, bean);
	}
	
	@MetadataBeanName("stock/inventaire")
	public InventaireBean initEditFormBean(ClientContext ctx, ClientOperationEnumVd operation, String magasinCode, String articleCode, String variantCode) {
		var bean = this.operation.findInventaireByCode(ctx, magasinCode, articleCode, variantCode);
		var options = buildUniteOptions(ctx, articleCode);	
		bean.getUniteVente().getUniteCode().setValueOptions(options);
		bean.getUniteAchat().getUniteCode().setValueOptions(options);
		bean.setAction(operation);
		return bean;
	}
	
	private InventaireBean initCreateFormBean(ClientContext ctx, InventaireBean bean) {
		bean.getMagasin().getMagasinCode().setEditable(true);
		bean.getArticle().getArticleCode().setEditable(true);
		bean.getUniteAchat().getUniteCode().setEditable(true);
		bean.getUniteVente().getUniteCode().setEditable(true);
		return bean;
	}
	
	public void initCreateFormBean(ClientContext ctx, String magasinCode, String articleCode, List<InventaireBean> beans) {
		var options = buildUniteOptions(ctx, articleCode);		
		Function<InventaireBean, InventaireBean> setUniteOptions = e -> setUniteOption(e, options);
		Consumer<InventaireBean> init = e -> initCreateFormBean(ctx, e);
		beans.stream().map(setUniteOptions).forEach(init);
	}
	
	private InventaireBean setUniteOption(InventaireBean bean, Map<Object, String> options) {
		var def = options.entrySet().stream()
				.findFirst()
				.map(Map.Entry::getKey)
				.map(String.class::cast)
				.orElse(null);
		bean.getUniteAchat().getUniteCode().setValueOptions(options);
		bean.getUniteAchat().getUniteCode().setValue(def);
		bean.getUniteVente().getUniteCode().setValueOptions(options);
		bean.getUniteVente().getUniteCode().setValue(def);
		
		return bean;
	}
	
	private Map<Object, String> buildUniteOptions(ClientContext ctx, String articleCode){
		Function<UniteBean, String> key = e -> e.getUniteCode().getValue();
		Function<UniteBean, String> val = e -> e.getDescription().getValue();
		return articleOperation.findUnitesArticleByCode(ctx, articleCode)
				.stream().collect(Collectors.toMap(key, val));
	}
	
	private void initMagasinOption(ClientContext ctx, Consumer<Map<Object, String>> optionsSetter) {
		Function<MagasinBean, Object> codeFn = e -> e.getMagasinCode().getValue();
		Function<MagasinBean, String> libelleFn = e -> e.getDescription().getValue();
		
		var options = magasinOperation.findMagasinsByCriteria(ctx, new MagasinSearchBean(null))
				.stream().collect(Collectors.toMap(codeFn, libelleFn));
		optionsSetter.accept(options);
	}

}
