package com.agro360.ws.controller.stock;

import java.util.List;
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

import com.agro360.bo.bean.stock.InventaireBean;
import com.agro360.bo.bean.stock.InventaireSearchBean;
import com.agro360.form.stock.InventaireForm;
import com.agro360.service.stock.InventaireService;
import com.agro360.ws.controller.common.AbstractController;

@RestController
@RequestMapping("/stock/inventaire")
public class InventaireController extends AbstractController {

	@Autowired
	private InventaireService service;
	
	@Autowired
	private InventaireForm form;
	
	@GetMapping
	public ResponseEntity<ModelMap> searchAction(
			@RequestBody(required = false) @Validated Optional<InventaireSearchBean> searchBean) {
		var sb = searchBean.orElse(new InventaireSearchBean());
		var model = new ModelMap(RECORDS_MODEL_KEY, service.search(getClientContext(), sb));
		model.addAttribute(RECORDS_TOTAL_KEY, sb.getLength());		
		return ResponseEntity.ok(model);
	}

	@PostMapping
	public ResponseEntity<ModelMap> saveAction(
			@RequestParam(required = true) String magasinCode,
			@RequestParam(required = true) String articleCode,
			@RequestBody @Validated List<InventaireBean> beans) {
		
		var ctx = getClientContext();
		service.save(ctx, magasinCode, articleCode, beans);
		return ResponseEntity.ok(new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages()));
	}
	
	@PostMapping("ajuster-quantite")
	public ResponseEntity<ModelMap> ajusterQuantiteAction(@RequestBody @Validated InventaireBean bean) {
		var ctx = getClientContext();
		var record = service.ajusterQuantite(ctx, bean);
		var model = new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages());
		model.addAttribute(RECORD_MODEL_KEY, record);
		return ResponseEntity.ok(model);
	}
	
	@PostMapping("ajuster-prix")
	public ResponseEntity<ModelMap> ajusterPrixAction(@RequestBody @Validated InventaireBean bean) {
		var ctx = getClientContext();
		var record = service.ajusterPrix(ctx, bean);
		var model = new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages());
		model.addAttribute(RECORD_MODEL_KEY, record);
		return ResponseEntity.ok(model);
	}
	
	@GetMapping(SEARCH_FORM_RN)
	public ResponseEntity<InventaireSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(form.initSearchFormBean(getClientContext()));
	}
	
	@GetMapping(CREATE_FORM_RN)
	public ResponseEntity<InventaireBean> getCreateFormAction() {
		return ResponseEntity.ok(form.initCreateFormBean(getClientContext()));
	}
	
	@GetMapping("non-stocked-articles")
	public ResponseEntity<ModelMap> getNonStockedArticlesAction(@RequestParam(required = true) String magasinCode) {
		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, 
				service.findNonStockedArticles(getClientContext(), magasinCode)));
	}
	
	@GetMapping("non-stocked-article-variants")
	public ResponseEntity<ModelMap> getNonStockedArticleVariantsAction(
			@RequestParam(required = true) String magasinCode,
			@RequestParam(required = true) String articleCode) {
		var ctx = getClientContext();
		var forms = service.findNonStockedArticleVariants(ctx, magasinCode, articleCode);
		form.initCreateFormBean(ctx, magasinCode, articleCode, forms);
		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, forms));
	}
}
