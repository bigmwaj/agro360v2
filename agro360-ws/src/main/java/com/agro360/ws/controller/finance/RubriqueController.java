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

import com.agro360.bo.bean.finance.RubriqueBean;
import com.agro360.bo.bean.finance.RubriqueSearchBean;
import com.agro360.form.finance.RubriqueForm;
import com.agro360.service.finance.RubriqueService;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/finance/rubrique")
public class RubriqueController extends AbstractController {

	@Autowired
	private RubriqueService service;
	
	@Autowired
	private RubriqueForm form;
	
	@GetMapping("/search-form")
	public ResponseEntity<RubriqueSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(form.initSearchFormBean(getClientContext()));
	}

	@GetMapping("/create-form")
	public ResponseEntity<RubriqueBean> getCreateFormAction(@RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(form.initCreateFormBean(getClientContext(), copyFrom));
	}

	@GetMapping()
	public ResponseEntity<ModelMap> searchAction(@RequestBody(required = false) @Validated Optional<RubriqueSearchBean> searchBean) {
		var ctx = getClientContext();
		var sb = searchBean.orElse(new RubriqueSearchBean());
		var result = service.search(ctx, sb);
		var model = new ModelMap(RECORDS_MODEL_KEY, form.initUpdateFormBean(ctx, result));
		model.addAttribute(RECORDS_TOTAL_KEY, sb.getLength());		
		return ResponseEntity.ok(model);
	}

	@PostMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated List<RubriqueBean> beans, BindingResult br) {
		service.save(getClientContext(), beans);
		return ResponseEntity.ok(new ModelMap());
	}
}
