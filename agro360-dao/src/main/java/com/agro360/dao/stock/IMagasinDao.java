package com.agro360.dao.stock;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.stock.MagasinDto;

@Repository
public interface IMagasinDao extends IDao<MagasinDto, String>{

	@Query(
			value = "   select dto from com.agro360.dto.stock.MagasinDto dto"
					+ " where (:magasin is null"
					+ "		or (dto.magasinCode like %:magasin%)"
					+ "		or (upper(dto.description) like %:magasin%)"
					+ " ) "
					+ "order by dto.magasinCode "
					+ "limit :limit offset :offset"
				
		)
	List<MagasinDto> findMagasinsByCriteria( 
		@Param("offset") Integer offset, 
		@Param("limit") Short limit,
		@Param("magasin") String magasin
	);
	
	@Query(
			value = "   select count(dto) from com.agro360.dto.stock.MagasinDto dto"
					+ " where "
					+ " (:magasin is null"
					+ "		or (dto.magasinCode like %:magasin%)"
					+ "		or (upper(dto.description) like %:magasin%)"
					+ " )"
				
		)
	Long countMagasinsByCriteria( 
		@Param("magasin") String magasin
	);
}
