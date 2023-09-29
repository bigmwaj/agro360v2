package com.agro360.ws.controller.vente;

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

import com.agro360.service.bean.vente.CommandeBean;
import com.agro360.service.bean.vente.CommandeSearchBean;
import com.agro360.service.bean.vente.LigneBean;
import com.agro360.service.logic.vente.CommandeService;
import com.agro360.service.logic.vente.LigneService;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/vente/commande")
public class CommandeController extends AbstractController {

	@Autowired
	private CommandeService commandeService;
	
	@Autowired
	private LigneService ligneService;

	@GetMapping()
	public ResponseEntity<ModelMap> searchAction(@RequestBody @Validated Optional<CommandeSearchBean> searchBean) {
		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, commandeService.search(searchBean.orElse(new CommandeSearchBean()))));
	}

	@PostMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated CommandeBean bean, BindingResult br) {
		return ResponseEntity.ok(new ModelMap().addAllAttributes(commandeService.save(bean)));
	}
	
	@GetMapping(SEARCH_FORM_RN)
	public ResponseEntity<CommandeSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(commandeService.initSearchFormBean());
	}
	
	@GetMapping(EDIT_FORM_RN)
	public ResponseEntity<CommandeBean> getEditFormAction(@RequestParam String commandeCode) {
		return ResponseEntity.ok(commandeService.initEditFormBean(commandeCode));
	}

	@GetMapping(CREATE_FORM_RN)
	public ResponseEntity<CommandeBean> getCreateFormAction(@RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(commandeService.initCreateFormBean(copyFrom));
	}

	@GetMapping(DELETE_FORM_RN)
	public ResponseEntity<CommandeBean> getDeleteFormAction(@RequestParam String commandeCode) {
		return ResponseEntity.ok(commandeService.initDeleteFormBean(commandeCode));
	}

	@GetMapping(CHANGE_STATUS_FORM_RN)
	public ResponseEntity<CommandeBean> getChangeStatusFormAction(@RequestParam String commandeCode) {
		return ResponseEntity.ok(commandeService.initChangeStatusFormBean(commandeCode));
	}
	
	@GetMapping("/ligne/create-form")
	public ResponseEntity<LigneBean> getLigneCreateFormAction(
			@RequestParam() Optional<String> articleCode,
			@RequestParam(required = false) String commandeCode, 
			@RequestParam Optional<Long> copyFrom) {
		return ResponseEntity.ok(ligneService.initCreateFormBean(commandeCode, copyFrom, articleCode));
	}
}
