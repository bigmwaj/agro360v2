package com.agro360.service.bean.common;

import com.agro360.vd.common.EditActionEnumVd;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AbstractFormBean extends AbstractBean{

	private static final long serialVersionUID = 4953208601044344467L;
	
	private EditActionEnumVd action = null;
}
