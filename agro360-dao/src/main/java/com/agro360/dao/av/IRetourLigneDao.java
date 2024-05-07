package com.agro360.dao.av;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.av.RetourLigneDto;

@Repository
public interface IRetourLigneDao extends IDao<RetourLigneDto, Long>{

	List<RetourLigneDto> findAllByCommandeCode(String commandeCode);

	Optional<RetourLigneDto> findOndByCommandeCodeAndLigneLigneIdAndRetourId(String commandeCode, Long ligneId, Long retourId);

}
