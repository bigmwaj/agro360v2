package com.agro360.web.controller.stock;

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

import com.agro360.service.bean.stock.MagasinBean;
import com.agro360.service.logic.stock.MagasinService;
import com.agro360.web.controller.common.AbstractController;

@RestController()
@RequestMapping("/stock/magasin")
public class MagasinController extends AbstractController {

	@Autowired
	private MagasinService magasinService;

	@GetMapping()
	public ResponseEntity<List<MagasinBean>> searchAction() {
		return ResponseEntity.ok(magasinService.search());
	}

	@GetMapping("/{id}")
	public ResponseEntity<MagasinBean> loadAction(@PathVariable String id) {
		return ResponseEntity.ok(magasinService.findById(id));
	}

	@PutMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated MagasinBean bean, BindingResult br) {
		var messages = magasinService.save(bean);
		var model = new ModelMap("messages", messages);
		return ResponseEntity.ok(model);
	}
}
