package com.agro360.dao.stock;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.stock.VariantDto;
import com.agro360.dto.stock.VariantPk;

@Repository
public interface IVariantDao extends IDao<VariantDto, VariantPk>{

	List<VariantDto> findAllByArticleCode(String articleCode);

	Optional<VariantDto> findOneByAliasIgnoreCase(String alias);
	
	@Query(
			value = "   select dto from com.agro360.dto.stock.VariantDto dto"
					+ " left join com.agro360.dto.stock.ArticleDto article on dto.articleCode = article.articleCode"
					+ " where (:query is not null) " 
					+ " and (  upper(dto.alias) like :query"
					+ "		or upper(dto.variantCode) like :query"
					+ "		or upper(dto.description) like :query"
					+ "  	or upper(article.articleCode) like :query"
					+ " 	or upper(article.description) like :query"
					+ " )"
					+ " order by dto.articleCode "
					+ " limit 20"
				
		)
	List<VariantDto> findByQuery(@Param("query") String query);

}
