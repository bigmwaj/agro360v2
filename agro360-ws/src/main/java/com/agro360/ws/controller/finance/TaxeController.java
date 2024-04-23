package com.agro360.ws.controller.finance;

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

import com.agro360.bo.bean.finance.TaxeBean;
import com.agro360.form.finance.TaxeForm;
import com.agro360.service.finance.TaxeService;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/finance/taxe")
public class TaxeController extends AbstractController {

	@Autowired
	private TaxeService service;
	
	@Autowired
	private TaxeForm form;

	@GetMapping("/create-form")
	public ResponseEntity<TaxeBean> getCreateFormAction(@RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(form.initCreateFormBean(getClientContext(), copyFrom));
	}

	@GetMapping()
	public ResponseEntity<ModelMap> searchAction() {
		var ctx = getClientContext();
		var list = service.searchAction(ctx);
		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, form.initUpdateFormBean(ctx, list)));
	}

	@PostMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated List<TaxeBean> beans, BindingResult br) {
		service.saveAction(getClientContext(), beans);
		return ResponseEntity.ok(new ModelMap());
	}
}
