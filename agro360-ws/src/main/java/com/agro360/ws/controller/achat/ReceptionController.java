package com.agro360.ws.controller.achat;

import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agro360.service.bean.achat.ReceptionBean;
import com.agro360.service.logic.achat.ReceptionService;
import com.agro360.service.utils.Message;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/achat/reception/{bonCommandeCode}")
public class ReceptionController extends AbstractController {

	@Autowired
	private ReceptionService receptionService;

	@GetMapping()
	public ResponseEntity<List<ReceptionBean>> searchAction(@PathVariable String bonCommandeCode) {
		return ResponseEntity.ok(receptionService.search(bonCommandeCode));
	}

	@GetMapping("/form")
	public ResponseEntity<Object> searchAction(@PathVariable String bonCommandeCode, @RequestParam String formModel) {
		Object model = new Object();
		switch (formModel) {
		case "lignes-non-receptionnees":
			model = receptionService.rechercherLignesNonReceptionneesEtMapperEnReceptions(bonCommandeCode);
			break;

		default:
			break;
		}
		return ResponseEntity.ok(model);
	}

	@PutMapping()
	public ResponseEntity<ModelMap> saveAction(@PathVariable String bonCommandeCode,
			@RequestBody @Validated List<ReceptionBean> beans, BindingResult br) {
		List<Message> messages = receptionService.save(beans);
		ModelMap model = new ModelMap("messages", messages);
		return ResponseEntity.ok(model);
	}
}
