package com.agro360.ws.controller.stock;

import java.util.List;
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

import com.agro360.service.bean.stock.UniteBean;
import com.agro360.service.bean.stock.UniteSearchBean;
import com.agro360.service.logic.stock.UniteService;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/stock/unite")
public class UniteController extends AbstractController {

	@Autowired
	private UniteService uniteService;

	@GetMapping()
	public ResponseEntity<ModelMap> searchAction(@RequestBody(required = false) @Validated Optional<UniteSearchBean> searchBean) {
		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, uniteService.search(searchBean.orElse(new UniteSearchBean()))));
	}

	@PostMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated List<UniteBean> beans, BindingResult br) {
		var messages = uniteService.save(beans);
		var model = new ModelMap(MESSAGES_MODEL_KEY, messages);
		return ResponseEntity.ok(model);
	}
	
	@GetMapping("/search-form")
	public ResponseEntity<UniteSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(uniteService.initSearchFormBean());
	}

	@GetMapping("/create-form")
	public ResponseEntity<UniteBean> getCreateFormAction(@RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(uniteService.initCreateFormBean(copyFrom));
	}
}
