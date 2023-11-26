package com.agro360.service.logic.tiers;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.agro360.dao.tiers.ITiersCategoryDao;
import com.agro360.dao.tiers.ITiersDao;
import com.agro360.dto.tiers.TiersDto;
import com.agro360.service.bean.tiers.TiersSearchBean;
import com.agro360.service.mapper.tiers.TiersMapper;
import com.agro360.vd.tiers.TiersTypeEnumVd;

@TestInstance(Lifecycle.PER_CLASS)
public class TiersServiceTest {

	@InjectMocks
	TiersService service;

	@Mock
	ITiersDao tiersDao;

	@Mock
	ITiersCategoryDao tiersCategoryDao;
	
	@Spy
	TiersMapper tiersMapper;
	
	List<TiersDto> tiersModel;
	
	Optional<TiersDto> findById(String code) {
		return tiersModel.stream().filter(e -> code.equals(e.getTiersCode())).findFirst();
	}
	
	@BeforeAll
	void setUpBeforeClass() throws Exception {
		MockitoAnnotations.openMocks(this);
		
		var company = new TiersDto();
		company.setTiersCode("COMPANY");
		company.setTiersType(TiersTypeEnumVd.COMPANY);
		company.setName("Company Name");

		var person = new TiersDto();
		person.setTiersCode("PERSON");
		person.setTiersType(TiersTypeEnumVd.PERSON);
		person.setFirstName("First Name");
		person.setLastName("Last Name");
		
		tiersModel = Arrays.asList(company, person);
	}

	@SuppressWarnings("unchecked")
	@Test
	void testSearch() {
		// Given
		var bean = new TiersSearchBean();
		
		// When
		when(tiersDao.findAll(any(Example.class))).thenReturn(tiersModel);		
		var results = service.search(bean);
		
		// Then
		assertAll(
			() -> assertEquals(2, results.size())
		);
	}

}
