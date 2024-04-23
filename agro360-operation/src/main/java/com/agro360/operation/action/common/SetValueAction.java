package com.agro360.operation.action.common;

import com.agro360.bo.bean.common.AbstractBean;
import com.agro360.operation.context.ClientContext;

public class SetValueAction<T> extends AbstractAction<T> {

	private final T value;

	public SetValueAction(T value) {
		this.value = value;
	}

	@Override
	public T process(ClientContext ctx, AbstractBean bean) {
		getLogger().debug("Process SetValueAction with value {}", this.value);
		return this.value;
	}
}