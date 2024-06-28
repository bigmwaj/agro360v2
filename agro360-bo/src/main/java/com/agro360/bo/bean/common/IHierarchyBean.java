package com.agro360.bo.bean.common;

import java.util.List;

public interface IHierarchyBean {
	
	List<? extends AbstractBean> getChildren();
}
