package com.agro360.ws.controller.stock;

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

import com.agro360.service.bean.stock.ArticleBean;
import com.agro360.service.bean.stock.ArticleSearchBean;
import com.agro360.service.logic.stock.ArticleService;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/stock/article")
public class ArticleController extends AbstractController {

	@Autowired
	private ArticleService articleService;

	@GetMapping()
	public ResponseEntity<List<ArticleBean>> searchAction(@RequestBody @Validated ArticleSearchBean searchBean) {
		return ResponseEntity.ok(articleService.search(searchBean));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ArticleBean> loadAction(@PathVariable String id) {
		return ResponseEntity.ok(articleService.findById(id));
	}

	@PutMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated ArticleBean bean, BindingResult br) {
		var messages = articleService.save(bean);
		var model = new ModelMap("messages", messages);
		return ResponseEntity.ok(model);
	}
}
