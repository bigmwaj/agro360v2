package com.agro360.ws.controller.production.avicole;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agro360.service.bean.production.avicole.OperationBean;

@RestController
@RequestMapping("/production/avicole/execution/{cycle}")
public class ExecutionController extends OperationController {

	@GetMapping("/create-form")
	public ResponseEntity<OperationBean> getCreateFormAction(@RequestParam Optional<Long> copyFrom) {
		return ResponseEntity.ok(operationService.initCreateFormBean(copyFrom));
	}
	
}
