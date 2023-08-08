package com.agro360.service.logic.stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.ICasierDao;
import com.agro360.dto.stock.CasierDto;
import com.agro360.dto.stock.CasierPk;
import com.agro360.dto.stock.MagasinDto;
import com.agro360.service.bean.stock.CasierBean;
import com.agro360.service.bean.stock.MagasinBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.stock.CasierMapper;
import com.agro360.service.message.Message;

@Service
public class CasierService extends AbstractService<CasierDto, CasierPk> {

	private static final String CREATE_SUCCESS = "Succès création du casier %s du magasin %s!";

	private static final String UPDATE_SUCCESS = "Succès modification du casier %s du magasin %s!";

	private static final String DELETE_SUCCESS = "Succès suppression du casier %s du magasin %s!";

	@Autowired
	ICasierDao dao;

	@Autowired
	CasierMapper mapper;

	@Override
	protected IDao<CasierDto, CasierPk> getDao() {
		return dao;
	}

	private List<Message> deleteCasier(MagasinBean magasinBean, List<CasierDto> existingCasiers) {
		List<Message> messages = new ArrayList<>();
		dao.deleteAll(existingCasiers);
		return messages;
	}

	private List<Message> synchCasiers(MagasinDto magasin, MagasinBean magasinBean, CasierBean bean, List<CasierDto> existingCasiers) {
		if( bean.getAction() == null ) {
			return Collections.emptyList();
		}
		
		List<Message> messages = new ArrayList<>();
		var dto = mapper.mapToDto(magasin, magasinBean, bean);
		switch (bean.getAction()) {
		case CREATE:
			save(dto);
			messages.add(Message.success(String.format(CREATE_SUCCESS, dto.getCasierCode(), magasin.getMagasinCode())));
			break;

		case UPDATE:
			save(dto);
			messages.add(Message.success(String.format(UPDATE_SUCCESS, dto.getCasierCode(), magasin.getMagasinCode())));
			break;

		case DELETE:
			if (existingCasiers.contains(dto)) {
				delete(dto);
				messages.add(Message.success(String.format(DELETE_SUCCESS, dto.getCasierCode(), magasin.getMagasinCode())));
			} else {
				messages.add(
						Message.warn(String.format("Le casier %s n'existe pas!", bean.getCasierCode().getValue())));
			}
			break;
		default:
		}

		return messages;
	}

	public List<Message> synchCasiers(MagasinDto magasin, MagasinBean magasinBean) {
		List<Message> messages = new ArrayList<>();
		var existingCasiers = findCasiers(magasinBean);

		switch (magasinBean.getAction()) {
		case CREATE:
		case UPDATE:
			var casiers = magasinBean.getCasiers();
			for (var bean : casiers) {
				messages.addAll(synchCasiers(magasin, magasinBean, bean, existingCasiers));
			}
			break;

		case DELETE:
			messages.addAll(deleteCasier(magasinBean, existingCasiers));
			break;
		default:
		}

		return messages;
	}

	private List<CasierDto> findCasiers(MagasinBean magasinBean) {
		Example<CasierDto> ex = Example.of(new CasierDto());
		ex.getProbe().setMagasin(new MagasinDto());
		ex.getProbe().getMagasin().setMagasinCode(magasinBean.getMagasinCode().getValue());

		return dao.findAll(ex);
	}
}
