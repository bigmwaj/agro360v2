//package com.agro360.ws.controller.production.avicole;
//
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.ui.ModelMap;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//
//import com.agro360.bean.bean.production.avicole.OperationBean;
//import com.agro360.bean.bean.production.avicole.OperationSearchBean;
//import com.agro360.service.logic.production.avicole.OperationService;
//import com.agro360.ws.controller.common.AbstractController;
//
//public abstract class OperationController extends AbstractController {
//
//	@Autowired
//	protected OperationService operationService;
//	
//	@GetMapping()
//	public ResponseEntity<ModelMap> searchAction(@RequestBody(required = false) @Validated Optional<OperationSearchBean> searchBean) {
//		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, operationService.search(searchBean.orElse(new OperationSearchBean()))));
//	}
//
//	@PostMapping
//	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated List<OperationBean> beans, BindingResult br) {
//		var messages = operationService.save(beans);
//		var model = new ModelMap(MESSAGES_MODEL_KEY, messages);
//		return ResponseEntity.ok(model);
//	}
//	
//	@GetMapping("/search-form")
//	public ResponseEntity<OperationSearchBean> getSearchFormAction() {
//		return ResponseEntity.ok(operationService.initSearchFormBean());
//	}
//	
//}
