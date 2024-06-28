package com.agro360.ws.controller.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agro360.bo.bean.stock.CategoryBean;
import com.agro360.service.stock.CategoryService;
import com.agro360.ws.controller.common.AbstractCategoryController;

@RestController("stock/CategoryController")
@RequestMapping("/stock/category")
public class CategoryController extends AbstractCategoryController<CategoryBean> {

	@Autowired
	private CategoryService service;
	
	@Override
	protected CategoryService getService() {
		return service;
	}

}
