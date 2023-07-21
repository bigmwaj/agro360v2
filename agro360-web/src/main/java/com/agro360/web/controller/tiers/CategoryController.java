package com.agro360.web.controller.tiers;

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

import com.agro360.service.bean.tiers.CategoryHierarchieBean;
import com.agro360.service.logic.tiers.CategoryService;
import com.agro360.service.utils.Message;
import com.agro360.web.controller.common.AbstractController;

@RestController()
@RequestMapping("/tiers/category")
public class CategoryController extends AbstractController {

	@Autowired
	private CategoryService categoryService;

	@GetMapping()
	public ResponseEntity<CategoryHierarchieBean> indexAction(@RequestParam(required = false) Optional<Integer> deep) {
		System.out.println(deep);
		return ResponseEntity.ok(categoryService.loadRootCategory(deep));
	}

	@GetMapping("/children/{id}")
	public ResponseEntity<List<CategoryHierarchieBean>> childrenAction(String id) {
		List<CategoryHierarchieBean> beans = categoryService.loadChildrenCategory(id);
		return ResponseEntity.ok(beans);
	}

	@PutMapping("/edit")
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated CategoryHierarchieBean bean, BindingResult br) {
		List<Message> messages = categoryService.save(bean);
		ModelMap model = new ModelMap("messages", messages);
		return ResponseEntity.ok(model);
	}

}
