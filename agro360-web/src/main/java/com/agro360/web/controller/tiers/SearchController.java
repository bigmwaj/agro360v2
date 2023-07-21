package com.agro360.web.controller.tiers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agro360.service.bean.tiers.TiersBean;
import com.agro360.service.bean.tiers.TiersSearchBean;
import com.agro360.service.logic.tiers.TiersService;
import com.agro360.web.controller.common.AbstractController;

@RestController()
@RequestMapping("/tiers/search")
public class SearchController extends AbstractController {

	@Autowired
	private TiersService tiersService;

	@PostMapping()
	public ResponseEntity<List<TiersBean>> searchAction(@RequestBody @Validated TiersSearchBean searchForm,
			BindingResult br) {
		return ResponseEntity.ok(tiersService.search(searchForm));
	}

}
