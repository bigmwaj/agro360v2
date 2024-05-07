package com.agro360.dao.av;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.av.ReglementFactureDto;

@Repository
public interface IReglementFactureDao extends IDao<ReglementFactureDto, Long>{

	List<ReglementFactureDto> findAllByFactureCode(String factureCode);

	
}
