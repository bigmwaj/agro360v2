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

import com.agro360.service.bean.stock.UniteBean;
import com.agro360.service.logic.stock.UniteService;
import com.agro360.service.utils.Message;
import com.agro360.web.controller.common.AbstractController;

@RestController()
@RequestMapping("/stock/unite")
public class UniteController extends AbstractController {

	@Autowired
	private UniteService uniteService;

	@GetMapping()
	public ResponseEntity<List<UniteBean>> searchAction() {
		return ResponseEntity.ok(uniteService.search());
	}

	@GetMapping("/{id}")
	public ResponseEntity<UniteBean> loadAction(@PathVariable String id) {
		return ResponseEntity.ok(uniteService.findById(id));
	}

	@PutMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated UniteBean bean, BindingResult br) {
		List<Message> messages = uniteService.save(bean);
		ModelMap model = new ModelMap("messages", messages);
		return ResponseEntity.ok(model);
	}
}
