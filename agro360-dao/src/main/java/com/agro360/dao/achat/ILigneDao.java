package com.agro360.dao.achat;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.achat.LigneDto;

@Repository(value = "achat/ILigneDao")
public interface ILigneDao extends IDao<LigneDto, Long>{

}
