package com.agro360.operation.action.common;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.ClientContext;

public class SetValueAction<T> extends AbstractAction<T, AbstractBean> {

	private final T value;

	public SetValueAction(T value) {
		this.value = value;
	}

	@Override
	public T process(ClientContext ctx, AbstractBean bean) {
		return this.value;
	}
}