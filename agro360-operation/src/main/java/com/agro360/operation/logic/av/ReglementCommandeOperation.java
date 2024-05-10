package com.agro360.operation.logic.av;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.av.ReglementCommandeBean;
import com.agro360.bo.mapper.AchatVenteMapper;
import com.agro360.dao.av.IReglementCommandeDao;
import com.agro360.dao.common.IDao;
import com.agro360.dao.finance.ITransactionDao;
import com.agro360.dto.av.ReglementCommandeDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;

@Service
public class ReglementCommandeOperation extends AbstractOperation<ReglementCommandeDto, Long> {

	@Autowired
	private IReglementCommandeDao dao;

	@Autowired
	private ITransactionDao transactionDao;
	
	@Override
	protected IDao<ReglementCommandeDto, Long> getDao() {
		return dao;
	}

	@RuleNamespace("av/reglement-commande/create")
	public void createReglement(ClientContext ctx, ReglementCommandeBean bean) {
		var dto = new ReglementCommandeDto();
		
		var txCode = bean.getTransaction().getTransactionCode().getValue();
		var tx = transactionDao.getReferenceById(txCode);
		dto.setTransaction(tx);
		
		setDtoValue(dto::setCommandeCode, bean.getCommandeCode());
		
		super.save(dto);	
	}

	public List<ReglementCommandeBean> findReglementsCommande(ClientContext ctx, String commandeCode) {
		return dao.findAllByCommandeCode(commandeCode).stream()
				.map(AchatVenteMapper::map)
				.collect(Collectors.toList());
	}


}
