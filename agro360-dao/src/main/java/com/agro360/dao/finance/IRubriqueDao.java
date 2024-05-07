package com.agro360.dao.finance;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.finance.RubriqueDto;
import com.agro360.vd.finance.TransactionTypeEnumVd;

@Repository
public interface IRubriqueDao extends IDao<RubriqueDto, String>{
	@Query(
			value = "   select dto from com.agro360.dto.finance.RubriqueDto dto"
					+ " where (:type is null or dto.type = :type)"
					+ " and (:rubrique is null "
					+ "     or (dto.rubriqueCode like (%:rubrique%))"
					+ "     or (upper(dto.libelle) like (%:rubrique%))"
					+ "     or (upper(dto.description) like (%:rubrique%))"
					+ ")"
					+ "order by dto.rubriqueCode asc "
					+ "limit :limit offset :offset"
				
		)
		List<RubriqueDto> findRubriquesByCriteria(
			@Param("offset") Integer offset, 
			@Param("limit") Short limit,
			@Param("rubrique") String rubrique, 
			@Param("type") TransactionTypeEnumVd type
		);
		

		@Query(
			value = "   select count(dto) from com.agro360.dto.finance.RubriqueDto dto"
					+ " where (:type is null or dto.type = :type)"
					+ " and (:rubrique is null "
					+ "     or (dto.rubriqueCode like (%:rubrique%))"
					+ "     or (upper(dto.libelle) like (%:rubrique%))"
					+ "     or (upper(dto.description) like (%:rubrique%))"
					+ ")"
				
		)
		Long countRubriquesByCriteria(
			@Param("rubrique") String rubrique, 
			@Param("type") TransactionTypeEnumVd type
		);
}
