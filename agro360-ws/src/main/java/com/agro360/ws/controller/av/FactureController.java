package com.agro360.ws.controller.av;

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

import com.agro360.bo.bean.av.FactureBean;
import com.agro360.bo.bean.av.FactureSearchBean;
import com.agro360.form.av.FactureForm;
import com.agro360.service.av.FactureService;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/achat-vente/facture")
public class FactureController extends AbstractController {

	@Autowired
	private FactureService service;
	
	@Autowired
	private FactureForm form;

	@GetMapping
	public ResponseEntity<ModelMap> searchAction(
			@RequestBody(required = false) @Validated Optional<FactureSearchBean> searchBean) {
		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, service.searchAction(getClientContext(), searchBean)));
	}
	
	@GetMapping(SEARCH_FORM_RN)
	public ResponseEntity<FactureSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(form.initSearchFormBean(getClientContext()));
	}
	
	@GetMapping(EDIT_FORM_RN)
	public ResponseEntity<FactureBean> getEditFormAction(@RequestParam String factureCode) {
		return ResponseEntity.ok(form.initEditFormBean(getClientContext(), factureCode));
	}

	@GetMapping(CREATE_FORM_RN)
	public ResponseEntity<FactureBean> getCreateFormAction(@RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(form.initCreateFormBean(getClientContext(), copyFrom));
	}

	@GetMapping(DELETE_FORM_RN)
	public ResponseEntity<FactureBean> getDeleteFormAction(@RequestParam String factureCode) {
		return ResponseEntity.ok(form.initDeleteFormBean(getClientContext(), factureCode));
	}

	@GetMapping(CHANGE_STATUS_FORM_RN)
	public ResponseEntity<FactureBean> getChangeStatusFormAction(@RequestParam String factureCode) {
		return ResponseEntity.ok(form.initChangeStatusFormBean(getClientContext(), factureCode));
	}
	
	@PostMapping()
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated FactureBean bean) {
		var ctx = getClientContext();
		service.save(ctx, bean);
		return ResponseEntity.ok(new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages()));
	}
}
