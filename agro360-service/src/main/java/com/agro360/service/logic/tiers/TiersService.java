package com.agro360.service.logic.tiers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.agro360.dao.tiers.ICategoryDao;
import com.agro360.dao.tiers.ITiersCategoryDao;
import com.agro360.dao.tiers.ITiersDao;
import com.agro360.dto.tiers.CategoryDto;
import com.agro360.dto.tiers.TiersCategoryDto;
import com.agro360.dto.tiers.TiersDto;
import com.agro360.service.bean.tiers.TiersBean;
import com.agro360.service.bean.tiers.TiersCategoryHierarchieBean;
import com.agro360.service.bean.tiers.TiersSearchBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.tiers.TiersMapper;
import com.agro360.service.mapper.tiers.TiersCategoryHierarchieMapper;
import com.agro360.service.utils.Message;
import com.agro360.vd.tiers.TiersStatusEnumVd;

@Service
public class TiersService extends AbstractService<TiersDto, String, ITiersDao>{

	private static final String CREATE_SUCCESS = "Enregistrement créé avec succès!";
	
	private static final String UPDATE_SUCCESS = "Enregistrement modifié avec succès!";
	
	private static final String DELETE_SUCCESS = "Enregistrement supprimé avec succès!";
	
	private static final String CHANGE_STS_SUCCESS = "Statut enregistrement modifié avec succès!";
	
	@Autowired
	private ITiersDao tiersDao;
	
	@Autowired
	private ITiersCategoryDao tiersCategoryDao;
	
	@Autowired
	private ICategoryDao categoryDao;

	@Autowired
	private TiersCategoryService tiersCategoryService;
	
	@Autowired
	private TiersMapper tiersBinding;
	
	@Autowired
	private TiersCategoryHierarchieMapper tiersCategoryBinding;
	
	@Override
	public ITiersDao getDao() {
		return tiersDao;
	}

	public List<TiersBean> search(TiersSearchBean bean) {
		Example<TiersDto> example = Example.of(new TiersDto());

		if (bean.getTiersCode() != null) {
			example.getProbe().setTiersCode(bean.getTiersCode().getValue());
		}

		if (bean.getTiersName() != null) {
			example.getProbe().setName(bean.getTiersName().getValue());
			example.getProbe().setFirstName(bean.getTiersName().getValue());
			example.getProbe().setLastName(bean.getTiersName().getValue());
			example.getMatcher().withIgnoreCase().withMatcher("name",
					ExampleMatcher.GenericPropertyMatchers.contains());
			example.getMatcher().withIgnoreCase().withMatcher("firstName",
					ExampleMatcher.GenericPropertyMatchers.contains());
			example.getMatcher().withIgnoreCase().withMatcher("lastName",
					ExampleMatcher.GenericPropertyMatchers.contains());
		}

		if (bean.getEmail() != null) {
			example.getProbe().setEmail(bean.getEmail().getValue());
			example.getMatcher().withIgnoreCase().withMatcher("email",
					ExampleMatcher.GenericPropertyMatchers.contains());
		}

		if (bean.getPhone() != null) {
			example.getProbe().setPhone(bean.getPhone().getValue());
			example.getMatcher().withMatcher("phone", ExampleMatcher.GenericPropertyMatchers.endsWith());
		}

		if (bean.getStatus() != null) {
			example.getProbe().setStatus((TiersStatusEnumVd) bean.getStatus().getValue());
		}

		if (bean.getTiersType() != null) {
			example.getProbe().setTiersType(bean.getTiersType().getValue());
		}
		return tiersDao.findAll(example).stream().map(this::loadSimply).collect(Collectors.toList());
	}
	
	private List<TiersCategoryDto> getTiersCategories(TiersDto tiers){
		Example<TiersCategoryDto> example = Example.of(new TiersCategoryDto());
		example.getProbe().setTiersCode(tiers.getTiersCode());
		return tiersCategoryDao.findAll(example);
	}
	
	private void synchCategoriesHierarchie(TiersDto tiers, TiersCategoryHierarchieBean beansHierarchie) {
		List<TiersCategoryDto> tiersCategories = getTiersCategories(tiers);
		List<TiersCategoryDto> selectedTiersCategories = tiersCategoryBinding.reduceToSelectedTiersCategories(tiers, beansHierarchie);
		
		for (TiersCategoryDto tiersCategory : selectedTiersCategories) {
			if( !tiersCategories.contains(tiersCategory) ) {
				tiersCategoryService.save(tiersCategory);
			}
		}

		for (TiersCategoryDto tiersCategory : tiersCategories) {
			if( !selectedTiersCategories.contains(tiersCategory) ) {
				tiersCategoryService.delete(tiersCategory);
			}
		}		
	}

	public List<Message> save(TiersBean bean) {
		TiersDto dto = tiersBinding.mapToDto(bean);
		List<Message> messages = new ArrayList<>();
		
		switch (bean.getAction()) {
		case CREATE:
			dto = save(dto);
			synchCategoriesHierarchie(dto, bean.getCategoriesHierarchie());
			messages.add(Message.success(CREATE_SUCCESS));
			break;

		case UPDATE:
			dto = save(dto);
			synchCategoriesHierarchie(dto, bean.getCategoriesHierarchie());
			messages.add(Message.success(UPDATE_SUCCESS));
			break;

		case CHANGE_STATUS:
			dto = save(dto);
			messages.add(Message.success(CHANGE_STS_SUCCESS));
			break;

		case DELETE:
			tiersDao.delete(dto);
			messages.add(Message.success(DELETE_SUCCESS));
			break;
		default:
		}

		return messages;
	}
	
	private TiersBean loadSimply(TiersDto dto) {
		return load(dto, false);
	}
	
	private TiersBean load(TiersDto dto, boolean fullLoad) {
		return tiersBinding.mapToBean(dto, Map.of(TiersMapper.OPTION_CATEGORY_KEY, fullLoad));
	}

	private TiersBean loadFully(TiersDto dto) {
		return load(dto, true);
	}
	
	public TiersBean load(String tiersCode) {
		return tiersDao.findById(tiersCode).map(this::loadFully).orElseThrow();
	}

	public List<TiersCategoryHierarchieBean> loadChildrenCategory(String categoryCode) {
		Example<CategoryDto> ex = Example.of(new CategoryDto());
		ex.getProbe().setParent(new CategoryDto());
		ex.getProbe().getParent().setCategoryCode(categoryCode);		
		return categoryDao.findAll(ex).stream().map(tiersCategoryBinding::mapToBean).toList();
	}

}
