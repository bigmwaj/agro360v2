package com.agro360.bo.bean.common;

import java.util.List;

public interface IHierarchieBean {
	
	List<? extends AbstractBean> getChildren();
}
