package com.agro360.dao.common;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.agro360.dto.common.AbstractDto;

@NoRepositoryBean
public interface IDao <E extends AbstractDto, ID extends Serializable> extends JpaRepository<E, ID>{

	public default E insert(E dto, ID id) {
		/*if( Objects.nonNull(id) && existsById(id) ) {
			throw new DtoAlreadyExistsException(S001.name(), S001.getDefaultMsg(), id);
		}*/
		return save(dto);
	}
	
}
