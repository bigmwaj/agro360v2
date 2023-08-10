package com.agro360.ws.controller.achat;

import java.util.List;
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

import com.agro360.service.bean.achat.BonCommandeBean;
import com.agro360.service.bean.achat.BonCommandeSearchBean;
import com.agro360.service.logic.achat.BonCommandeService;
import com.agro360.service.message.Message;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/achat/bon-commande")
public class BonCommandeController extends AbstractController {

	@Autowired
	private BonCommandeService bonCommandeService;

	@GetMapping()
	public ResponseEntity<ModelMap> searchAction(@RequestBody(required = false) @Validated Optional<BonCommandeSearchBean> searchBean) {
		return ResponseEntity.ok(new ModelMap("records", bonCommandeService.search(searchBean.orElse(new BonCommandeSearchBean()))));
	}

	@GetMapping("/{id}")
	public ResponseEntity<BonCommandeBean> loadAction(@PathVariable String id) {
		return ResponseEntity.ok(bonCommandeService.findById(id));
	}

	@PutMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated BonCommandeBean bean, BindingResult br) {
		List<Message> messages = bonCommandeService.save(bean);
		ModelMap model = new ModelMap("messages", messages);
		return ResponseEntity.ok(model);
	}
}
