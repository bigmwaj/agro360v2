package com.agro360.dao.av;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.av.ReceptionLigneDto;

@Repository
public interface IReceptionLigneDao extends IDao<ReceptionLigneDto, Long>{
	
	List<ReceptionLigneDto> findAllByCommandeCode(String commandeCode);

	Optional<ReceptionLigneDto> findOndByCommandeCodeAndLigneLigneIdAndReceptionId(String commandeCode, Long ligneId, Long receptionId);

}
