package com.agro360.ws.controller.achat;

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

import com.agro360.service.bean.achat.BonCommandeBean;
import com.agro360.service.bean.achat.BonCommandeSearchBean;
import com.agro360.service.bean.achat.LigneBean;
import com.agro360.service.logic.achat.BonCommandeService;
import com.agro360.service.logic.achat.LigneService;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/achat/bon-commande")
public class BonCommandeController extends AbstractController {

	@Autowired
	private BonCommandeService bonCommandeService;

	@Autowired
	private LigneService ligneService;

	@GetMapping
	public ResponseEntity<ModelMap> searchAction(
			@RequestBody(required = false) @Validated Optional<BonCommandeSearchBean> searchBean) {
		var q = searchBean.orElse(new BonCommandeSearchBean());
		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, bonCommandeService.search(q)));
	}

	@PostMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated BonCommandeBean bean, BindingResult br) {
		return ResponseEntity.ok(new ModelMap().addAllAttributes(bonCommandeService.save(bean)));
	}
	
	@GetMapping(SEARCH_FORM_RN)
	public ResponseEntity<BonCommandeSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(bonCommandeService.initSearchFormBean());
	}
	
	@GetMapping(EDIT_FORM_RN)
	public ResponseEntity<BonCommandeBean> getEditFormAction(@RequestParam String bonCommandeCode) {
		return ResponseEntity.ok(bonCommandeService.initEditFormBean(bonCommandeCode));
	}

	@GetMapping(CREATE_FORM_RN)
	public ResponseEntity<BonCommandeBean> getCreateFormAction(@RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(bonCommandeService.initCreateFormBean(copyFrom));
	}

	@GetMapping(DELETE_FORM_RN)
	public ResponseEntity<BonCommandeBean> getDeleteFormAction(@RequestParam String bonCommandeCode) {
		return ResponseEntity.ok(bonCommandeService.initDeleteFormBean(bonCommandeCode));
	}

	@GetMapping(CHANGE_STATUS_FORM_RN)
	public ResponseEntity<BonCommandeBean> getChangeStatusFormAction(@RequestParam String bonCommandeCode) {
		return ResponseEntity.ok(bonCommandeService.initChangeStatusFormBean(bonCommandeCode));
	}
	
	@GetMapping("/ligne/" + CREATE_FORM_RN)
	public ResponseEntity<LigneBean> getLigneCreateFormAction(
			@RequestParam(required = false) String bonCommandeCode, 
			@RequestParam() Optional<String> articleCode,
			@RequestParam Optional<Long> copyFrom) {
		return ResponseEntity.ok(ligneService.initFormBean(bonCommandeCode, copyFrom, articleCode));
	}
}
