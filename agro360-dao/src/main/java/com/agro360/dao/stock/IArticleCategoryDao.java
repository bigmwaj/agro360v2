package com.agro360.dao.stock;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IHierarchisableDao;
import com.agro360.dto.stock.ArticleCategoryDto;
import com.agro360.dto.stock.ArticleCategoryPk;
import com.agro360.dto.stock.CategoryDto;

@Repository
public interface IArticleCategoryDao extends IHierarchisableDao<CategoryDto, ArticleCategoryDto, ArticleCategoryPk>{

	List<ArticleCategoryDto> findAllByArticleCode(String articleCode);
	
	default List<ArticleCategoryDto> findAllCategories(String hierarchieCode){
		return findAllByArticleCode(hierarchieCode);
	}
}
