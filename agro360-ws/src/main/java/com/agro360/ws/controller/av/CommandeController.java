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
import com.agro360.bo.bean.av.PaiementBean;
import com.agro360.bo.bean.av.ReglementBean;
import com.agro360.bo.bean.stock.VariantBean;
import com.agro360.form.av.CommandeForm;
import com.agro360.operation.context.ClientContext;
import com.agro360.service.av.CommandeService;
import com.agro360.service.stock.ArticleService;
import com.agro360.vd.av.CommandeTypeEnumVd;
import com.agro360.vd.common.ClientOperationEnumVd;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/achat-vente/commande")
public class CommandeController extends AbstractController {

	@Autowired
	private CommandeService service;
	
	@Autowired
	private CommandeForm form;
	
	@Autowired
	private ArticleService articleService;

	@GetMapping
	public ResponseEntity<ModelMap> searchAction(
			@RequestBody(required = false) 
			@Validated Optional<CommandeSearchBean> searchBean) {
		var ctx = getClientContext();
		var sb = searchBean.orElse(new CommandeSearchBean());
		var result = service.search(ctx, sb);
		var model = new ModelMap(RECORDS_MODEL_KEY, form.initSearchResultBeans(ctx, result));
		model.addAttribute(RECORDS_TOTAL_KEY, sb.getLength());		
		return ResponseEntity.ok(model);
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
	public ResponseEntity<CommandeBean> getCreateFormAction(@RequestParam CommandeTypeEnumVd type, 
			@RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(form.initCreateFormBean(getClientContext(), type, copyFrom));
	}

	@GetMapping(DELETE_FORM_RN)
	public ResponseEntity<CommandeBean> getDeleteFormAction(@RequestParam String commandeCode) {
		return ResponseEntity.ok(form.initDeleteFormBean(getClientContext(), commandeCode));
	}

	@GetMapping(CHANGE_STATUS_FORM_RN)
	public ResponseEntity<CommandeBean> getChangeStatusFormAction(@RequestParam String commandeCode) {
		return ResponseEntity.ok(form.initChangeStatusFormBean(getClientContext(), commandeCode));
	}
	
	@PostMapping()
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated CommandeBean bean, @RequestParam Optional<Boolean> processPaiement) {
		var ctx = getClientContext();
		
		service.save(ctx, bean);
		
		var commandeCode = bean.getCommandeCode().getValue();
		if( processPaiement.isPresent() && processPaiement.get() ) {
			return initPaiement(ctx, commandeCode);
		}
		
		var action = bean.getAction();
		var model = new ModelMap();
		
		if( !ClientOperationEnumVd.DELETE.equals(action)) {
			bean = form.initEditFormBean(ctx, bean.getCommandeCode().getValue());	
			model.addAttribute(RECORD_MODEL_KEY, bean);
		}
		
		model.addAttribute(MESSAGES_MODEL_KEY, ctx.getMessages());
		return ResponseEntity.ok(model);
	}
	
	@GetMapping("/init-paiement")
	public ResponseEntity<ModelMap> initPaiementAction(@RequestParam String commandeCode) {
		return initPaiement(getClientContext(), commandeCode);
	}
	
	private ResponseEntity<ModelMap> initPaiement( ClientContext ctx, String commandeCode) {
		var model = new ModelMap();
		var paiements = form.initPaiementsFormBean(ctx, commandeCode);
		var bean = form.initEditFormBean(ctx, commandeCode);	
		model.addAttribute(RECORD_MODEL_KEY, bean);
		model.addAttribute(RECORDS_MODEL_KEY, paiements);
		model.addAttribute(MESSAGES_MODEL_KEY, ctx.getMessages());
		return ResponseEntity.ok(model);
	}
	
	@PostMapping("/encaisser")
	public ResponseEntity<ModelMap> encaisserAction(@RequestParam String commandeCode,
			@RequestBody @Validated List<PaiementBean> paiements) {
		var ctx = getClientContext();
		var model = new ModelMap();
		
		service.reglerCommande(ctx, commandeCode, paiements);
		var commande = form.initEditFormBean(ctx, commandeCode);	
		model.addAttribute(RECORD_MODEL_KEY, commande);
		model.addAttribute(MESSAGES_MODEL_KEY, ctx.getMessages());
		return ResponseEntity.ok(model);
	}
	
	@GetMapping("/reglement")
	public ResponseEntity<List<ReglementBean>> getReglementsAction(@RequestParam String commandeCode) {		
		var ctx = getClientContext();
		var reglements = service.findReglements(ctx, commandeCode);
		return ResponseEntity.ok(reglements);
	}
	
	@GetMapping("/variants")
	public ResponseEntity<List<VariantBean>> getLigneVariantsAction(@RequestParam String query) {		
		var ctx = getClientContext();
		var options = articleService.getVariantsByQuery(ctx, query);
		return ResponseEntity.ok(options);
	}
	
}
