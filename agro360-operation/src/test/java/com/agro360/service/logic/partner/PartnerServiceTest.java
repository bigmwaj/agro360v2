package com.agro360.service.logic.partner;

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
import org.springframework.data.domain.Example;

import com.agro360.dao.core.IPartnerCategoryDao;
import com.agro360.dao.core.IPartnerDao;
import com.agro360.dto.core.PartnerDto;
import com.agro360.operation.logic.core.PartnerOperation;
import com.agro360.vd.core.PartnerTypeEnumVd;

@TestInstance(Lifecycle.PER_CLASS)
public class PartnerServiceTest {

	@InjectMocks
	PartnerOperation service;

	@Mock
	IPartnerDao partnerDao;

	@Mock
	IPartnerCategoryDao partnerCategoryDao;
	
	List<PartnerDto> partnerModel;
	
	Optional<PartnerDto> findById(String code) {
		return partnerModel.stream().filter(e -> code.equals(e.getPartnerCode())).findFirst();
	}
	
	@BeforeAll
	void setUpBeforeClass() throws Exception {
		MockitoAnnotations.openMocks(this);
		
		var company = new PartnerDto();
		company.setPartnerCode("COMPANY");
		company.setType(PartnerTypeEnumVd.COMPANY);
		company.setName("Company Name");

		var person = new PartnerDto();
		person.setPartnerCode("PERSON");
		person.setType(PartnerTypeEnumVd.PERSON);
		person.setFirstName("First Name");
		person.setLastName("Last Name");
		
		partnerModel = Arrays.asList(company, person);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testSearch() {
		// Given
//		var bean = new PartnerSearchBean();
		
		// When
		when(partnerDao.findAll(any(Example.class))).thenReturn(partnerModel);		
//		var results = service.search(bean);
//		
//		// Then
//		assertAll(
//			() -> assertEquals(2, results.size())
//		);
	}

}
