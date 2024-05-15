package com.agro360.operation.logic.av;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.av.ReglementFactureBean;
import com.agro360.bo.mapper.AchatVenteMapper;
import com.agro360.dao.av.IReglementFactureDao;
import com.agro360.dao.common.IDao;
import com.agro360.dao.finance.ITransactionDao;
import com.agro360.dto.av.ReglementFactureDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

@Service
public class ReglementFactureOperation extends AbstractOperation<ReglementFactureDto, Long> {

	@Autowired
	private IReglementFactureDao dao;

	@Autowired
	private ITransactionDao transactionDao;
	
	@Override
	protected IDao<ReglementFactureDto, Long> getDao() {
		return dao;
	}

	@RuleNamespace("av/reglement-facture/create")
	public void createReglement(ClientContext ctx, ReglementFactureBean bean) {
		var dto = new ReglementFactureDto();
		
		var txCode = bean.getTransaction().getTransactionCode().getValue();
		var tx = transactionDao.getReferenceById(txCode);
		dto.setTransaction(tx);
		
		setDtoValue(dto::setFactureCode, bean.getFactureCode());
		
		super.save(ctx, dto);
	}

	public List<ReglementFactureBean> findReglementsByFactureCode(ClientContext ctx, String factureCode) {
		return dao.findAllByFactureCode(factureCode).stream()
				.map(AchatVenteMapper::map)
				.collect(Collectors.toList());
	}


}
