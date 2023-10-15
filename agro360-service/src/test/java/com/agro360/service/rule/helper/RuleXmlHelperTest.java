package com.agro360.service.rule.helper;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

@TestInstance(Lifecycle.PER_CLASS)
public class RuleXmlHelperTest {

	@Test
	void loadMetadataFromXmlTest() {
		try {
			// Given
			var helper = new RuleXmlHelper();
			var xmlName = "stock/article";

			// When
			var result = helper.loadMetadataFromXml(xmlName);

			// Then
			System.out.println("RuleXmlHelperTest.loadMetadataFromXmlTest() " + result);
			assertNotNull(result);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
