package com.agro360.operation.logic.av;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.av.ReceptionBean;
import com.agro360.bo.mapper.av.ReceptionMapper;
import com.agro360.dao.av.ILigneDao;
import com.agro360.dao.av.IReceptionDao;
import com.agro360.dao.common.IDao;
import com.agro360.dto.av.LigneDto;
import com.agro360.dto.av.ReceptionDto;
import com.agro360.operation.logic.common.AbstractOperation;

@Service("av/ReceptionService")
public class ReceptionOperation extends AbstractOperation<ReceptionDto, Long> {

	@Autowired
	IReceptionDao dao;

	@Autowired
	ILigneDao ligneDao;

	@Autowired
	LigneOperation ligneService;

	@Autowired
	ReceptionMapper mapper;

	@Override
	protected IDao<ReceptionDto, Long> getDao() {
		return dao;
	}
	
	@Override
	protected String getRulePath() {
		return "achat/bon-commande/reception";
	}

	public List<ReceptionBean> search(String commandeCode) {
		var ex = Example.of(new ReceptionDto());
		ex.getProbe().setLigne(new LigneDto());
		ex.getProbe().getLigne().setCommandeCode(commandeCode);
		return dao.findAll(ex).stream().map(mapper::mapToBean).collect(Collectors.toList());
	}

}
