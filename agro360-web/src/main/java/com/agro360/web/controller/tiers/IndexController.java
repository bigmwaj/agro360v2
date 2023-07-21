package com.agro360.web.controller.tiers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agro360.web.bean.tiers.IndexBean;
import com.agro360.web.controller.common.AbstractController;
import com.agro360.web.mapper.tiers.TiersIndexMapper;

@RestController()
@RequestMapping("/tiers/index")
public class IndexController extends AbstractController {

	@Autowired
	private TiersIndexMapper indexBinding;

	@GetMapping
	public ResponseEntity<IndexBean> indexAction() {
		return ResponseEntity.ok(indexBinding.init());
	}

}
