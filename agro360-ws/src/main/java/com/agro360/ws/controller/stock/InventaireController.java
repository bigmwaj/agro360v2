package com.agro360.ws.controller.stock;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.agro360.bo.bean.stock.InventaireBean;
import com.agro360.bo.bean.stock.InventaireSearchBean;
import com.agro360.form.stock.InventaireForm;
import com.agro360.service.stock.InventaireService;
import com.agro360.vd.common.ClientOperationEnumVd;
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
	
	@GetMapping(EDIT_FORM_RN)
	public ResponseEntity<InventaireBean> getEditFormAction(
			@RequestParam(required = true) ClientOperationEnumVd operation,
			@RequestParam(required = true) String magasinCode,
			@RequestParam(required = true) String articleCode,
			@RequestParam(required = true) String variantCode) {
		
		var ctx = getClientContext();
		return ResponseEntity.ok(form.initEditFormBean(ctx, operation, magasinCode, articleCode, variantCode));
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
	
	@PostMapping("ajuster")
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated InventaireBean bean) {
		var ctx = getClientContext();
		var record = service.save(ctx, bean);
		var model = new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages());
		model.addAttribute(RECORD_MODEL_KEY, record);
		return ResponseEntity.ok(model);
	}
	
	@PostMapping(value = "import", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ModelMap> importAction(@RequestPart("files")  List<MultipartFile> files) {
		var excelData = files.stream().map(t -> {
			try {
				return t.getInputStream();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e.getLocalizedMessage());
			}			
		}).collect(Collectors.toList());
		
		
		var ctx = getClientContext();
		service.importInventory(ctx, excelData);
		var model = new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages());
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
