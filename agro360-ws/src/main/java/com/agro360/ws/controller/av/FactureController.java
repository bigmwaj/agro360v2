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

import com.agro360.bo.bean.av.FactureBean;
import com.agro360.bo.bean.av.FactureSearchBean;
import com.agro360.bo.bean.av.PaiementBean;
import com.agro360.bo.bean.av.ReglementBean;
import com.agro360.form.av.FactureForm;
import com.agro360.operation.context.ClientContext;
import com.agro360.service.av.FactureService;
import com.agro360.vd.av.FactureTypeEnumVd;
import com.agro360.vd.common.ClientOperationEnumVd;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/achat-vente/facture")
public class FactureController extends AbstractController {

	@Autowired
	private FactureService service;
	
	@Autowired
	private FactureForm form;

	@GetMapping
	public ResponseEntity<ModelMap> searchAction(
			@RequestBody(required = false) @Validated Optional<FactureSearchBean> searchBean) {
		var sb = searchBean.orElse(new FactureSearchBean());
		var model = new ModelMap(RECORDS_MODEL_KEY, service.search(getClientContext(), sb));
		model.addAttribute(RECORDS_TOTAL_KEY, sb.getLength());		
		return ResponseEntity.ok(model);
	}
	
	@GetMapping(SEARCH_FORM_RN)
	public ResponseEntity<FactureSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(form.initSearchFormBean(getClientContext()));
	}
	
	@GetMapping(EDIT_FORM_RN)
	public ResponseEntity<FactureBean> getEditFormAction(@RequestParam String factureCode) {
		return ResponseEntity.ok(form.initEditFormBean(getClientContext(), factureCode));
	}

	@GetMapping(CREATE_FORM_RN)
	public ResponseEntity<FactureBean> getCreateFormAction(@RequestParam FactureTypeEnumVd type, @RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(form.initCreateFormBean(getClientContext(), type, copyFrom));
	}

	@GetMapping(DELETE_FORM_RN)
	public ResponseEntity<FactureBean> getDeleteFormAction(@RequestParam String factureCode) {
		return ResponseEntity.ok(form.initDeleteFormBean(getClientContext(), factureCode));
	}

	@GetMapping(CHANGE_STATUS_FORM_RN)
	public ResponseEntity<FactureBean> getChangeStatusFormAction(@RequestParam String factureCode) {
		return ResponseEntity.ok(form.initChangeStatusFormBean(getClientContext(), factureCode));
	}
	
	@PostMapping()
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated FactureBean bean, @RequestParam Optional<Boolean> processPaiement) {
		var ctx = getClientContext();
		
		service.save(ctx, bean);
		
		var commandeCode = bean.getFactureCode().getValue();
		if( processPaiement.isPresent() && processPaiement.get() ) {
			return initPaiementAction(ctx, commandeCode);
		}
		
		var action = bean.getAction();
		var model = new ModelMap();
		if( !ClientOperationEnumVd.DELETE.equals(action)) {
			bean = form.initEditFormBean(ctx, bean.getFactureCode().getValue());	
			model.addAttribute(RECORD_MODEL_KEY, bean);
		}
		model.addAttribute(MESSAGES_MODEL_KEY, ctx.getMessages());
		return ResponseEntity.ok(model);
	}
	
	@GetMapping("/init-paiement")
	public ResponseEntity<ModelMap> initPaiementAction(
			@RequestParam(required = true) String factureCode) {
		return initPaiementAction(getClientContext(), factureCode);
	}

	private ResponseEntity<ModelMap> initPaiementAction( ClientContext ctx, String factureCode) {
		var model = new ModelMap();
		var paiements = form.initPaiementsFormBean(ctx, factureCode);
		var bean = form.initEditFormBean(ctx, factureCode);	
		model.addAttribute(RECORD_MODEL_KEY, bean);
		model.addAttribute(RECORDS_MODEL_KEY, paiements);
		model.addAttribute(MESSAGES_MODEL_KEY, ctx.getMessages());
		return ResponseEntity.ok(model);
	}
	
	@PostMapping("/encaisser")
	public ResponseEntity<ModelMap> encaisserAction(
			@RequestParam(required = true) String factureCode,
			@RequestBody @Validated List<PaiementBean> paiements) {
		var ctx = getClientContext();
		var model = new ModelMap();
		
		service.reglerFacture(ctx, factureCode, paiements);
		var facture = form.initEditFormBean(ctx, factureCode);	
		model.addAttribute(RECORD_MODEL_KEY, facture);
		model.addAttribute(MESSAGES_MODEL_KEY, ctx.getMessages());
		return ResponseEntity.ok(model);
	}
	
	@GetMapping("/reglement")
	public ResponseEntity<List<ReglementBean>> getReglementsAction(
			@RequestParam(required = true) String factureCode) {		
		var ctx = getClientContext();
		var reglements = service.findReglements(ctx, factureCode);
		return ResponseEntity.ok(reglements);
	}
}
