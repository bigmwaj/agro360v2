package com.agro360.ws.controller.stock;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.ArticleSearchBean;
import com.agro360.bo.bean.stock.ConversionBean;
import com.agro360.bo.bean.stock.VariantBean;
import com.agro360.form.stock.ArticleForm;
import com.agro360.form.stock.ConversionForm;
import com.agro360.form.stock.VariantForm;
import com.agro360.service.stock.ArticleService;
import com.agro360.ws.controller.common.AbstractController;

@RestController
@RequestMapping("/stock/article")
public class ArticleController extends AbstractController {

	@Autowired
	private ArticleService service;
	
	@Autowired
	private ArticleForm form;
	
	@Autowired
	private VariantForm variantForm;
	
	@Autowired
	private ConversionForm conversionForm;

	@GetMapping
	public ResponseEntity<ModelMap> searchAction(
			@RequestBody(required = false) @Validated Optional<ArticleSearchBean> searchBean) {
		var sb = searchBean.orElse(new ArticleSearchBean());
		var model = new ModelMap(RECORDS_MODEL_KEY, service.search(getClientContext(), sb));
		model.addAttribute(RECORDS_TOTAL_KEY, sb.getLength());		
		return ResponseEntity.ok(model);
	}

	@PostMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated ArticleBean bean) {
		var action = bean.getAction();
		var model = new ModelMap();
		var ctx = getClientContext();
		
		service.save(ctx, bean);
		switch (action) {
		case CREATE:
		case UPDATE:
			bean = form.initUpdateFormBean(ctx, bean.getArticleCode().getValue());	
			model.addAttribute(RECORD_MODEL_KEY, bean);
			break;

		default:
			break;
		}
		model.addAttribute(MESSAGES_MODEL_KEY, ctx.getMessages());
		return ResponseEntity.ok(model);
	}
	
	@GetMapping(SEARCH_FORM_RN)
	public ResponseEntity<ArticleSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(form.initSearchFormBean(getClientContext()));
	}
	
	@GetMapping(EDIT_FORM_RN)
	public ResponseEntity<ArticleBean> getEditFormAction(@RequestParam String articleCode) {
		return ResponseEntity.ok(form.initUpdateFormBean(getClientContext(), articleCode));
	}

	@GetMapping(CREATE_FORM_RN)
	public ResponseEntity<ArticleBean> getCreateFormAction(@RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(form.initCreateFormBean(getClientContext(), copyFrom));
	}

	@GetMapping(DELETE_FORM_RN)
	public ResponseEntity<ArticleBean> getDeleteFormAction(@RequestParam String articleCode) {
		return ResponseEntity.ok(form.initDeleteFormBean(getClientContext(), articleCode));
	}
	
	@GetMapping("/variant/create-form")
	public ResponseEntity<VariantBean> getVariantCreateFormAction(@RequestParam String articleCode, @RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(variantForm.initCreateFormBean(getClientContext(), articleCode, copyFrom));
	}
	
	@GetMapping("/conversion/create-form")
	public ResponseEntity<ConversionBean> getConversionCreateFormAction(@RequestParam String articleCode, @RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(conversionForm.initCreateFormBean(getClientContext(), articleCode, copyFrom));
	}
}
