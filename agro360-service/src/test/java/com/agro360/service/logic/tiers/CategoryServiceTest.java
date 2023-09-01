package com.agro360.service.logic.tiers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.data.domain.Example;

import com.agro360.dao.tiers.ICategoryDao;
import com.agro360.dao.tiers.ITiersCategoryDao;
import com.agro360.dto.tiers.CategoryDto;
import com.agro360.service.bean.tiers.CategoryBean;
import com.agro360.service.mapper.tiers.CategoryMapper;

@TestInstance(Lifecycle.PER_CLASS)
public class CategoryServiceTest {

	@InjectMocks
	CategoryService service;

	@Mock
	ICategoryDao categoryDao;

	@Mock
	ITiersCategoryDao tiersCategoryDao;
	
	@Spy
	CategoryMapper categoryMapper;
	
	List<CategoryDto> categoryModel;
	
	Optional<CategoryDto> findById(String code) {
		return categoryModel.stream().filter(e->code.equals(e.getCategoryCode())).findFirst();
	}
	
	@BeforeAll
	void setUpBeforeClass() throws Exception {
		MockitoAnnotations.openMocks(this);
		
		CategoryDto root = new CategoryDto();
		root.setCategoryCode("ROOT");
		root.setDescription("Root Category");
		
		CategoryDto child1 = new CategoryDto();
		child1.setCategoryCode("CHILD1");
		child1.setDescription("Child 1");
		child1.setParent(root);
		
		CategoryDto child11 = new CategoryDto();
		child11.setCategoryCode("CHILD11");
		child11.setDescription("Child 11");
		child11.setParent(child1);
		
		
		CategoryDto child2 = new CategoryDto();
		child2.setCategoryCode("CHILD2");
		child2.setDescription("Child 2");
		child2.setParent(root);
		
		CategoryDto child21 = new CategoryDto();
		child21.setCategoryCode("CHILD21");
		child21.setDescription("Child 21");
		child21.setParent(child2);
		
		categoryModel = Arrays.asList(root, child1, child11, child2, child21);
	}

//	@SuppressWarnings("unchecked")
//	@Test
//	void testSearch() {
//		// Given
//		CategorySearchBean bean = new CategorySearchBean();
//		
//		// When
//		when(tiersDao.findAll(any(Example.class))).thenReturn(tiersModel);		
//		List<CategoryBean> results = service.search(bean);
//		
//		// Then
//		assertAll(
//			() -> assertEquals(2, results.size())
//		);
//	}
	
//	@Test
//	void testSave() {
//		// Given
//		String code = "PERSON";
//		
//		// When
//		when(categoryDao.findById(code)).thenReturn(findById(code));	
//		CategoryBean bean = service.findById(code);
//		
//		// Then
//		assertAll(
//			() -> assertNotNull(bean)
//		);
//	}
	
	@Test()
	void test_findRootCategory() {
		// Given
		var categoryCode = "ROOT";
		var root = findById(categoryCode).orElse(null);
		
		// When
		when(categoryDao.getReferenceById(any())).thenReturn(root);	
		CategoryBean bean = service.findRootCategory(Optional.empty());
		
		// Then
		assertAll(
			() -> assertNotNull(bean),
			() -> assertEquals(bean.getCategoryCode().getValue(), categoryCode),
			() -> assertTrue(bean.getChildren().isEmpty())
		);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	void test_findChildrenCategory() {
		
		// Given
		String code = "ROOT";
		List<CategoryDto> dtos = 
		categoryModel.stream()
		.filter(e -> e.getParent() != null)
		.filter(e -> code.equals(e.getParent().getCategoryCode())).toList();
		
		// When
		when(categoryDao.findAll(any(Example.class))).thenReturn(dtos);	
		List<CategoryBean> children = service.findChildrenCategory(code);
		
		// Then
		assertAll(
			() -> assertEquals(children.size(), 2)
		);
	}

}
