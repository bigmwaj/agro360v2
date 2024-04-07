//package com.agro360.ws.controller.production.avicole;
//
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
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.agro360.bean.bean.production.avicole.JourneeBean;
//import com.agro360.bean.bean.production.avicole.JourneeSearchBean;
//import com.agro360.service.logic.production.avicole.JourneeService;
//import com.agro360.ws.controller.common.AbstractController;
//
//@RestController
//@RequestMapping("/production/avicole/journee")
//public class JourneeController extends AbstractController {
//
//	@Autowired
//	private JourneeService journeeService;
//	
//	@GetMapping
//	public ResponseEntity<ModelMap> searchAction(
//			@RequestBody(required = false) @Validated Optional<JourneeSearchBean> searchBean) {
//		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, journeeService
//				.search(searchBean.orElse(new JourneeSearchBean()))));
//	}
//	
//	@GetMapping(SEARCH_FORM_RN)
//	public ResponseEntity<JourneeSearchBean> getSearchFormAction() {
//		return ResponseEntity.ok(journeeService.initSearchFormBean());
//	}
//
//	@PostMapping
//	public ResponseEntity<ModelMap> saveAction(@RequestBody @Validated JourneeBean bean, BindingResult br) {
//		var messages = journeeService.save(bean);
//		var model = new ModelMap(MESSAGES_MODEL_KEY, messages);
//		return ResponseEntity.ok(model);
//	}
//}
