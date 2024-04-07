package com.agro360.service.rule.module.stock;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import com.agro360.bo.bean.stock.CaisseBean;
import com.agro360.bo.bean.stock.OperationCaisseBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.rule.helper.RuleXmlHelper;
import com.agro360.vd.common.ClientOperationEnumVd;

@TestInstance(Lifecycle.PER_CLASS)
public class CaisseRuleTest {

	RuleXmlHelper ruleXmlHelper;

	String ruleName;

	ClientContext userContext;

	@BeforeAll()
	void setup() {
		ruleName = "stock/caisse";
		ruleXmlHelper = new RuleXmlHelper();
		userContext = new ClientContext();
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
		var operation = ClientOperationEnumVd.INIT_CREATE_FORM;

		// When
		ruleXmlHelper.applyInitRules(userContext, bean, ruleName, operation);

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
		
		assertTrue(bean.getPartner().isEditable());
		assertTrue(bean.getPartner().isVisible());
		
		assertTrue(bean.getPartner().getPartnerCode().isEditable());
		assertTrue(bean.getPartner().getPartnerCode().isVisible());
		assertTrue(bean.getPartner().getPartnerCode().isRequired());
		
		assertFalse(bean.getPartner().getPartnerName().isEditable());
		assertTrue(bean.getPartner().getPartnerName().isVisible());
		assertFalse(bean.getPartner().getPartnerName().isRequired());
		
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
		var operation = ClientOperationEnumVd.INIT_UPDATE_FORM;

		// When
		ruleXmlHelper.applyInitRules(userContext, bean, ruleName, operation);

		// Then
//		assertFalse(bean.getArticleCode().isEditable());
//		assertTrue(bean.getArticleCode().isRequired());
//		assertTrue(bean.getArticleCode().isVisible());
//		
//		assertFalse(bean.getType().isEditable());
//		assertTrue(bean.getType().isRequired());
//		assertTrue(bean.getType().isVisible());
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
		var operation = ClientOperationEnumVd.INIT_DELETE_FORM;

		// When
		ruleXmlHelper.applyInitRules(userContext, bean, ruleName, operation);

		// Then
//		assertFalse(bean.getArticleCode().isEditable());
//		assertTrue(bean.getArticleCode().isRequired());
//		assertTrue(bean.getArticleCode().isVisible());
	}
}
