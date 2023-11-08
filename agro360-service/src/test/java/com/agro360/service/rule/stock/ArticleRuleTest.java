package com.agro360.service.rule.stock;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.agro360.service.bean.stock.ArticleBean;
import com.agro360.service.bean.stock.ConversionBean;
import com.agro360.service.bean.stock.VariantBean;
import com.agro360.service.context.BeanContext;
import com.agro360.service.rule.helper.BeanHelper;
import com.agro360.service.rule.helper.RuleXmlHelper;

@TestInstance(Lifecycle.PER_CLASS)
public class ArticleRuleTest {

	RuleXmlHelper ruleXmlHelper;

	String ruleName;
	
	BeanHelper rules;

	@BeforeAll()
	void setup() {
		ruleName = "stock/article";
		ruleXmlHelper = new RuleXmlHelper();
		rules = ruleXmlHelper.loadRulesFromXml(ruleName);
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

	@Test
	void test_initCreate() {
		// Given
		var bean = createNewBean();
		var operation = "init-create-form";
		var beanCtx = new BeanContext();
		
		beanCtx.setOperation(operation);

		// When
		ruleXmlHelper.applyInitRules(beanCtx, rules, bean);

		// Then
		assertTrue(bean.isEditable());
		assertTrue(bean.isVisible());
		
		assertTrue(bean.getArticleCode().isEditable());
		assertTrue(bean.getArticleCode().isRequired());
		assertTrue(bean.getArticleCode().isVisible());
		
		assertTrue(bean.getTypeArticle().isEditable());
		assertTrue(bean.getTypeArticle().isRequired());
		assertTrue(bean.getTypeArticle().isVisible());

		assertTrue(bean.getDescription().isEditable());
		assertFalse(bean.getDescription().isRequired());
		assertTrue(bean.getDescription().isVisible());
		
		assertTrue(bean.getUnite().isEditable());
		assertTrue(bean.getUnite().isVisible());
		
		assertTrue(bean.getUnite().getUniteCode().isEditable());
		assertTrue(bean.getUnite().getUniteCode().isVisible());
		assertTrue(bean.getUnite().getUniteCode().isRequired());
		
		assertFalse(bean.getUnite().getDescription().isEditable());
		assertTrue(bean.getUnite().getDescription().isVisible());
		
		{
			var variant = bean.getVariants().get(0);
			assertTrue(variant.getVariantCode().isRequired());
			assertTrue(variant.getVariantCode().isEditable());
			assertTrue(variant.getVariantCode().isVisible());
			
			assertTrue(variant.getDescription().isEditable());
			assertTrue(variant.getDescription().isVisible());
		}
		
		{
			var conversion = bean.getConversions().get(0);
			assertTrue(conversion.getUnite().isEditable());
			assertTrue(conversion.getUnite().isVisible());
			
			assertTrue(conversion.getUnite().getUniteCode().isRequired());
			assertTrue(conversion.getUnite().getUniteCode().isEditable());
			assertTrue(conversion.getUnite().getUniteCode().isVisible());
			
			assertTrue(conversion.getFacteur().isEditable());
			assertTrue(conversion.getFacteur().isVisible());
		}
	}

	@Test
	void test_initEdit() {
		// Given
		var bean = createBean();
		var operation = "init-edit-form";
		var beanCtx = new BeanContext();
		
		beanCtx.setOperation(operation);

		// When
		ruleXmlHelper.applyInitRules(beanCtx, rules, bean);

		// Then
		assertFalse(bean.getArticleCode().isEditable());
		assertTrue(bean.getArticleCode().isRequired());
		assertTrue(bean.getArticleCode().isVisible());
		
		assertFalse(bean.getTypeArticle().isEditable());
		assertTrue(bean.getTypeArticle().isRequired());
		assertTrue(bean.getTypeArticle().isVisible());

		assertTrue(bean.getDescription().isEditable());
		assertFalse(bean.getDescription().isRequired());
		assertTrue(bean.getDescription().isVisible());
		
		assertTrue(bean.getUnite().isEditable());
		assertTrue(bean.getUnite().isVisible());
		
		assertFalse(bean.getUnite().getUniteCode().isEditable());
		assertTrue(bean.getUnite().getUniteCode().isVisible());
		assertTrue(bean.getUnite().getUniteCode().isRequired());
		
		assertFalse(bean.getUnite().getDescription().isEditable());
		assertTrue(bean.getUnite().getDescription().isVisible());
	}

	@Test
	void test_initDelete() {
		// Given
		var bean = createBean();
		var operation = "init-delete-form";
		var beanCtx = new BeanContext();
		beanCtx.setOperation(operation);

		// When
		ruleXmlHelper.applyInitRules(beanCtx, rules, bean);

		// Then
		assertFalse(bean.getArticleCode().isEditable());
		assertTrue(bean.getArticleCode().isRequired());
		assertTrue(bean.getArticleCode().isVisible());
	}
}
