package com.agro360.ws.controller.tiers;

import java.util.Optional;

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

	@GetMapping("/{id}")
	public ResponseEntity<TiersBean> loadTiersByIdAction(@PathVariable String id) {
		return ResponseEntity.ok(tiersService.findById(id));
	}

	@PutMapping
	public ResponseEntity<ModelMap> saveTiersAction(@RequestBody @Validated TiersBean bean, BindingResult br) {
		var messages = tiersService.save(bean);
		var model = new ModelMap(MESSAGES_MODEL_KEY, messages);		
		return ResponseEntity.ok(model);
	}

}
