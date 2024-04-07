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
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.agro360.bean.bean.production.avicole.ProductionBean;
//import com.agro360.bean.bean.production.avicole.ProductionSearchBean;
//import com.agro360.service.logic.production.avicole.ProductionService;
//import com.agro360.ws.controller.common.AbstractController;
//
//@RestController
//@RequestMapping("/production/avicole/production/{cycle}")
//public class ProductionController extends AbstractController {
//
//	@Autowired
//	protected ProductionService productionService;
//	
//	@GetMapping()
//	public ResponseEntity<ModelMap> searchAction(@PathVariable String cycle, 
//			@RequestBody(required = false) @Validated Optional<ProductionSearchBean> searchBean) {
//		return ResponseEntity.ok(new ModelMap(RECORDS_MODEL_KEY, 
//				productionService.search(searchBean.orElse(new ProductionSearchBean()))));
//	}
//
//	@PostMapping
//	public ResponseEntity<ModelMap> saveAction(@PathVariable String cycle, 
//			@RequestBody @Validated List<ProductionBean> beans, BindingResult br) {
//		var messages = productionService.save(beans);
//		var model = new ModelMap(MESSAGES_MODEL_KEY, messages);
//		return ResponseEntity.ok(model);
//	}
//	
//	@GetMapping("/search-form")
//	public ResponseEntity<ProductionSearchBean> getSearchFormAction(@PathVariable String cycle) {
//		return ResponseEntity.ok(productionService.initSearchFormBean());
//	}
//	
//	@GetMapping("/create-form")
//	public ResponseEntity<ProductionBean> getCreateFormAction(@RequestParam Optional<Long> copyFrom) {
//		return ResponseEntity.ok(productionService.initCreateFormBean(copyFrom));
//	}
//}
