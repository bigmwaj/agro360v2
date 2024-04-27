package com.agro360.dao.av;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.av.ReglementCommandeDto;

@Repository
public interface IReglementCommandeDao extends IDao<ReglementCommandeDto, Long>{

	List<ReglementCommandeDto> findAllByCommandeCode(String commandeCode);
	
}
