package com.agro360.ws.controller.av;

import java.util.List;
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

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.CommandeSearchBean;
import com.agro360.bo.bean.av.LigneTaxeBean;
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
		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, service.search(getClientContext(), searchBean)));
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
	public ResponseEntity<CommandeBean> getCreateFormAction(@RequestParam Optional<String> aliasVariant) {
		return ResponseEntity.ok(form.initCreateFormBean(getClientContext(), aliasVariant));
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
	public ResponseEntity<ModelMap> getLigneCreateFormAction(
			@RequestParam(required = false) String commandeCode, 
			@RequestParam() Optional<String> magasinCode,
			@RequestParam() Optional<String> alias) {		
		var ctx = getClientContext();
		var form = ligneForm.initCreateFormBean(ctx, commandeCode, magasinCode, alias);
		var model = new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages());
		model.addAttribute(RECORD_MODEL_KEY, form);
		return ResponseEntity.ok(model);
	}
	
	@GetMapping("/ligne/taxe")
	public ResponseEntity<List<LigneTaxeBean>> getLigneTaxesAction(
			@RequestParam(required = false) String commandeCode, 
			@RequestParam() Optional<Long> ligneId,
			@RequestParam() String articleCode) {		
		var ctx = getClientContext();
		var taxes = service.findLigneTaxes(ctx, commandeCode, ligneId, articleCode);
		return ResponseEntity.ok(taxes);
	}
	
	@PostMapping()
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated CommandeBean bean) {
		var ctx = getClientContext();
		service.save(ctx, bean);
		return ResponseEntity.ok(new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages()));
	}

}
