package com.agro360.operation.action.av.commande;

import static com.agro360.vd.av.CommandeStatusEnumVd.AANN;
import static com.agro360.vd.av.CommandeStatusEnumVd.ANNL;
import static com.agro360.vd.av.CommandeStatusEnumVd.ATAP;
import static com.agro360.vd.av.CommandeStatusEnumVd.BRLN;
import static com.agro360.vd.av.CommandeStatusEnumVd.CLOT;
import static com.agro360.vd.av.CommandeStatusEnumVd.RECP;
import static com.agro360.vd.av.CommandeStatusEnumVd.RECT;
import static com.agro360.vd.av.CommandeStatusEnumVd.RGLM;
import static com.agro360.vd.av.CommandeStatusEnumVd.SOLD;
import static com.agro360.vd.av.CommandeStatusEnumVd.TERM;
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

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.core.UserAccountBean;
import com.agro360.dao.av.ICommandeDao;
import com.agro360.dto.av.CommandeDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.vd.av.CommandeStatusEnumVd;
import com.agro360.vd.av.CommandeTypeEnumVd;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class FilterStatusActionTest {

	@Mock
	private ICommandeDao daoMock;

	@InjectMocks
	private FilterStatusAction action;

	private ClientContext ctx;

	private CommandeBean bean;

	@BeforeAll
	void setup() {
		ctx = new ClientContext(new UserAccountBean());
		bean = new CommandeBean();
	}

	private CommandeDto build(CommandeStatusEnumVd status, CommandeTypeEnumVd type) {
		var dto = new CommandeDto();
		dto.setStatus(status);
		dto.setType(type);

		return dto;
	}
	
	private Set<CommandeStatusEnumVd> excludeInternal(Collection<CommandeStatusEnumVd> expected){
		return expected.stream().filter(Predicate.not(CommandeStatusEnumVd::isInternalOnly))
		.collect(Collectors.toCollection(TreeSet::new));
	}

	@Test
	void test_process() {

		// Given
		when(this.daoMock.getReferenceById(any()))
			.thenReturn(build(BRLN, CommandeTypeEnumVd.VENTE))
			.thenReturn(build(ATAP, CommandeTypeEnumVd.VENTE))
			.thenReturn(build(AANN, CommandeTypeEnumVd.VENTE))
			.thenReturn(build(RGLM, CommandeTypeEnumVd.VENTE))
			.thenReturn(build(TERM, CommandeTypeEnumVd.VENTE))
			.thenReturn(build(SOLD, CommandeTypeEnumVd.VENTE))
			.thenReturn(build(RECP, CommandeTypeEnumVd.VENTE))
			.thenReturn(build(RECT, CommandeTypeEnumVd.VENTE))
			.thenReturn(build(TERM, CommandeTypeEnumVd.ACHAT))
			.thenReturn(build(SOLD, CommandeTypeEnumVd.ACHAT))
			.thenReturn(build(RECP, CommandeTypeEnumVd.ACHAT))
			.thenReturn(build(RECT, CommandeTypeEnumVd.ACHAT));
		
		var expectedStatusForBRLN = excludeInternal(List.of(ATAP, RGLM, SOLD));
		var expectedStatusForATAP = excludeInternal(List.of(TERM, RGLM, SOLD, ANNL));
		var expectedStatusForAANN = excludeInternal(List.of(ANNL));
		var expectedStatusForRGLM = excludeInternal(List.of(RGLM, ATAP, AANN, SOLD));
		var expectedStatusForTERM_VENTE = excludeInternal(List.of(CLOT));
		var expectedStatusForSOLD_VENTE = excludeInternal(List.of(CLOT));
		var expectedStatusForRECP_VENTE = excludeInternal(List.of(CLOT, RECT));
		var expectedStatusForRECT_VENTE = excludeInternal(List.of(CLOT));
		var expectedStatusForTERM_ACHAT = excludeInternal(List.of(RECT, RECP, CLOT));
		var expectedStatusForSOLD_ACHAT = excludeInternal(List.of(RECT, RECP, CLOT));
		var expectedStatusForRECP_ACHAT = excludeInternal(List.of(RECT, RECP, CLOT));
		var expectedStatusForRECT_ACHAT = excludeInternal(List.of(CLOT));

		// When
		var statusForBRLN = action.process(ctx, bean).keySet();
		var statusForATAP = action.process(ctx, bean).keySet();
		var statusForAANN = action.process(ctx, bean).keySet();
		var statusForRGLM = action.process(ctx, bean).keySet();
		var statusForTERM_VENTE = action.process(ctx, bean).keySet();
		var statusForSOLD_VENTE = action.process(ctx, bean).keySet();
		var statusForRECP_VENTE = action.process(ctx, bean).keySet();
		var statusForRECT_VENTE = action.process(ctx, bean).keySet();
		var statusForTERM_ACHAT = action.process(ctx, bean).keySet();
		var statusForSOLD_ACHAT = action.process(ctx, bean).keySet();
		var statusForRECP_ACHAT = action.process(ctx, bean).keySet();
		var statusForRECT_ACHAT = action.process(ctx, bean).keySet();

		// Then
		assertAll(
			() -> assertIterableEquals(new TreeSet<>(statusForBRLN), expectedStatusForBRLN),
			() -> assertIterableEquals(new TreeSet<>(statusForATAP), expectedStatusForATAP),
			() -> assertIterableEquals(new TreeSet<>(statusForAANN), expectedStatusForAANN),
			() -> assertIterableEquals(new TreeSet<>(statusForRGLM), expectedStatusForRGLM),
			() -> assertIterableEquals(new TreeSet<>(statusForTERM_VENTE), expectedStatusForTERM_VENTE),
			() -> assertIterableEquals(new TreeSet<>(statusForSOLD_VENTE), expectedStatusForSOLD_VENTE),
			() -> assertIterableEquals(new TreeSet<>(statusForRECP_VENTE), expectedStatusForRECP_VENTE),
			() -> assertIterableEquals(new TreeSet<>(statusForRECT_VENTE), expectedStatusForRECT_VENTE),
			() -> assertIterableEquals(new TreeSet<>(statusForTERM_ACHAT), expectedStatusForTERM_ACHAT),
			() -> assertIterableEquals(new TreeSet<>(statusForSOLD_ACHAT), expectedStatusForSOLD_ACHAT),
			() -> assertIterableEquals(new TreeSet<>(statusForRECP_ACHAT), expectedStatusForRECP_ACHAT),
			() -> assertIterableEquals(new TreeSet<>(statusForRECT_ACHAT), expectedStatusForRECT_ACHAT)
		);
	}
}
