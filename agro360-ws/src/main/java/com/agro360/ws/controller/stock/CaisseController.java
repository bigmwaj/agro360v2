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

import com.agro360.service.bean.stock.CaisseBean;
import com.agro360.service.bean.stock.CaisseIdBean;
import com.agro360.service.bean.stock.CaisseSearchBean;
import com.agro360.service.bean.stock.OperationCaisseBean;
import com.agro360.service.logic.stock.CaisseService;
import com.agro360.service.logic.stock.OperationCaisseService;
import com.agro360.vd.stock.TypeOperationEnumVd;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/stock/caisse")
public class CaisseController extends AbstractController {

	@Autowired
	private CaisseService caisseService;

	@Autowired
	private OperationCaisseService operationCaisseService;

	@GetMapping
	public ResponseEntity<ModelMap> searchAction(@RequestBody @Validated Optional<CaisseSearchBean> searchBean) {
		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, caisseService.search(searchBean.orElse(new CaisseSearchBean()))));
	}

	@PostMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated CaisseBean bean, BindingResult br) {
		return ResponseEntity.ok(new ModelMap().addAllAttributes(caisseService.save(bean)));
	}

	@GetMapping(SEARCH_FORM_RN)
	public ResponseEntity<CaisseSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(caisseService.initSearchFormBean());
	}
	
	@GetMapping(EDIT_FORM_RN)
	public ResponseEntity<CaisseBean> getEditFormAction(@RequestBody @Validated CaisseIdBean idBean) {
		return ResponseEntity.ok(caisseService.initEditFormBean(idBean));
	}

	@GetMapping(CREATE_FORM_RN)
	public ResponseEntity<CaisseBean> getCreateFormAction(
			@RequestBody(required = false) Optional<CaisseIdBean> copyFrom) {
		return ResponseEntity.ok(caisseService.initCreateFormBean(copyFrom));
	}

	@GetMapping(DELETE_FORM_RN)
	public ResponseEntity<CaisseBean> getDeleteFormAction(@RequestBody CaisseIdBean idBean) {
		return ResponseEntity.ok(caisseService.initDeleteFormBean(idBean));
	}

	@GetMapping(CHANGE_STATUS_FORM_RN)
	public ResponseEntity<CaisseBean> getChangeStatusFormAction(@RequestBody CaisseIdBean idBean) {
		return ResponseEntity.ok(caisseService.initChangeStatusFormBean(idBean));
	}
	
	@GetMapping("/operation/create-form")
	public ResponseEntity<OperationCaisseBean> getOperationCreateFormAction(
			@RequestParam() Optional<String> articleCode,
			@RequestParam() Optional<TypeOperationEnumVd> typeOperation,
			@RequestBody CaisseIdBean idBean, 
			@RequestParam Optional<Long> copyFrom) {
		return ResponseEntity.ok(operationCaisseService.initCreateFormBean(idBean, copyFrom, articleCode, typeOperation));
	}
}
