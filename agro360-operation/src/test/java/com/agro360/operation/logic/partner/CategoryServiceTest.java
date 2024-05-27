package com.agro360.operation.logic.partner;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.mockito.Spy;

import com.agro360.bo.bean.core.CategoryBean;
import com.agro360.operation.logic.core.CategoryOperation;

@TestInstance(Lifecycle.PER_CLASS)
public class CategoryServiceTest {
	
	@Spy
	CategoryOperation service;
	
	@Test
	void test_createCategory() {
		System.out.println("Test");
		// Given		
		var bean = new CategoryBean();
		bean.getCategoryCode().setValue("TEST");
		bean.getDescription().setValue("DESCRIPTION");
		bean.getParentCategoryCode().setValue("ROOT");
		
		// When
//		bean = service.createCategory(bean);
		
		// Then
		assertNotNull(bean);
	}

}
