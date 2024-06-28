package com.agro360.operation.logic.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.stock.ArticleCategoryBean;
import com.agro360.bo.bean.stock.CategoryBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.dao.common.IAbstractCategoryDao;
import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IArticleCategoryDao;
import com.agro360.dao.stock.ICategoryDao;
import com.agro360.dto.stock.ArticleCategoryDto;
import com.agro360.dto.stock.ArticleCategoryPk;
import com.agro360.dto.stock.CategoryDto;
import com.agro360.operation.logic.common.AbstractAssignmentOperation;

@Service
public class ArticleCategoryOperation extends AbstractAssignmentOperation<CategoryBean, 
		CategoryDto, 
		ArticleCategoryBean,
		ArticleCategoryDto, 
		ArticleCategoryPk>{
	
	@Autowired
	private IArticleCategoryDao dao;
	
	@Override
	protected IDao<ArticleCategoryDto, ArticleCategoryPk> getDao() {
		return dao;
	}
	
	@Autowired
	private ICategoryDao categoryDao;
	
	@Override
	protected IAbstractCategoryDao<CategoryDto> getCategoryDao() {
		return categoryDao;
	}
	
	@Override
	protected ArticleCategoryDto newInstance(CategoryDto category) {
		return new ArticleCategoryDto(category);
	}

	@Override
	protected ArticleCategoryBean map(CategoryDto dto) {
		return StockMapper.map(dto);
	}

}

