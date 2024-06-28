package com.agro360.dto.core;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.agro360.dao.common.AbstractRepositoryTest;
import com.agro360.dao.core.ICodeGeneratorDao;

public class CodeGenetorRepositoryTest extends AbstractRepositoryTest {

	@Autowired
	private ICodeGeneratorDao dao;

	@Test
	public void create_test() {
		// Given
		var dto = CodeGenetorDto.builder()
				.codeGenCode("TEST")
				.prefix("PREF")
				.sequence(0l)
				.build();

		// When
		dao.save(dto);

		// Then
		assertThat(dao.findAll().size()).isEqualTo(1l);
	}

}
