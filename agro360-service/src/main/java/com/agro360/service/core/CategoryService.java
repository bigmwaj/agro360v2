package com.agro360.service.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.core.CategoryBean;
import com.agro360.bo.message.Message;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.core.CategoryOperation;
import com.agro360.service.common.AbstractService;

@Transactional(rollbackFor = Exception.class)
@Service
public class CategoryService extends AbstractService {

	@Autowired
	private CategoryOperation service;

	public CategoryBean indexAction(ClientContext ctx, Optional<Integer> deep) {
		return service.findCategoryByCode(ctx, "ROOT", deep);
	}

	public List<CategoryBean> childrenAction(ClientContext ctx, String id) {
		return service.findChildrenCategory(id);
	}

	public void saveAction(ClientContext ctx, CategoryBean bean) {		
		var messages = new ArrayList<Message>();
		save(ctx, bean, messages);
	}
	
	private void save(ClientContext ctx, CategoryBean bean, List<Message> messages) {
		if( !"ROOT".equals(bean.getCategoryCode().getValue()) ) {
			switch (bean.getAction()) {
			case CREATE:
				service.createCategory(ctx, bean);
				break;

			case UPDATE:
				service.updateCategory(ctx, bean);
				break;

			case DELETE:
				service.deleteCategory(bean);
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
