package com.agro360.ws.controller.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agro360.bo.bean.core.CategoryBean;
import com.agro360.service.core.CategoryService;
import com.agro360.ws.controller.common.AbstractCategoryController;

@RestController("core/CategoryController")
@RequestMapping("/core/category")
public class CategoryController extends AbstractCategoryController<CategoryBean> {

	@Autowired
	private CategoryService service;
	
	@Override
	protected CategoryService getService() {
		return service;
	}

}
