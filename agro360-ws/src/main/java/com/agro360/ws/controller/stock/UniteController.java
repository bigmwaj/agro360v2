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

import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.bean.stock.UniteSearchBean;
import com.agro360.form.stock.UniteForm;
import com.agro360.service.stock.UniteService;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/stock/unite")
public class UniteController extends AbstractController {

	@Autowired
	private UniteService service;
	
	@Autowired
	private UniteForm form;
	
	@GetMapping("/search-form")
	public ResponseEntity<UniteSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(form.initSearchFormBean(getClientContext()));
	}

	@GetMapping("/create-form")
	public ResponseEntity<UniteBean> getCreateFormAction(@RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(form.initCreateFormBean(getClientContext(), copyFrom));
	}

	@GetMapping()
	public ResponseEntity<ModelMap> searchAction(@RequestBody(required = false) @Validated Optional<UniteSearchBean> searchBean) {
		var ctx = getClientContext();
		var sb = searchBean.orElse(new UniteSearchBean());
		var list = service.search(ctx, sb);
		form.initUpdateFormBean(ctx, list);
		var model = new ModelMap(RECORDS_MODEL_KEY, list);	
		model.addAttribute(RECORDS_TOTAL_KEY, sb.getLength());	
		return ResponseEntity.ok(model);
	}

	@PostMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated List<UniteBean> beans) {
		var ctx = getClientContext();
		service.save(ctx, beans);
		return ResponseEntity.ok(new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages()));
	}
	
}
