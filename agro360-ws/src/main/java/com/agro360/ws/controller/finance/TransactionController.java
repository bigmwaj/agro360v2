package com.agro360.ws.controller.finance;

import java.time.LocalDate;
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

import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.bo.bean.finance.TransactionSearchBean;
import com.agro360.bo.bean.finance.TransfertBean;
import com.agro360.form.finance.TransactionForm;
import com.agro360.service.finance.TransactionService;
import com.agro360.vd.finance.TransactionTypeEnumVd;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/finance/transaction")
public class TransactionController extends AbstractController {

	@Autowired
	private TransactionService service;
	
	@Autowired
	private TransactionForm form;

	@GetMapping()
	public ResponseEntity<ModelMap> searchAction(
			@RequestBody @Validated Optional<TransactionSearchBean> searchBean) {	
		var ctx = getClientContext();
		var sb = searchBean.orElse(new TransactionSearchBean());
		var result = service.search(ctx, sb);
		var model = new ModelMap(RECORDS_MODEL_KEY, form.initSearchResultBeans(ctx, result));
		model.addAttribute(RECORDS_TOTAL_KEY, sb.getLength());		
		return ResponseEntity.ok(model);
	}
	
	@GetMapping("/search-form")
	public ResponseEntity<TransactionSearchBean> getSearchFormAction(
			@RequestParam Optional<TransactionTypeEnumVd> type) {
		return ResponseEntity.ok(form.initSearchFormBean(getClientContext(), type));
	}
	
	@GetMapping("/edit-form")
	public ResponseEntity<TransactionBean> getEditFormAction(@RequestParam String transactionCode) {
		return ResponseEntity.ok(form.initEditFormBean(getClientContext(), transactionCode));
	}

	@GetMapping("/create-form")
	public ResponseEntity<TransactionBean> getCreateFormAction(@RequestParam TransactionTypeEnumVd type, @RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(form.initCreateFormBean(getClientContext(), type, copyFrom));
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
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated TransactionBean bean) {
		var action = bean.getAction();
		var ctx = getClientContext();
		var model = new ModelMap();
		
		service.save(ctx, bean);
		switch (action) {
		case CREATE:
		case UPDATE:
			bean = form.initEditFormBean(ctx, bean.getTransactionCode().getValue());	
			model.addAttribute(RECORD_MODEL_KEY, bean);
			break;
			
		case CHANGE_STATUS:
			bean = service.findTransaction(ctx, bean.getTransactionCode().getValue());
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
	
	@PostMapping("transfert")
	public ResponseEntity<ModelMap> transfertAction(@RequestBody @Validated TransfertBean bean) {
		var ctx = getClientContext();
		var result = service.transfert(ctx, bean);
		var model = new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages());

		model.addAttribute(RECORDS_MODEL_KEY, result);
		return ResponseEntity.ok(model);
	}

	@GetMapping("/generate-etat-recette-depense")
	public ResponseEntity<ModelMap> generateEtatRecetteDepense() {
		var etat = service.genererEtatRecetteDepense(getClientContext(), LocalDate.now());
		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, etat));
	}
}
