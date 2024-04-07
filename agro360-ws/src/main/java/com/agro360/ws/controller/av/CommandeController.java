package com.agro360.ws.controller.av;

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

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.CommandeSearchBean;
import com.agro360.bo.bean.av.LigneBean;
import com.agro360.form.av.CommandeForm;
import com.agro360.form.av.LigneForm;
import com.agro360.service.av.CommandeService;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/achat-vente/commande")
public class CommandeController extends AbstractController {

	@Autowired
	private CommandeService service;
	
	@Autowired
	private CommandeForm form;
	
	@Autowired
	private LigneForm ligneForm;

	@GetMapping
	public ResponseEntity<ModelMap> searchAction(
			@RequestBody(required = false) @Validated Optional<CommandeSearchBean> searchBean) {
		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, service.searchAction(getClientContext(), searchBean)));
	}
	
	@GetMapping(SEARCH_FORM_RN)
	public ResponseEntity<CommandeSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(form.initSearchFormBean(getClientContext()));
	}
	
	@GetMapping(EDIT_FORM_RN)
	public ResponseEntity<CommandeBean> getEditFormAction(@RequestParam String commandeCode) {
		return ResponseEntity.ok(form.initEditFormBean(getClientContext(), commandeCode));
	}

	@GetMapping(CREATE_FORM_RN)
	public ResponseEntity<CommandeBean> getCreateFormAction(@RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(form.initCreateFormBean(getClientContext(), copyFrom));
	}

	@GetMapping(DELETE_FORM_RN)
	public ResponseEntity<CommandeBean> getDeleteFormAction(@RequestParam String commandeCode) {
		return ResponseEntity.ok(form.initDeleteFormBean(getClientContext(), commandeCode));
	}

	@GetMapping(CHANGE_STATUS_FORM_RN)
	public ResponseEntity<CommandeBean> getChangeStatusFormAction(@RequestParam String commandeCode) {
		return ResponseEntity.ok(form.initChangeStatusFormBean(getClientContext(), commandeCode));
	}
	
	@GetMapping("/ligne/" + CREATE_FORM_RN)
	public ResponseEntity<LigneBean> getLigneCreateFormAction(
			@RequestParam(required = false) String commandeCode, 
			@RequestParam() Optional<String> articleCode,
			@RequestParam Optional<Long> copyFrom) {
		return ResponseEntity.ok(ligneForm.initCreateFormBean(getClientContext(), commandeCode, copyFrom, articleCode));
	}
	
	@PostMapping()
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated CommandeBean bean, BindingResult br) {
		service.saveAction(getClientContext(), bean);
		return ResponseEntity.ok(new ModelMap());
	}

}
