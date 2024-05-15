package com.agro360.bo.bean.common;

import java.io.Serializable;

import com.agro360.bo.metadata.FieldMetadata;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AbstractSearchBean  extends AbstractBean implements Serializable {

	private static final long serialVersionUID = -8406398372083120110L;

	private static final Short DEFAULT_QUERY_LIMIT = 1_000;

	private Long length;
	
	private Short pageSize = 10;
	
	private Integer pageIndex = 0;
	
	private Integer pageSizeOptions[] = {5, 10, 20, 50};

	private FieldMetadata<String> createBtn = new FieldMetadata<>();
	
	public AbstractSearchBean(){
		super();
	}
	
	public AbstractSearchBean(Short pageSize){
		super();
		this.pageSize = pageSize;
	}
	
	@JsonIgnore
	public Integer getOffset() {
		if( pageSize != null ) {
			return pageSize * pageIndex;
		}
		return pageIndex;
	}
	
	@JsonIgnore
	public Short getLimit() {
		if( pageSize == null ) {
			return DEFAULT_QUERY_LIMIT;
		}
		return pageSize;
	}
}
