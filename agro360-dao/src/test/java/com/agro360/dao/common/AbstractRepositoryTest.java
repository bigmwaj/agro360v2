package com.agro360.dao.common;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import com.agro360.dao.spring.TestConfig;

@DataJpaTest
@ContextConfiguration(classes = {TestConfig.class})
public class AbstractRepositoryTest {

}
