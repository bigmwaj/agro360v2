package com.agro360.ws.controller.stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

import com.agro360.bo.bean.stock.UniteBean;
import com.agro360.bo.bean.stock.UniteSearchBean;
import com.agro360.bo.mapper.stock.UniteMapper;
import com.agro360.bo.message.Message;
import com.agro360.form.stock.UniteForm;
import com.agro360.operation.logic.stock.UniteOperation;
import com.agro360.vd.common.EditActionEnumVd;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/stock/unite")
public class UniteController extends AbstractController {

	@Autowired
	UniteOperation service;

	@Autowired
	UniteMapper mapper;
	
	@Autowired
	UniteForm form;
	
	@GetMapping("/search-form")
	public ResponseEntity<UniteSearchBean> getSearchFormAction() {
		return ResponseEntity.ok(form.initSearchFormBean(getClientContext()));
	}

	@GetMapping("/create-form")
	public ResponseEntity<UniteBean> getCreateFormAction(@RequestParam Optional<String> copyFrom) {
		return ResponseEntity.ok(form.initCreateFormBean(getClientContext(), copyFrom));
	}

	@GetMapping()
	public ResponseEntity<ModelMap> searchAction(@RequestBody(required = false) @Validated Optional<UniteSearchBean> searchBean) {
		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, service.findUnitesByCriteria(getClientContext(), searchBean.orElse(new UniteSearchBean()))));
	}

	@PostMapping
	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated List<UniteBean> beans, BindingResult br) {
		beans.stream().map(this::save).flatMap(List::stream).collect(Collectors.toList());
		return ResponseEntity.ok(new ModelMap());
	}
	
	private List<Message> save(UniteBean bean) {
		if( bean.getAction() == null ) {
			return Collections.singletonList(Message.error("Aucune action sélectionnée"));
		}
		
		if(  EditActionEnumVd.SYNC.equals(bean.getAction())) {
			return Collections.emptyList();
		}
		
		List<Message> messages = new ArrayList<>();

		switch (bean.getAction()) {
		case CREATE:
			service.createUnite(getClientContext(), bean);
			break;

		case UPDATE:
			service.updateUnite(getClientContext(), bean);
			break;

		case DELETE:
			service.deleteUnite(getClientContext(), bean);
			break;
			
		default:
			
		}

		return messages;
	}
}
