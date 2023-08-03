package com.agro360.ws.bean.tiers;

import java.util.List;

import com.agro360.service.bean.common.AbstractBean;
import com.agro360.service.bean.tiers.TiersBean;
import com.agro360.service.bean.tiers.TiersSearchBean;
import com.agro360.service.metadata.FieldMetadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
public class IndexBean extends AbstractBean{

	private static final long serialVersionUID = 7083068606147994941L;

	private FieldMetadata<String> pageTitle = new FieldMetadata<>();
	
	private List<TiersBean> tiersItems = null;
	
	private TiersSearchBean searchForm = new TiersSearchBean();

	private TiersDataTableHeaderBean dataTableHeader = new TiersDataTableHeaderBean();
	
}
