package com.agro360.ws.controller.common;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.agro360.bo.bean.common.AbstractCategoryBean;
import com.agro360.service.common.AbstractCategoryService;

public abstract class AbstractCategoryController<T extends AbstractCategoryBean<T>> extends AbstractController {

	protected abstract AbstractCategoryService<T> getService();

	@GetMapping()
	public ResponseEntity<T> indexAction(@RequestParam(required = false) Optional<Integer> deep) {
		return ResponseEntity.ok(getService().indexAction(getClientContext(), deep));
	}

	@GetMapping("/children/{id}")
	public ResponseEntity<List<T>> childrenAction(String id) {
		var beans = getService().childrenAction(getClientContext(), id);
		return ResponseEntity.ok(beans);
	}

	@PutMapping()
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated T bean, BindingResult br) {		
		getService().saveAction(getClientContext(), bean);
		return ResponseEntity.ok(new ModelMap());
	}

}
