package com.agro360.ws.controller.vente;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agro360.service.bean.vente.CommandeBean;
import com.agro360.service.bean.vente.CommandeSearchBean;
import com.agro360.service.logic.vente.CommandeService;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/vente/commande")
public class CommandeController extends AbstractController {

	@Autowired
	private CommandeService commandeService;

	@GetMapping()
	public ResponseEntity<ModelMap> searchAction(@RequestBody @Validated Optional<CommandeSearchBean> searchBean) {
		return ResponseEntity.ok(new ModelMap("records", commandeService.search(searchBean.orElse(new CommandeSearchBean()))));
	}

	@GetMapping("/{id}")
	public ResponseEntity<CommandeBean> loadAction(@PathVariable String id) {
		return ResponseEntity.ok(commandeService.findById(id));
	}

	@PutMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated CommandeBean bean, BindingResult br) {
		var messages = commandeService.save(bean);
		var model = new ModelMap("messages", messages);
		return ResponseEntity.ok(model);
	}
}
