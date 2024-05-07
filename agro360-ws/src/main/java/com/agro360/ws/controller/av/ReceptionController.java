package com.agro360.ws.controller.av;

import java.util.List;

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

import com.agro360.bo.bean.av.ReceptionLigneBean;
import com.agro360.form.av.ReceptionLigneForm;
import com.agro360.service.av.ReceptionLigneService;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/achat-vente/commande/reception")
public class ReceptionController extends AbstractController {
	
	@Autowired
	private ReceptionLigneForm form;
	
	@Autowired
	private ReceptionLigneService service;
	
	@GetMapping(CREATE_FORM_RN)
	public ResponseEntity<ModelMap> getCreateFormAction(
			@RequestParam(required = false) String commandeCode) {		
		var ctx = getClientContext();
		var form = this.form.initCreateFormBean(ctx, commandeCode);

		var model = new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages());
		model.addAttribute(RECORD_MODEL_KEY, form);
		return ResponseEntity.ok(model);
	}
	
	@GetMapping()
	public ResponseEntity<ModelMap> getReceptionsAction(
			@RequestParam(required = false) String commandeCode) {		
		var ctx = getClientContext();
		var records = service.findCommandeReceptions(ctx, commandeCode);
		records = form.initUpdateFormBean(ctx, commandeCode, records);
		
		var model = new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages());
		model.addAttribute(RECORDS_MODEL_KEY, records);
		return ResponseEntity.ok(model);
	}

	@GetMapping("/ligne")
	public ResponseEntity<ModelMap> getLigneAction(
			@RequestParam(required = false) String commandeCode,
			@RequestParam(required = false) Long ligneId) {		
		var ctx = getClientContext();
		var ligne = form.initLigne(ctx, commandeCode, ligneId);
		var model = new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages());
		model.addAttribute(RECORD_MODEL_KEY, ligne);
		return ResponseEntity.ok(model);
	}

	@PostMapping()
	public ResponseEntity<ModelMap> saveAction(
			@RequestParam(required = false) String commandeCode, 
			@RequestBody @Validated List<ReceptionLigneBean> beans
			) {
		var ctx = getClientContext();
		service.save(ctx, commandeCode, beans);
		return ResponseEntity.ok(new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages()));
	}
}
