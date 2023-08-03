package com.agro360.ws.controller.tiers;

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

import com.agro360.service.bean.tiers.CategoryBean;
import com.agro360.service.logic.tiers.CategoryService;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/tiers/category")
public class CategoryController extends AbstractController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping()
	public ResponseEntity<CategoryBean> indexAction(@RequestParam(required = false) Optional<Integer> deep) {
		return ResponseEntity.ok(categoryService.findRootCategory(deep));
	}

	@GetMapping("/children/{id}")
	public ResponseEntity<List<CategoryBean>> childrenAction(String id) {
		var beans = categoryService.findChildrenCategory(id);
		return ResponseEntity.ok(beans);
	}

	@PutMapping("/edit")
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated CategoryBean bean, BindingResult br) {
		var messages = categoryService.save(bean);
		var model = new ModelMap("messages", messages);
		return ResponseEntity.ok(model);
	}

}
