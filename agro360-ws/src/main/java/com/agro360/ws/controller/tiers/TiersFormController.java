package com.agro360.ws.controller.tiers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.agro360.service.bean.tiers.TiersBean;
import com.agro360.service.bean.tiers.TiersSearchBean;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/tiers/tiers-form")
public class TiersFormController extends AbstractController {

	@GetMapping("/{formModelName}")
	public ResponseEntity<Object> getFormAction(@PathVariable String formModelName) {
		Object model = null;
		switch (formModelName) {
		case "search-tiers-form":
			model = new TiersSearchBean();
			break;

		case "create-tiers-form":
			model = new TiersBean();
			break;

		default:
			break;
		}
		return ResponseEntity.ok(model);
	}

}
