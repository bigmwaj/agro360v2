package com.agro360.web.controller.tiers;

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

import com.agro360.service.bean.tiers.TiersBean;
import com.agro360.service.logic.tiers.TiersService;
import com.agro360.service.utils.Message;
import com.agro360.web.controller.common.AbstractController;

@RestController()
@RequestMapping("/tiers/tiers")
public class TiersController extends AbstractController {

	@Autowired
	private TiersService tiersService;

	@GetMapping("/{id}")
	public ResponseEntity<TiersBean> loadAction(@PathVariable String id) {
		return ResponseEntity.ok(tiersService.load(id));
	}

	@PutMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated TiersBean bean, BindingResult br) {
		List<Message> messages = tiersService.save(bean);
		ModelMap model = new ModelMap("messages", messages);		
		return ResponseEntity.ok(model);
	}

}
