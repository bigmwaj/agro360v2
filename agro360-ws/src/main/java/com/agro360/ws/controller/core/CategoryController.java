package com.agro360.ws.controller.core;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agro360.bo.bean.core.CategoryBean;
import com.agro360.service.core.CategoryService;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/core/category")
public class CategoryController extends AbstractController {

	@Autowired
	private CategoryService service;

	@GetMapping()
	public ResponseEntity<CategoryBean> indexAction(@RequestParam(required = false) Optional<Integer> deep) {
		return ResponseEntity.ok(service.indexAction(getClientContext(), deep));
	}

	@GetMapping("/children/{id}")
	public ResponseEntity<List<CategoryBean>> childrenAction(String id) {
		var beans = service.childrenAction(getClientContext(), id);
		return ResponseEntity.ok(beans);
	}

	@PutMapping()
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated CategoryBean bean, BindingResult br) {		
		service.saveAction(getClientContext(), bean);

		return ResponseEntity.ok(new ModelMap());
	}

}
