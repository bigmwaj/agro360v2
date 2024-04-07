package com.agro360.dao.av;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.av.ReceptionDto;

@Repository("av/IReceptionDao")
public interface IReceptionDao extends IDao<ReceptionDto, Long>{

}
