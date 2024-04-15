package com.agro360.operation.logic.av;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.av.ReceptionLigneBean;
import com.agro360.bo.mapper.AchatVenteMapper;
import com.agro360.dao.av.ILigneDao;
import com.agro360.dao.av.IReceptionLigneDao;
import com.agro360.dao.common.IDao;
import com.agro360.dto.av.LigneDto;
import com.agro360.dto.av.ReceptionLigneDto;
import com.agro360.operation.logic.common.AbstractOperation;

@Service
public class ReceptionLigneOperation extends AbstractOperation<ReceptionLigneDto, Long> {

	@Autowired
	IReceptionLigneDao dao;

	@Autowired
	ILigneDao ligneDao;

	@Autowired
	LigneOperation ligneService;

	@Override
	protected IDao<ReceptionLigneDto, Long> getDao() {
		return dao;
	}
	
	public List<ReceptionLigneBean> search(String commandeCode) {
		var ex = Example.of(new ReceptionLigneDto());
		ex.getProbe().setLigne(new LigneDto());
		ex.getProbe().getLigne().setCommandeCode(commandeCode);
		return dao.findAll(ex).stream().map(AchatVenteMapper::mapToBean).collect(Collectors.toList());
	}

}
