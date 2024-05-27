package com.agro360.operation.action.av.facture;

import static com.agro360.vd.av.FactureStatusEnumVd.ANNL;
import static com.agro360.vd.av.FactureStatusEnumVd.ATAP;
import static com.agro360.vd.av.FactureStatusEnumVd.BRLN;
import static com.agro360.vd.av.FactureStatusEnumVd.CLOT;
import static com.agro360.vd.av.FactureStatusEnumVd.REJT;
import static com.agro360.vd.av.FactureStatusEnumVd.RGLM;
import static com.agro360.vd.av.FactureStatusEnumVd.*;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.agro360.bo.bean.av.FactureBean;
import com.agro360.bo.bean.core.UserAccountBean;
import com.agro360.dao.av.IFactureDao;
import com.agro360.dto.av.FactureDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.vd.av.FactureStatusEnumVd;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class FilterStatusActionTest {

	@Mock
	private IFactureDao daoMock;

	@InjectMocks
	private FilterStatusAction action;

	private ClientContext ctx;

	private FactureBean bean;

	@BeforeAll
	void setup() {
		ctx = new ClientContext(new UserAccountBean());
		bean = new FactureBean();
	}

	private FactureDto build(FactureStatusEnumVd status) {
		var dto = new FactureDto();
		dto.setStatus(status);

		return dto;
	}
	
	private Set<FactureStatusEnumVd> excludeInternal(Collection<FactureStatusEnumVd> expected){
		return expected.stream().filter(Predicate.not(FactureStatusEnumVd::isInternalOnly))
		.collect(Collectors.toCollection(TreeSet::new));
	}

	@Test
	void test_process() {

		// Given
		when(this.daoMock.getReferenceById(any()))
			.thenReturn(build(BRLN))
			.thenReturn(build(ATAP))
			.thenReturn(build(REJT))
			.thenReturn(build(RGLM))
			.thenReturn(build(TERM))
			.thenReturn(build(SOLD));
		
		var expectedStatusForBRLN = excludeInternal(List.of(RGLM, ATAP, SOLD));
		var expectedStatusForATAP = excludeInternal(List.of(RGLM, TERM, SOLD, REJT, ANNL));
		var expectedStatusForREJT = excludeInternal(List.of(ATAP, RGLM, AANN, SOLD));
		var expectedStatusForRGLM = excludeInternal(List.of(RGLM, ATAP, ANNL, TERM, AANN, SOLD));
		var expectedStatusForTERM = excludeInternal(List.of(CLOT, RGLM, SOLD));
		var expectedStatusForSOLD = excludeInternal(List.of(CLOT));

		// When
		var statusForBRLN = action.process(ctx, bean).keySet();
		var statusForATAP = action.process(ctx, bean).keySet();
		var statusForREJT = action.process(ctx, bean).keySet();
		var statusForRGLM = action.process(ctx, bean).keySet();
		var statusForTERM = action.process(ctx, bean).keySet();
		var statusForSOLD = action.process(ctx, bean).keySet();

		
		// Then
		assertAll(
			() -> assertIterableEquals(new TreeSet<>(statusForBRLN), expectedStatusForBRLN),
			() -> assertIterableEquals(new TreeSet<>(statusForATAP), expectedStatusForATAP),
			() -> assertIterableEquals(new TreeSet<>(statusForREJT), expectedStatusForREJT),
			() -> assertIterableEquals(new TreeSet<>(statusForRGLM), expectedStatusForRGLM),
			() -> assertIterableEquals(new TreeSet<>(statusForTERM), expectedStatusForTERM),
			() -> assertIterableEquals(new TreeSet<>(statusForSOLD), expectedStatusForSOLD)
		);
	}
}
