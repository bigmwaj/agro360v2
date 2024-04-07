package com.agro360.ws.controller.core;

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

import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.core.PartnerSearchBean;
import com.agro360.form.core.PartnerForm;
import com.agro360.operation.context.ClientContext;
import com.agro360.service.core.PartnerService;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/core/partner")
public class PartnerController extends AbstractController {

	@Autowired
	private PartnerService service;
	
	@Autowired
	private PartnerForm form;

	@GetMapping()
	public ResponseEntity<ModelMap> searchAction(
			@RequestBody 
			@Validated Optional<PartnerSearchBean> searchForm, 
			BindingResult br) {	
		var ctx = new ClientContext();
		var records = service.searchAction(ctx, searchForm);
		var model = new ModelMap(RECORDS_MODEL_KEY, records);	
		return ResponseEntity.ok(model);
	}
	
	@GetMapping("/search-form")
	public ResponseEntity<PartnerSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(form.initSearchFormBean(getClientContext()));
	}
	
	@GetMapping("/edit-form")
	public ResponseEntity<PartnerBean> getEditFormAction(@RequestParam String partnerCode) {
		return ResponseEntity.ok(form.initEditFormBean(getClientContext(), partnerCode));
	}

	@GetMapping("/create-form")
	public ResponseEntity<PartnerBean> getCreateFormAction(@RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(form.initCreateFormBean(getClientContext(), copyFrom));
	}

	@GetMapping("/delete-form")
	public ResponseEntity<PartnerBean> getDeleteFormAction(@RequestParam String partnerCode) {
		System.out.println("PartnerController.getDeleteFormAction() " + partnerCode);
		return ResponseEntity.ok(form.initDeleteFormBean(getClientContext(), partnerCode));
	}

	@GetMapping("/change-status-form")
	public ResponseEntity<PartnerBean> getChangeStatusFormAction(@RequestParam String partnerCode) {
		return ResponseEntity.ok(form.initChangeStatusFormBean(getClientContext(), partnerCode));
	}
	
	@PostMapping()
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated PartnerBean bean, BindingResult br) {
		service.saveAction(getClientContext(), bean);
		return ResponseEntity.ok(new ModelMap());
	}

}
