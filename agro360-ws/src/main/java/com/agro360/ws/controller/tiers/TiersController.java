package com.agro360.ws.controller.tiers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agro360.service.bean.tiers.TiersBean;
import com.agro360.service.bean.tiers.TiersSearchBean;
import com.agro360.service.logic.tiers.TiersService;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/tiers/tiers")
public class TiersController extends AbstractController {

	@Autowired
	private TiersService tiersService;

	@GetMapping()
	public ResponseEntity<ModelMap> searchAction(@RequestBody @Validated Optional<TiersSearchBean> searchForm, BindingResult br) {		
		var records = tiersService.search(searchForm.orElse(new TiersSearchBean()));
		var model = new ModelMap(RECORDS_MODEL_KEY, records);	
		return ResponseEntity.ok(model);
	}

	@PostMapping()
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated TiersBean bean, BindingResult br) {
		return ResponseEntity.ok(new ModelMap().addAllAttributes(tiersService.save(bean)));
	}
	
	@GetMapping("/search-form")
	public ResponseEntity<TiersSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(tiersService.initSearchFormBean());
	}
	
	@GetMapping("/edit-form")
	public ResponseEntity<TiersBean> getEditFormAction(@RequestParam String tiersCode) {
		return ResponseEntity.ok(tiersService.initEditFormBean(tiersCode));
	}

	@GetMapping("/create-form")
	public ResponseEntity<TiersBean> getCreateFormAction(@RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(tiersService.initCreateFormBean(copyFrom));
	}

	@GetMapping("/delete-form")
	public ResponseEntity<TiersBean> getDeleteFormAction(@RequestParam String tiersCode) {
		return ResponseEntity.ok(tiersService.initDeleteFormBean(tiersCode));
	}

	@GetMapping("/change-status-form")
	public ResponseEntity<TiersBean> getChangeStatusFormAction(@RequestParam String tiersCode) {
		return ResponseEntity.ok(tiersService.initChangeStatusFormBean(tiersCode));
	}

}
