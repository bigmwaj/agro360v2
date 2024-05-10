package com.agro360.dao.av;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.av.FactureTaxeDto;
import com.agro360.dto.av.FactureTaxePk;

@Repository
public interface IFactureTaxeDao extends IDao<FactureTaxeDto, FactureTaxePk>{

	Optional<FactureTaxeDto> findOneByFactureCodeAndTaxeTaxeCode(String factureCode, String taxeCode);

	List<FactureTaxeDto> findAllByFactureCode(String factureCode);

}
