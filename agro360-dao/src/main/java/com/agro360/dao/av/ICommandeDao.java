package com.agro360.dao.av;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.av.CommandeDto;

@Repository("av/ICommandeDao")
public interface ICommandeDao extends IDao<CommandeDto, String>{

}
