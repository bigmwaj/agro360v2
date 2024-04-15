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

import com.agro360.bo.bean.finance.CompteBean;
import com.agro360.bo.bean.finance.CompteSearchBean;
import com.agro360.form.finance.CompteForm;
import com.agro360.service.finance.CompteService;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/finance/compte")
public class CompteController extends AbstractController {

	@Autowired
	CompteService service;
	
	@Autowired
	CompteForm form;
	
	@GetMapping("/search-form")
	public ResponseEntity<CompteSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(form.initSearchFormBean(getClientContext()));
	}

	@GetMapping("/create-form")
	public ResponseEntity<CompteBean> getCreateFormAction(@RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(form.initCreateFormBean(getClientContext(), copyFrom));
	}
	
	@GetMapping("/generate-etat-compte")
	public ResponseEntity<ModelMap> generateEtatCompteAction() {
		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, service.generateEtatCompteAction(getClientContext())));
	}

	@GetMapping()
	public ResponseEntity<ModelMap> searchAction(@RequestBody(required = false) @Validated Optional<CompteSearchBean> searchBean) {
		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, service.searchAction(getClientContext(), searchBean)));
	}

	@PostMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated List<CompteBean> beans, BindingResult br) {
		service.saveAction(getClientContext(), beans);
		return ResponseEntity.ok(new ModelMap());
	}
}
