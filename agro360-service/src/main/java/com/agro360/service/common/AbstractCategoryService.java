package com.agro360.service.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.agro360.bo.bean.common.AbstractCategoryBean;
import com.agro360.bo.message.Message;
import com.agro360.dto.common.AbstractCategoryDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractCategoryOperation;

public abstract class AbstractCategoryService<B extends AbstractCategoryBean<B>> extends AbstractService {

	protected abstract <T extends AbstractCategoryDto<T>>AbstractCategoryOperation<T , B> getOperation();

	public B indexAction(ClientContext ctx, Optional<Integer> deep) {
		return getOperation().findCategoryByCode(ctx, "ROOT", deep);
	}

	public List<B> childrenAction(ClientContext ctx, String id) {
		return getOperation().findChildrenCategory(id);
	}

	public void saveAction(ClientContext ctx, B bean) {		
		var messages = new ArrayList<Message>();
		save(ctx, bean, messages);
	}
	
	private void save(ClientContext ctx, B bean, List<Message> messages) {
		if( !"ROOT".equals(bean.getCategoryCode().getValue()) ) {
			switch (bean.getAction()) {
			case CREATE:
				getOperation().createCategory(ctx, bean);
				break;

			case UPDATE:
				getOperation().updateCategory(ctx, bean);
				break;

			case DELETE:
				getOperation().deleteCategory(bean);
				return;

			default:
				return;
			}
		}
		
		for (var b : bean.getChildren()) {
			save(ctx, b, messages);
		}
	}

}
