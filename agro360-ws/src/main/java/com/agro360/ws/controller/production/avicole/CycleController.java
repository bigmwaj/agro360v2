package com.agro360.ws.controller.production.avicole;

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

import com.agro360.service.bean.production.avicole.CycleBean;
import com.agro360.service.bean.production.avicole.CycleSearchBean;
import com.agro360.service.logic.production.avicole.CycleService;
import com.agro360.ws.controller.common.AbstractController;

@RestController
@RequestMapping("/production/avicole/cycle")
public class CycleController extends AbstractController {

	@Autowired
	private CycleService cycleService;
	
	@GetMapping
	public ResponseEntity<ModelMap> searchAction(
			@RequestBody(required = false) @Validated Optional<CycleSearchBean> searchBean) {
		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, cycleService
				.search(searchBean.orElse(new CycleSearchBean()))));
	}

	@PostMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated CycleBean bean, BindingResult br) {
		var messages = cycleService.save(bean);
		var model = new ModelMap(MESSAGES_MODEL_KEY, messages);
		return ResponseEntity.ok(model);
	}
	
	@GetMapping("/search-form")
	public ResponseEntity<CycleSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(cycleService.initSearchFormBean());
	}
	
	@GetMapping("/update-form")
	public ResponseEntity<CycleBean> getEditFormAction(@RequestParam String cycleCode) {
		return ResponseEntity.ok(cycleService.initEditFormBean(cycleCode));
	}

	@GetMapping("/create-form")
	public ResponseEntity<CycleBean> getCreateFormAction(@RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(cycleService.initCreateFormBean(copyFrom));
	}

	@GetMapping("/delete-form")
	public ResponseEntity<CycleBean> getDeleteFormAction(@RequestParam String cycleCode) {
		return ResponseEntity.ok(cycleService.initDeleteFormBean(cycleCode));
	}
}
