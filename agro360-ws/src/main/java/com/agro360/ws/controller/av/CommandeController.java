package com.agro360.ws.controller.av;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

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
import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.bean.av.LigneTaxeBean;
import com.agro360.bo.bean.av.ReceptionLigneBean;
import com.agro360.bo.bean.av.ReglementCommandeBean;
import com.agro360.bo.bean.av.RetourLigneBean;
import com.agro360.form.av.CommandeForm;
import com.agro360.form.av.LigneForm;
import com.agro360.form.av.ReceptionLigneForm;
import com.agro360.form.av.RetourLigneForm;
import com.agro360.service.av.CommandeService;
import com.agro360.service.av.ReceptionLigneService;
import com.agro360.service.av.RetourLigneService;
import com.agro360.vd.av.CommandeTypeEnumVd;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/achat-vente/commande")
public class CommandeController extends AbstractController {

	@Autowired
	private CommandeService service;
	
	@Autowired
	private CommandeForm form;
	
	@Autowired
	private RetourLigneForm retourLigneForm;
	
	@Autowired
	private RetourLigneService retourLigneService;
	
	@Autowired
	private ReceptionLigneForm receptionLigneForm;
	
	@Autowired
	private ReceptionLigneService receptionLigneService;
	
	@Autowired
	private LigneForm ligneForm;

	@GetMapping
	public ResponseEntity<ModelMap> searchAction(
			@RequestBody(required = false) 
			@Validated Optional<CommandeSearchBean> searchBean) {
		var ctx = getClientContext();
		var sb = searchBean.orElse(new CommandeSearchBean());
		var model = new ModelMap(RECORDS_MODEL_KEY, form.initSearchResultBeans(ctx, service.search(ctx, sb)));
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
	public ResponseEntity<CommandeBean> getCreateFormAction(@RequestParam CommandeTypeEnumVd type, @RequestParam Optional<String> copyFrom) {
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
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated CommandeBean bean) {
		var action = bean.getAction();
		var ctx = getClientContext();
		var model = new ModelMap();
		
		service.save(ctx, bean);
		
		switch (action) {
		case CREATE:
		case UPDATE:
			bean = form.initEditFormBean(ctx, bean.getCommandeCode().getValue());	
			model.addAttribute(RECORD_MODEL_KEY, bean);
			break;
			
		case CHANGE_STATUS:
			bean = service.findCommande(ctx, bean.getCommandeCode().getValue());
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
	
	@PostMapping("/encaisser")
	public ResponseEntity<ModelMap> encaisserAction(@RequestBody @Validated CommandeBean bean) {
		var action = bean.getAction();
		var ctx = getClientContext();
		var model = new ModelMap();
		
		service.encaisser(ctx, bean);
		
		switch (action) {
		case CREATE:
		case UPDATE:
			bean = form.initEditFormBean(ctx, bean.getCommandeCode().getValue());	
			model.addAttribute(RECORD_MODEL_KEY, bean);
			break;
			
		default:
			break;
		}
		model.addAttribute(MESSAGES_MODEL_KEY, ctx.getMessages());
		return ResponseEntity.ok(model);
	}
	
	@GetMapping("/reglement")
	public ResponseEntity<List<ReglementCommandeBean>> getReglementsAction(
			@RequestParam(required = false) String commandeCode) {		
		var ctx = getClientContext();
		var reglements = service.findReglementsCommande(ctx, commandeCode);
		return ResponseEntity.ok(reglements);
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
	
	@PostMapping("/ligne/refresh-prix")
	public ResponseEntity<LigneBean> refreshLignePrixAction(
			@RequestBody(required = true) LigneBean ligne) {		
		var ctx = getClientContext();
		ligne = service.refreshLignePrix(ctx, ligne);
		return ResponseEntity.ok(ligne);
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
	
	@GetMapping("/ligne/retour/" + CREATE_FORM_RN)
	public ResponseEntity<ModelMap> getRetourLigneCreateFormAction(
			@RequestParam(required = false) String commandeCode, 
			@RequestParam(required = false) Long ligneId) {		
		var ctx = getClientContext();
		var form = retourLigneForm.initCreateFormBean(ctx, commandeCode, ligneId);

		var model = new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages());
		model.addAttribute(RECORD_MODEL_KEY, form);
		return ResponseEntity.ok(model);
	}
	
	@GetMapping("/ligne/retour")
	public ResponseEntity<ModelMap> getRetourLignesAction(
			@RequestParam(required = false) String commandeCode, 
			@RequestParam(required = false) Long ligneId) {		
		var ctx = getClientContext();
		final Function<RetourLigneBean, RetourLigneBean> map = e -> retourLigneForm
				.initUpdateFormBean(ctx, commandeCode, ligneId, e);
		var records = retourLigneService.findCommandeLigneRetours(ctx, commandeCode, ligneId)
				.stream()
				.map(map);

		var model = new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages());
		model.addAttribute(RECORDS_MODEL_KEY, records);
		return ResponseEntity.ok(model);
	}

	@PostMapping("/ligne/retour")
	public ResponseEntity<ModelMap> saveRetoursLigneAction(
			@RequestParam(required = false) String commandeCode, 
			@RequestParam(required = false) Long ligneId,
			@RequestBody @Validated List<RetourLigneBean> beans
			) {
		var ctx = getClientContext();
		retourLigneService.save(ctx, commandeCode, ligneId, beans);
		return ResponseEntity.ok(new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages()));
	}
	
	@GetMapping("/ligne/reception/" + CREATE_FORM_RN)
	public ResponseEntity<ModelMap> getReceptionLigneCreateFormAction(
			@RequestParam(required = false) String commandeCode, 
			@RequestParam(required = false) Long ligneId) {		
		var ctx = getClientContext();
		var form = receptionLigneForm.initCreateFormBean(ctx, commandeCode, ligneId);

		var model = new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages());
		model.addAttribute(RECORD_MODEL_KEY, form);
		return ResponseEntity.ok(model);
	}
	
	@GetMapping("/ligne/reception")
	public ResponseEntity<ModelMap> getReceptionLignesAction(
			@RequestParam(required = false) String commandeCode, 
			@RequestParam(required = false) Long ligneId) {		
		var ctx = getClientContext();
		final Function<ReceptionLigneBean, ReceptionLigneBean> map = e -> receptionLigneForm
				.initUpdateFormBean(ctx, commandeCode, ligneId, e);
		var records = receptionLigneService.findCommandeLigneReceptions(ctx, commandeCode, ligneId)
				.stream()
				.map(map);

		var model = new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages());
		model.addAttribute(RECORDS_MODEL_KEY, records);
		return ResponseEntity.ok(model);
	}

	@PostMapping("/ligne/reception")
	public ResponseEntity<ModelMap> saveReceptionsLigneAction(
			@RequestParam(required = false) String commandeCode, 
			@RequestParam(required = false) Long ligneId,
			@RequestBody @Validated List<ReceptionLigneBean> beans
			) {
		var ctx = getClientContext();
		receptionLigneService.save(ctx, commandeCode, ligneId, beans);
		return ResponseEntity.ok(new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages()));
	}
}
