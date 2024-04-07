package com.agro360.dao.av;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.av.LigneDto;

@Repository(value = "av/ILigneDao")
public interface ILigneDao extends IDao<LigneDto, Long>{

	List<LigneDto> findAllByCommandeCode(String commandeCode);

	Optional<LigneDto> findOneByCommandeCodeAndLigneId(String commandeCode, Long ligneId);

}
