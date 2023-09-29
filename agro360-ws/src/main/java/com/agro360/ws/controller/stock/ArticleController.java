package com.agro360.ws.controller.stock;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agro360.service.bean.stock.ArticleBean;
import com.agro360.service.bean.stock.ArticleSearchBean;
import com.agro360.service.bean.stock.ConversionBean;
import com.agro360.service.bean.stock.VariantBean;
import com.agro360.service.logic.stock.ArticleService;
import com.agro360.service.logic.stock.ConversionService;
import com.agro360.service.logic.stock.VariantService;
import com.agro360.ws.controller.common.AbstractController;

@RestController
@RequestMapping("/stock/article")
public class ArticleController extends AbstractController {

	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private VariantService variantService;
	
	@Autowired
	private ConversionService conversionService;

	@GetMapping
	public ResponseEntity<ModelMap> searchAction(
			@RequestBody(required = false) @Validated Optional<ArticleSearchBean> searchBean) {
		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, articleService
				.search(searchBean.orElse(new ArticleSearchBean()))));
	}

	@PostMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated ArticleBean bean, BindingResult br) {
		return ResponseEntity.ok(new ModelMap().addAllAttributes(articleService.save(bean)));
	}
	
	@GetMapping(SEARCH_FORM_RN)
	public ResponseEntity<ArticleSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(articleService.initSearchFormBean());
	}
	
	@GetMapping(EDIT_FORM_RN)
	public ResponseEntity<ArticleBean> getEditFormAction(@RequestParam String articleCode) {
		return ResponseEntity.ok(articleService.initEditFormBean(articleCode));
	}

	@GetMapping(CREATE_FORM_RN)
	public ResponseEntity<ArticleBean> getCreateFormAction(@RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(articleService.initCreateFormBean(copyFrom));
	}

	@GetMapping(DELETE_FORM_RN)
	public ResponseEntity<ArticleBean> getDeleteFormAction(@RequestParam String articleCode) {
		return ResponseEntity.ok(articleService.initDeleteFormBean(articleCode));
	}
	
	@GetMapping("/variant/create-form")
	public ResponseEntity<VariantBean> getVariantCreateFormAction(@RequestParam String articleCode, @RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(variantService.initCreateFormBean(articleCode, copyFrom));
	}
	
	@GetMapping("/conversion/create-form")
	public ResponseEntity<ConversionBean> getConversionCreateFormAction(@RequestParam String articleCode, @RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(conversionService.initCreateFormBean(articleCode, copyFrom));
	}
}
