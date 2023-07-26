package com.agro360.dao.vente;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.vente.CommandeDto;

@Repository
public interface ICommandeDao extends IDao<CommandeDto, String>{

}
