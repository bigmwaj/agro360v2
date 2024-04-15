package com.agro360.service.production.avicole;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = Exception.class)
@Service
public class ExecutionService extends OperationService {

}
