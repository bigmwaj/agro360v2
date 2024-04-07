package com.agro360.service.rule.module.stock;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.function.Executable;

import com.agro360.bo.bean.stock.ArticleBean;
import com.agro360.bo.bean.stock.ConversionBean;
import com.agro360.bo.bean.stock.VariantBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.helper.RuleXmlHelper;
import com.agro360.vd.common.ClientOperationEnumVd;

@TestInstance(Lifecycle.PER_CLASS)
public class ArticleRuleTest {

	RuleXmlHelper ruleXmlHelper;

	String ruleName;
	
	ClientContext userContext;

	@BeforeAll()
	void setup() {
		ruleName = "stock/article";
		ruleXmlHelper = new RuleXmlHelper();
		userContext = new ClientContext();
	}
	
	ArticleBean createNewBean() {
		var bean = new ArticleBean();
		var variant = new VariantBean();
		var conversion = new ConversionBean();
		
		bean.getVariants().add(variant);
		bean.getConversions().add(conversion);
		
		return bean;
	}
	
	ArticleBean createBean() {
		var bean = createNewBean();
		bean.getArticleCode().setValue("ANY");
		return bean;
	}

	List<Executable> test_initCreate(ArticleBean bean) {
		
		List<Executable> exes = new ArrayList<>();
		
		exes.add(
			() -> assertAll(
				() -> assertTrue(bean.isEditable()),
				() -> assertTrue(bean.isVisible()),	
				() -> assertTrue(bean.getArticleCode().isEditable()),
				() -> assertTrue(bean.getArticleCode().isRequired()),
				() -> assertTrue(bean.getArticleCode().isVisible()),
		
				() -> assertTrue(bean.getType().isEditable()),
				() -> assertTrue(bean.getType().isRequired()),
				() -> assertTrue(bean.getType().isVisible()),
		
				() -> assertTrue(bean.getDescription().isEditable()),
				() -> assertFalse(bean.getDescription().isRequired()),
				() -> assertTrue(bean.getDescription().isVisible()),
		
				() -> assertTrue(bean.getUnite().isEditable()),
				() -> assertTrue(bean.getUnite().isVisible()),
		
				() -> assertTrue(bean.getUnite().getUniteCode().isEditable()),
				() -> assertTrue(bean.getUnite().getUniteCode().isVisible()),
				() -> assertTrue(bean.getUnite().getUniteCode().isRequired()),
		
				() -> assertFalse(bean.getUnite().getDescription().isEditable()),
				() -> assertTrue(bean.getUnite().getDescription().isVisible())
			)
		);
		
		var variant = bean.getVariants().get(0);
		exes.add(
			() -> assertAll(
				() -> assertTrue(variant.getVariantCode().isRequired()),
				() -> assertTrue(variant.getVariantCode().isEditable()),
				() -> assertTrue(variant.getVariantCode().isVisible()),
				
				() -> assertTrue(variant.getDescription().isEditable()),
				() -> assertTrue(variant.getDescription().isVisible())
			)
		);
		
		var conversion = bean.getConversions().get(0);
		exes.add(
			() -> assertAll(
				() -> assertTrue(conversion.getUnite().isEditable()),
				() -> assertTrue(conversion.getUnite().isVisible()),
				
				() -> assertTrue(conversion.getUnite().getUniteCode().isRequired()),
				() -> assertTrue(conversion.getUnite().getUniteCode().isEditable()),
				() -> assertTrue(conversion.getUnite().getUniteCode().isVisible()),
				
				() -> assertTrue(conversion.getFacteur().isEditable()),
				() -> assertTrue(conversion.getFacteur().isVisible())
			)
		);
		
		return exes;
	}
	
	@Test
	void test_initCreate() {
		// Given
		var bean = createNewBean();
		var operation = ClientOperationEnumVd.INIT_CREATE_FORM;
		
		// When
		ruleXmlHelper.applyInitRules(userContext, bean, ruleName, operation);

		// Then
		assertAll(test_initCreate(bean));
	}
	
	@Test
	void test_validateCreate() {
		// Given
		var bean = createNewBean();
		var prevOperation = ClientOperationEnumVd.VALIDATE_FORM;

		// When
		ruleXmlHelper.applyValidationRules(userContext, bean, ruleName, prevOperation);

		// Then
		assertAll(test_initCreate(bean));
	}
	
	List<Executable> test_initEdit(ArticleBean bean) {
			
		List<Executable> exes = new ArrayList<>();
		exes.add( 
			() -> assertAll(
				() -> assertFalse(bean.getArticleCode().isEditable()),
				() -> assertTrue(bean.getArticleCode().isRequired()),
				() -> assertTrue(bean.getArticleCode().isVisible()),
				
				() -> assertFalse(bean.getType().isEditable()),
				() -> assertTrue(bean.getType().isRequired()),
				() -> assertTrue(bean.getType().isVisible()),

				() -> assertTrue(bean.getDescription().isEditable()),
				() -> assertFalse(bean.getDescription().isRequired()),
				() -> assertTrue(bean.getDescription().isVisible()),
				
				() -> assertTrue(bean.getUnite().isEditable()),
				() -> assertTrue(bean.getUnite().isVisible()),
				
				() -> assertFalse(bean.getUnite().getUniteCode().isEditable()),
				() -> assertTrue(bean.getUnite().getUniteCode().isVisible()),
				() -> assertTrue(bean.getUnite().getUniteCode().isRequired()),
				
				() -> assertFalse(bean.getUnite().getDescription().isEditable()),
				() -> assertTrue(bean.getUnite().getDescription().isVisible())
			)
		);
		return exes;
	}

	@Test
	void test_initEdit() {
		// Given
		var bean = createBean();
		var operation = ClientOperationEnumVd.INIT_UPDATE_FORM;

		// When
		ruleXmlHelper.applyInitRules(userContext, bean, ruleName, operation);

		// Then
		assertAll(test_initEdit(bean));
	}
	
	@Test
	void test_validateEdit() {
		// Given
		var bean = createBean();
		var operation = ClientOperationEnumVd.VALIDATE_FORM;

		// When
		ruleXmlHelper.applyValidationRules(userContext, bean, ruleName, operation);

		// Then
		assertAll(test_initEdit(bean));
	}
	
	List<Executable> test_initDelete(ArticleBean bean) {
		List<Executable> exes = new ArrayList<>();
		exes.add(
			() -> assertAll(
				() -> assertFalse(bean.getArticleCode().isEditable()),
				() -> assertTrue(bean.getArticleCode().isRequired()),
				() -> assertTrue(bean.getArticleCode().isVisible())
			)
		);
		return exes;
	}

	@Test
	void test_initDelete() {
		// Given
		var bean = createBean();
		var operation = ClientOperationEnumVd.INIT_DELETE_FORM;

		// When
		ruleXmlHelper.applyInitRules(userContext, bean, ruleName, operation);

		// Then
		assertAll(test_initDelete(bean));
		
	}
	
	@Test
	void test_validateDelete() {
		// Given
		var bean = createBean();
		var prevOperation = ClientOperationEnumVd.VALIDATE_FORM;

		// When
		ruleXmlHelper.applyValidationRules(userContext, bean, ruleName, prevOperation);

		// Then
		assertAll(test_initDelete(bean));
	}
}
