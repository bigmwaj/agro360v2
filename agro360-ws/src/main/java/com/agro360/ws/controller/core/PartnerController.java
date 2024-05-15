package com.agro360.ws.controller.core;

import java.util.Collections;
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

import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.core.PartnerSearchBean;
import com.agro360.form.core.PartnerForm;
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
			@Validated Optional<PartnerSearchBean> searchBean) {	
		var sb = searchBean.orElse(new PartnerSearchBean());
		var model = new ModelMap(RECORDS_MODEL_KEY, service.search(getClientContext(), sb));
		model.addAttribute(RECORDS_TOTAL_KEY, sb.getLength());		
		return ResponseEntity.ok(model);
	}
	
	@GetMapping(SEARCH_FORM_RN)
	public ResponseEntity<PartnerSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(form.initSearchFormBean(getClientContext()));
	}
	
	@GetMapping(EDIT_FORM_RN)
	public ResponseEntity<PartnerBean> getEditFormAction(@RequestParam String partnerCode) {
		return ResponseEntity.ok(form.initEditFormBean(getClientContext(), partnerCode));
	}

	@GetMapping(CREATE_FORM_RN)
	public ResponseEntity<PartnerBean> getCreateFormAction(@RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(form.initCreateFormBean(getClientContext(), copyFrom));
	}

	@GetMapping(DELETE_FORM_RN)
	public ResponseEntity<PartnerBean> getDeleteFormAction(@RequestParam String partnerCode) {
		System.out.println("PartnerController.getDeleteFormAction() " + partnerCode);
		return ResponseEntity.ok(form.initDeleteFormBean(getClientContext(), partnerCode));
	}

	@GetMapping(CHANGE_STATUS_FORM_RN)
	public ResponseEntity<PartnerBean> getChangeStatusFormAction(@RequestParam String partnerCode) {
		return ResponseEntity.ok(form.initChangeStatusFormBean(getClientContext(), partnerCode));
	}
	
	@PostMapping()
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated PartnerBean bean) {
		var action = bean.getAction();
		var ctx = getClientContext();
		var model = new ModelMap();
		
		service.save(ctx, bean);
		switch (action) {
		case CREATE:
		case UPDATE:
			bean = form.initEditFormBean(ctx, bean.getPartnerCode().getValue());	
			model.addAttribute(RECORD_MODEL_KEY, bean);
			break;
			
		case CHANGE_STATUS:
			bean = service.findPartner(ctx, bean.getPartnerCode().getValue());
			var beans = Collections.singletonList(bean);
			beans = form.initSearchResultBeans(ctx, beans);
			model.addAttribute(RECORD_MODEL_KEY, beans.get(0));
			break;

		default:
			break;
		}
		model.addAttribute(MESSAGES_MODEL_KEY, ctx.getMessages());
		return ResponseEntity.ok(model);
	}

}
