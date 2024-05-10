package com.agro360.dao.av;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.av.LigneTaxeDto;
import com.agro360.dto.av.LigneTaxePk;

@Repository
public interface ILigneTaxeDao extends IDao<LigneTaxeDto, LigneTaxePk>{

	List<LigneTaxeDto> findAllByCommandeCodeAndLigneId(String commandeCode, Long ligneId);
	
	List<LigneTaxeDto> findAllByCommandeCode(String commandeCode);
	
	Optional<LigneTaxeDto> findOneByCommandeCodeAndLigneIdAndTaxeTaxeCode(String commandeCode, Long ligneId, String taxeCode);

}
