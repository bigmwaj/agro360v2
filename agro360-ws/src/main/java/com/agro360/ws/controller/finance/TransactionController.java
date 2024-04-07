package com.agro360.ws.controller.finance;

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

import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.bo.bean.finance.TransactionSearchBean;
import com.agro360.bo.bean.finance.TransfertBean;
import com.agro360.form.finance.TransactionForm;
import com.agro360.operation.context.ClientContext;
import com.agro360.service.finance.TransactionService;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/finance/transaction")
public class TransactionController extends AbstractController {

	@Autowired
	TransactionService service;
	
	@Autowired
	TransactionForm form;

	@GetMapping()
	public ResponseEntity<ModelMap> searchAction(
			@RequestBody 
			@Validated Optional<TransactionSearchBean> searchForm, 
			BindingResult br) {	
		var ctx = new ClientContext();
		var records = service.searchAction(ctx, searchForm);
		var model = new ModelMap(RECORDS_MODEL_KEY, records);	
		return ResponseEntity.ok(model);
	}
	
	@GetMapping("/search-form")
	public ResponseEntity<TransactionSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(form.initSearchFormBean(getClientContext()));
	}
	
	@GetMapping("/edit-form")
	public ResponseEntity<TransactionBean> getEditFormAction(@RequestParam String transactionCode) {
		return ResponseEntity.ok(form.initEditFormBean(getClientContext(), transactionCode));
	}

	@GetMapping("/create-form")
	public ResponseEntity<TransactionBean> getCreateFormAction(@RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(form.initCreateFormBean(getClientContext(), copyFrom));
	}

	@GetMapping("/delete-form")
	public ResponseEntity<TransactionBean> getDeleteFormAction(@RequestParam String transactionCode) {
		System.out.println("TransactionController.getDeleteFormAction() " + transactionCode);
		return ResponseEntity.ok(form.initDeleteFormBean(getClientContext(), transactionCode));
	}

	@GetMapping("/change-status-form")
	public ResponseEntity<TransactionBean> getChangeStatusFormAction(@RequestParam String transactionCode) {
		return ResponseEntity.ok(form.initChangeStatusFormBean(getClientContext(), transactionCode));
	}

	@GetMapping("/transfert-form")
	public ResponseEntity<TransfertBean> getTransfertFormAction() {
		return ResponseEntity.ok(form.initTransfertFormBean(getClientContext()));
	}
	
	@PostMapping()
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated TransactionBean bean, BindingResult br) {
		service.saveAction(getClientContext(), bean);
		return ResponseEntity.ok(new ModelMap());
	}
	
	@PostMapping("transfert")
	public ResponseEntity<ModelMap> transfertAction(@RequestBody @Validated TransfertBean bean, BindingResult br) {
		service.transfert(getClientContext(), bean);
		return ResponseEntity.ok(new ModelMap());
	}

}
