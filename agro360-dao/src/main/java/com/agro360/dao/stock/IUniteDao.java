package com.agro360.dao.stock;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.stock.UniteDto;

@Repository
public interface IUniteDao extends IDao<UniteDto, String>{

	@Query(
			value = "   select dto from com.agro360.dto.stock.UniteDto dto"
					+ " where"
					+ " (:unite is null"
					+ "		or (dto.uniteCode like %:unite%)"
					+ "		or (upper(dto.description) like %:unite%)"
					+ " ) "
					+ "order by dto.uniteCode "
					+ "limit :limit offset :offset"
				
		)
	List<UniteDto> findUnitesByCriteria( 
		@Param("offset") Integer offset, 
		@Param("limit") Short limit,
		@Param("unite") String unite
	);
	
	@Query(
			value = "   select count(dto) from com.agro360.dto.stock.UniteDto dto"
					+ " where "
					+ " (:unite is null"
					+ "		or (dto.uniteCode like %:unite%)"
					+ "		or (upper(dto.description) like %:unite%)"
					+ " )"
				
		)
	Long countUnitesByCriteria( 
		@Param("unite") String unite
	);
}
