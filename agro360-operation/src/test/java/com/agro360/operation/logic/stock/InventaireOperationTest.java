package com.agro360.operation.logic.stock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IConversionDao;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.ConversionDto;
import com.agro360.dto.stock.UniteDto;
import com.agro360.operation.context.ClientContext;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class InventaireOperationTest {

	@InjectMocks
	private InventaireOperation operation;
	
	@Mock
	private IConversionDao conversionDaoMock;
	
	@Mock
	private IArticleDao articleDaoMock;
	
	private ClientContext ctx;
	
	@Test
	public void test_getFacteurBase_should_return_one_when_unite_equals_uniteBase() {
		// Given
		var unite = "U";
		var articleCode = "A1";
		var uniteBase = "U";
		
		// When
		var facteur = operation.getFacteurBase(articleCode, uniteBase, unite);
		
		// Then
		assertEquals(facteur, BigDecimal.ONE);
	}
	
	@Test
	public void test_getFacteurBase_should_return_customFactor_when_unite_notEquals_uniteBase() {
		// Given
		var unite = "U1";
		var articleCode = "A1";
		var uniteBase = "U2";
		var customFactor = 12.;
		var conversion = new ConversionDto();
		conversion.setFacteur(customFactor);
		
		when(this.conversionDaoMock.findById(any()))
			.thenReturn(Optional.of(conversion));
		
		// When
		var facteur = operation.getFacteurBase(articleCode, uniteBase, unite);
		
		// Then
		assertEquals(facteur, BigDecimal.valueOf(customFactor));
	}
	
	@Test
	public void test_getFacteurBase_should_throw_exeption_when_conversion_notExists() {
		// Given
		var unite = "U1";
		var articleCode = "A1";
		var uniteBase = "U2";
		
		when(this.conversionDaoMock.findById(any()))
		.thenReturn(Optional.empty());
		
		// When
		Executable exe = () -> operation.getFacteurBase(articleCode, uniteBase, unite);
		
		// Then
		assertThrows(RuntimeException.class, exe);
	}

	@Test
	public void test_getFacteur_should_return_one_when_uniteSource_equals_uniteCible() {
		// Given
		var uniteSource = "U";
		var articleCode = "A1";
		var uniteCible = "U";
		
		// When
		var facteur = operation.getFacteur(ctx, articleCode, uniteCible, uniteSource);
		
		// Then
		assertEquals(facteur, BigDecimal.ONE);
	}
	
	@Test
	public void test_getFacteur_should_throw_exeption_when_conversion_notExists_forUniteSource() {
		// Given
		var uniteSource = "U1";
		var articleCode = "A1";
		var uniteCible = "U2";
		
		var article = new ArticleDto();
		article.setArticleCode(articleCode);
		article.setUnite(new UniteDto());
		article.getUnite().setUniteCode("U");
		
		// When
		when(this.articleDaoMock.getReferenceById(any()))
		.thenReturn(article);
		
		when(this.conversionDaoMock.findById(any()))
		.thenReturn(Optional.empty());
		
		// When
		Executable exe = () -> operation.getFacteur(ctx, articleCode, uniteSource, uniteCible);
		
		// Then
		assertThrows(RuntimeException.class, exe);
	}
	
	@Test
	public void test_getFacteur_should_throw_exeption_when_conversion_notExists_forUniteCible() {
		// Given
		var uniteSource = "U1";
		var articleCode = "A1";
		var uniteCible = "U2";
		var customFactor1 = 12.;
		
		var article = new ArticleDto();
		article.setUnite(new UniteDto());
		article.getUnite().setUniteCode("U");
		article.setArticleCode(articleCode);
		
		var conversion1 = new ConversionDto();
		conversion1.setFacteur(customFactor1);
		
		// When
		when(this.articleDaoMock.getReferenceById(any()))
		.thenReturn(article);
		
		when(this.conversionDaoMock.findById(any()))
		.thenReturn(Optional.of(conversion1))
		.thenReturn(Optional.empty());
		
		// When
		Executable exe = () -> operation.getFacteur(ctx, articleCode, uniteSource, uniteCible);
		
		// Then
		assertThrows(RuntimeException.class, exe);
	}

	@Test
	public void test_getFacteur_should_return_custom_factor() {
		// Given
		var uniteSource = "CARTON";
		var articleCode = "A";
		var uniteCible = "ALV";
		var customFactor1 = 360.;
		var customFactor2 = 30.;
		
		var article = new ArticleDto();
		article.setArticleCode(articleCode);
		article.setUnite(new UniteDto());
		article.getUnite().setUniteCode("U");
		
		var conversion1 = new ConversionDto();
		conversion1.setFacteur(customFactor1);
		
		var conversion2 = new ConversionDto();
		conversion2.setFacteur(customFactor2);
		
		// When
		when(this.articleDaoMock.getReferenceById(any()))
		.thenReturn(article);
		
		when(this.conversionDaoMock.findById(any()))
		.thenReturn(Optional.of(conversion1))
		.thenReturn(Optional.of(conversion2));
		
		// When
		var facteur = operation.getFacteur(ctx, articleCode, uniteSource, uniteCible);
		
		// Then
		assertEquals(0, facteur.compareTo(BigDecimal.valueOf(12.0)));
	}
}
