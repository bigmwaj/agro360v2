package com.agro360.ws.controller.core;

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

import com.agro360.bo.bean.core.UserAccountBean;
import com.agro360.form.core.PartnerForm;
import com.agro360.form.core.UserAccountForm;
import com.agro360.service.common.ServiceValidationException;
import com.agro360.service.core.UserAccountService;
import com.agro360.vd.core.UserAccountStatusEnumVd;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/core/user-account")
public class UserAccountController extends AbstractController {
	
	@Autowired
	private UserAccountService service;
	
	@Autowired
	private UserAccountForm form;

	@Autowired
	private PartnerForm partnerForm;
	
	@GetMapping(EDIT_FORM_RN)
	public ResponseEntity<UserAccountBean> getEditFormAction(@RequestParam String partnerCode) {
		return ResponseEntity.ok(form.initEditFormBean(getClientContext(), partnerCode));
	}

	@GetMapping(CREATE_FORM_RN)
	public ResponseEntity<UserAccountBean> getCreateFormAction(@RequestParam String partnerCode) {
		return ResponseEntity.ok(form.initCreateFormBean(getClientContext(), partnerCode));
	}

	@GetMapping(CHANGE_STATUS_FORM_RN)
	public ResponseEntity<UserAccountBean> getChangeStatusFormAction(@RequestParam String partnerCode, 
			@RequestParam Optional<UserAccountStatusEnumVd> status) {
		return ResponseEntity.ok(form.initChangeStatusFormBean(getClientContext(), partnerCode, status));
	}

	@PostMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated UserAccountBean bean) {
		var ctx = getClientContext();
		var model = new ModelMap();
		var partnerCode = bean.getPartner().getPartnerCode().getValue();
		try {
			service.save(ctx, bean);
			var partnerBean = partnerForm.initEditFormBean(ctx, partnerCode);
			model.addAttribute(MESSAGES_MODEL_KEY, ctx.getMessages());
			model.addAttribute("PARTNER_RECORD", partnerBean);
			return ResponseEntity.ok(model);
		} catch (ServiceValidationException e) {
			getLogger().error(e.getLocalizedMessage(), e);
			model.addAttribute(RECORD_MODEL_KEY, bean);
			return ResponseEntity.badRequest().body(model);
		}
	}
}
