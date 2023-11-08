package com.agro360.service.rule.stock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.agro360.service.bean.stock.CaisseBean;
import com.agro360.service.bean.stock.OperationCaisseBean;
import com.agro360.service.context.BeanContext;
import com.agro360.service.rule.helper.BeanHelper;
import com.agro360.service.rule.helper.RuleXmlHelper;

@TestInstance(Lifecycle.PER_CLASS)
public class CaisseRuleTest {

	RuleXmlHelper ruleXmlHelper;

	String ruleName;
	
	BeanHelper rules;

	@BeforeAll()
	void setup() {
		ruleName = "stock/caisse";
		ruleXmlHelper = new RuleXmlHelper();
		rules = ruleXmlHelper.loadRulesFromXml(ruleName);
	}
	
	CaisseBean createNewBean() {
		var bean = new CaisseBean();
		var operation = new OperationCaisseBean();
		
		bean.getOperations().add(operation);
		
		return bean;
	}
	
	CaisseBean createBean() {
		var bean = createNewBean();
		bean.getMagasin().getMagasinCode().setValue("CENTRAL");
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
		
		assertTrue(bean.getMagasin().isEditable());
		assertTrue(bean.getMagasin().isVisible());
		
		assertTrue(bean.getMagasin().getMagasinCode().isEditable());
		assertTrue(bean.getMagasin().getMagasinCode().isVisible());
		assertTrue(bean.getMagasin().getMagasinCode().isRequired());
		
		assertFalse(bean.getMagasin().getDescription().isEditable());
		assertTrue(bean.getMagasin().getDescription().isVisible());
		assertFalse(bean.getMagasin().getDescription().isRequired());
		
		assertTrue(bean.getAgent().isEditable());
		assertTrue(bean.getAgent().isVisible());
		
		assertTrue(bean.getAgent().getTiersCode().isEditable());
		assertTrue(bean.getAgent().getTiersCode().isVisible());
		assertTrue(bean.getAgent().getTiersCode().isRequired());
		
		assertFalse(bean.getAgent().getTiersName().isEditable());
		assertTrue(bean.getAgent().getTiersName().isVisible());
		assertFalse(bean.getAgent().getTiersName().isRequired());
		
		assertTrue(bean.getJournee().isEditable());
		assertTrue(bean.getJournee().isRequired());
		assertTrue(bean.getJournee().isVisible());
		
		assertFalse(bean.getStatus().isEditable());
		assertTrue(bean.getStatus().isRequired());
		assertTrue(bean.getStatus().isVisible());

		assertTrue(bean.getNote().isEditable());
		assertFalse(bean.getNote().isRequired());
		assertTrue(bean.getNote().isVisible());
		
		{
			var oper = bean.getOperations().get(0);
			
			{
				assertTrue(oper.getArticle().isEditable());
				assertTrue(oper.getArticle().isVisible());
				
				assertTrue(oper.getArticle().getArticleCode().isRequired());
				assertTrue(oper.getArticle().getArticleCode().isEditable());
				assertTrue(oper.getArticle().getArticleCode().isVisible());
			}
			
			{
				assertTrue(oper.getUnite().isEditable());
				assertTrue(oper.getUnite().isVisible());
				
				assertTrue(oper.getUnite().getUniteCode().isRequired());
				assertTrue(oper.getUnite().getUniteCode().isEditable());
				assertTrue(oper.getUnite().getUniteCode().isVisible());
			}
			
			assertFalse(oper.getVariantCode().isRequired());
			assertTrue(oper.getVariantCode().isEditable());
			assertTrue(oper.getVariantCode().isVisible());
			
			assertTrue(oper.getDescription().isEditable());
			assertTrue(oper.getDescription().isVisible());
			
			assertTrue(oper.getDateOperation().isRequired());
			assertTrue(oper.getDateOperation().isEditable());
			assertTrue(oper.getDateOperation().isVisible());
			
			assertTrue(oper.getTypeOperation().isRequired());
			assertTrue(oper.getTypeOperation().isEditable());
			assertTrue(oper.getTypeOperation().isVisible());
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
//		assertFalse(bean.getArticleCode().isEditable());
//		assertTrue(bean.getArticleCode().isRequired());
//		assertTrue(bean.getArticleCode().isVisible());
//		
//		assertFalse(bean.getTypeArticle().isEditable());
//		assertTrue(bean.getTypeArticle().isRequired());
//		assertTrue(bean.getTypeArticle().isVisible());
//
//		assertTrue(bean.getDescription().isEditable());
//		assertFalse(bean.getDescription().isRequired());
//		assertTrue(bean.getDescription().isVisible());
//		
//		assertTrue(bean.getUnite().isEditable());
//		assertTrue(bean.getUnite().isVisible());
//		
//		assertFalse(bean.getUnite().getUniteCode().isEditable());
//		assertTrue(bean.getUnite().getUniteCode().isVisible());
//		assertTrue(bean.getUnite().getUniteCode().isRequired());
//		
//		assertFalse(bean.getUnite().getDescription().isEditable());
//		assertTrue(bean.getUnite().getDescription().isVisible());
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
//		assertFalse(bean.getArticleCode().isEditable());
//		assertTrue(bean.getArticleCode().isRequired());
//		assertTrue(bean.getArticleCode().isVisible());
	}
}
