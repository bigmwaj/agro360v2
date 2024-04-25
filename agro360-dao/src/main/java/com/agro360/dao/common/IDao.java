package com.agro360.dao.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.agro360.dto.common.AbstractDto;

@NoRepositoryBean
public interface IDao <E extends AbstractDto, ID> extends JpaRepository<E, ID>{

	public default E insert(E dto, ID id) {

		return save(dto);
	}
	
}
