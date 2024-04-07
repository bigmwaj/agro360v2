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

import com.agro360.bo.bean.stock.CasierBean;
import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.bean.stock.MagasinSearchBean;
import com.agro360.form.stock.CasierForm;
import com.agro360.form.stock.MagasinForm;
import com.agro360.service.stock.MagasinService;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/stock/magasin")
public class MagasinController extends AbstractController {

	@Autowired
	private MagasinService service;
	
	@Autowired
	private MagasinForm form;
	
	@Autowired
	private CasierForm casierForm;

	@GetMapping()
	public ResponseEntity<ModelMap> searchAction(
			@RequestBody(required = false) @Validated Optional<MagasinSearchBean> searchBean) {
		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, service.searchAction(getClientContext(), searchBean)));
	}

	@PostMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated MagasinBean bean, BindingResult br) {
		service.saveAction(getClientContext(), bean);
		
		return ResponseEntity.ok(new ModelMap());
	}
	
	@GetMapping("/search-form")
	public ResponseEntity<MagasinSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(form.initSearchFormBean(getClientContext()));
	}
	
	@GetMapping("/edit-form")
	public ResponseEntity<MagasinBean> getEditFormAction(@RequestParam String magasinCode) {
		return ResponseEntity.ok(form.initEditFormBean(getClientContext(), magasinCode));
	}

	@GetMapping("/create-form")
	public ResponseEntity<MagasinBean> getCreateFormAction(@RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(form.initCreateFormBean(getClientContext(), copyFrom));
	}

	@GetMapping("/delete-form")
	public ResponseEntity<MagasinBean> getDeleteFormAction(@RequestParam String magasinCode) {
		return ResponseEntity.ok(form.initDeleteFormBean(getClientContext(), magasinCode));
	}
	
	@GetMapping("/casier/create-form")
	public ResponseEntity<CasierBean> getCasierCreateFormAction(@RequestParam String magasinCode, @RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(casierForm.initCreateFormBean(getClientContext(),  magasinCode, copyFrom));
	}
}
