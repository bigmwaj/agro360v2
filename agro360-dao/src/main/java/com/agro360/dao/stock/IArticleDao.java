package com.agro360.dao.stock;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.stock.ArticleDto;
import com.agro360.dto.stock.UniteDto;
import com.agro360.vd.stock.ArticleTypeEnumVd;

@Repository
public interface IArticleDao extends IDao<ArticleDto, String>{

	@Query(
		value = "   select dto.unite from com.agro360.dto.stock.ArticleDto dto"
				+ " where (dto.articleCode = :article)"
				+ " union "
				+ " select dto2.unite from com.agro360.dto.stock.ConversionDto dto2"
				+ " where (dto2.articleCode = :article)"
	)
	List<UniteDto> findUnitesArticleByCode(@Param("article") String article);

	@Query(
			value = "   select dto from com.agro360.dto.stock.ArticleDto dto"
					+ " where (:type is null or dto.type = :type) " 
					+ " and (:article is null"
					+ "		or (dto.articleCode like %:article%)"
					+ "		or (upper(dto.description) like %:article%)"
					+ " ) "
					+ "order by dto.articleCode "
					+ "limit :limit offset :offset"
				
		)
	List<ArticleDto> findArticlesByCriteria( 
		@Param("offset") Integer offset, 
		@Param("limit") Short limit,
		@Param("type") ArticleTypeEnumVd type,
		@Param("article") String article
	);
	
	@Query(
			value = "   select count(dto) from com.agro360.dto.stock.ArticleDto dto"
					+ " where (:type is null or dto.type = :type) "
					+ " and (:article is null"
					+ "		or (dto.articleCode like %:article%)"
					+ "		or (upper(dto.description) like %:article%)"
					+ " )"
				
		)
	Long countArticlesByCriteria( 
		@Param("type") ArticleTypeEnumVd type,
		@Param("article") String article
	);
}
