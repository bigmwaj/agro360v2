package com.agro360.dao.core;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IHierarchisableDao;
import com.agro360.dto.core.CategoryDto;
import com.agro360.dto.core.PartnerCategoryDto;
import com.agro360.dto.core.PartnerCategoryPk;

@Repository
public interface IPartnerCategoryDao extends IHierarchisableDao<CategoryDto, PartnerCategoryDto, PartnerCategoryPk>{

	List<PartnerCategoryDto> findAllByPartnerCode(String partnerCode);
	
	default List<PartnerCategoryDto> findAllCategories(String hierarchieCode){
		return findAllByPartnerCode(hierarchieCode);
	}
	
}
