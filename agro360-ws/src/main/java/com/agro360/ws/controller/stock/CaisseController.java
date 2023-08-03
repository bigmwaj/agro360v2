package com.agro360.ws.controller.stock;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.RestController;

import com.agro360.service.bean.stock.CaisseBean;
import com.agro360.service.logic.stock.CaisseService;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/stock/caisse")
public class CaisseController extends AbstractController {

	@Autowired
	private CaisseService caisseService;

	@GetMapping()
	public ResponseEntity<List<CaisseBean>> searchAction() {
		return ResponseEntity.ok(caisseService.search());
	}

	@GetMapping("/{magasin}/{agent}/{journee}")
	public ResponseEntity<CaisseBean> loadAction(@PathVariable String magasin, @PathVariable String agent, @PathVariable LocalDate journee) {
		return ResponseEntity.ok(caisseService.findById(magasin, agent, journee));
	}

	@PutMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated CaisseBean bean, BindingResult br) {
		var messages = caisseService.save(bean);
		var model = new ModelMap("messages", messages);
		return ResponseEntity.ok(model);
	}
}
