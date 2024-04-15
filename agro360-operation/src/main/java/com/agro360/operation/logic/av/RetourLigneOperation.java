package com.agro360.operation.logic.av;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.av.RetourLigneBean;
import com.agro360.bo.mapper.AchatVenteMapper;
import com.agro360.dao.av.ILigneDao;
import com.agro360.dao.av.IRetourLigneDao;
import com.agro360.dao.common.IDao;
import com.agro360.dto.av.LigneDto;
import com.agro360.dto.av.RetourLigneDto;
import com.agro360.operation.logic.common.AbstractOperation;

@Service
public class RetourLigneOperation extends AbstractOperation<RetourLigneDto, Long> {

	@Autowired
	IRetourLigneDao dao;

	@Autowired
	ILigneDao ligneDao;

	@Autowired
	LigneOperation ligneService;

	@Override
	protected IDao<RetourLigneDto, Long> getDao() {
		return dao;
	}
	
	public List<RetourLigneBean> search(String commandeCode) {
		var ex = Example.of(new RetourLigneDto());
		ex.getProbe().setLigne(new LigneDto());
		ex.getProbe().getLigne().setCommandeCode(commandeCode);
		return dao.findAll(ex).stream().map(AchatVenteMapper::map).collect(Collectors.toList());
	}

}
