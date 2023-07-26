package com.agro360.service.mapper.vente;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.dao.vente.ICommandeDao;
import com.agro360.dto.vente.CommandeDto;
import com.agro360.service.bean.vente.CommandeBean;
import com.agro360.service.mapper.common.AbstractMapper;

@Component
public class CommandeMapper extends AbstractMapper {

	@Autowired
	ICommandeDao dao;

	public CommandeBean mapToBean(CommandeDto dto, Map<String, Object> options) {
		var bean = new CommandeBean();

		return bean;
	}

	public CommandeDto mapToDto(CommandeBean bean) {
		var dto = new CommandeDto();

		return dto;
	}
}
