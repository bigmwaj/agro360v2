package com.agro360.operation.action.finance.transaction;

import static com.agro360.vd.finance.TransactionStatusEnumVd.ANNULEE;
import static com.agro360.vd.finance.TransactionStatusEnumVd.APPROUVEE;
import static com.agro360.vd.finance.TransactionStatusEnumVd.CLOTUREE;
import static com.agro360.vd.finance.TransactionStatusEnumVd.ENCOURS;
import static com.agro360.vd.finance.TransactionStatusEnumVd.RESERVEE;
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

import com.agro360.bo.bean.core.UserAccountBean;
import com.agro360.bo.bean.finance.TransactionBean;
import com.agro360.dao.finance.ITransactionDao;
import com.agro360.dto.finance.TransactionDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.vd.finance.TransactionStatusEnumVd;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
public class FilterStatusActionTest {

	@Mock
	private ITransactionDao daoMock;

	@InjectMocks
	private FilterStatusAction action;

	private ClientContext ctx;

	private TransactionBean bean;

	@BeforeAll
	void setup() {
		ctx = new ClientContext(new UserAccountBean());
		bean = new TransactionBean();
	}

	private TransactionDto build(TransactionStatusEnumVd status) {
		var dto = new TransactionDto();
		dto.setStatus(status);

		return dto;
	}
	
	private Set<TransactionStatusEnumVd> excludeInternal(Collection<TransactionStatusEnumVd> expected){
		return expected.stream().filter(Predicate.not(TransactionStatusEnumVd::isInternalOnly))
		.collect(Collectors.toCollection(TreeSet::new));
	}

	@Test
	void test_process() {

		// Given
		when(this.daoMock.getReferenceById(any()))
			.thenReturn(build(ENCOURS))
			.thenReturn(build(APPROUVEE))
			.thenReturn(build(ANNULEE))
			.thenReturn(build(RESERVEE))
			.thenReturn(build(CLOTUREE));
		
		var expectedStatusForENCOURS = excludeInternal(List.of(APPROUVEE));
		var expectedStatusForAPPROUVEE = excludeInternal(List.of(CLOTUREE));
		var expectedStatusForANNULEE = excludeInternal(List.of());
		var expectedStatusForRESERVEE = excludeInternal(List.of(APPROUVEE, ANNULEE));
		var expectedStatusForCLOTUREE = excludeInternal(List.of());

		// When
		var statusForENCOURS = action.process(ctx, bean).keySet();
		var statusForAPPROUVEE = action.process(ctx, bean).keySet();
		var statusForANNULEE = action.process(ctx, bean).keySet();
		var statusForRESERVEE = action.process(ctx, bean).keySet();
		var statusForCLOTUREE = action.process(ctx, bean).keySet();

		// Then
		assertAll(
			() -> assertIterableEquals(new TreeSet<>(statusForENCOURS), expectedStatusForENCOURS),
			() -> assertIterableEquals(new TreeSet<>(statusForAPPROUVEE), expectedStatusForAPPROUVEE),
			() -> assertIterableEquals(new TreeSet<>(statusForANNULEE), expectedStatusForANNULEE),
			() -> assertIterableEquals(new TreeSet<>(statusForRESERVEE), expectedStatusForRESERVEE),
			() -> assertIterableEquals(new TreeSet<>(statusForCLOTUREE), expectedStatusForCLOTUREE)
		);
	}
}
