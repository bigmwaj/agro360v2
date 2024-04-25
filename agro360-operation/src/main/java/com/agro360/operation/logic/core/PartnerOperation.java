package com.agro360.operation.logic.core;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.core.PartnerBean;
import com.agro360.bo.bean.core.PartnerSearchBean;
import com.agro360.bo.mapper.CoreMapper;
import com.agro360.dao.common.IDao;
import com.agro360.dao.core.IPartnerDao;
import com.agro360.dto.core.PartnerDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.metadata.BeanMetadataConfig;
import com.agro360.operation.utils.RuleNamespace;

@Service
public class PartnerOperation extends AbstractOperation<PartnerDto, String> {

	@Autowired
	private IPartnerDao dao;

	@Override
	protected IDao<PartnerDto, String> getDao() {
		return dao;
	}
	
	@RuleNamespace("core/partner/create")
	public PartnerBean createPartner(ClientContext ctx, PartnerBean bean) {
		var dto = new PartnerDto();
		
		setDtoValue(dto::setPartnerCode, bean.getPartnerCode());
		setDtoValue(dto::setAddress, bean.getAddress());
		setDtoValue(dto::setCity, bean.getCity());
		setDtoValue(dto::setCountry, bean.getCountry());
		setDtoValue(dto::setEmail, bean.getEmail());
		setDtoValue(dto::setPhone, bean.getPhone());
		setDtoValue(dto::setFirstName, bean.getFirstName());
		setDtoValue(dto::setLastName, bean.getLastName());
		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setName, bean.getName());
		setDtoValue(dto::setType, bean.getType());
		setDtoValue(dto::setTitle, bean.getTitle());		
		
		dto = super.save(dto);		
		return CoreMapper.map(dto);	
	}
	
	@Qualifier("core/partner")
	@Autowired()
	BeanMetadataConfig beanConfig;
	
	@RuleNamespace("core/partner/update")
	public PartnerBean updatePartner(ClientContext ctx, PartnerBean bean) {
		var dto = dao.getReferenceById(bean.getPartnerCode().getValue());

		setDtoChangedValue(dto::setAddress, bean.getAddress());
		setDtoChangedValue(dto::setCity, bean.getCity());
		setDtoChangedValue(dto::setCountry, bean.getCountry());
		setDtoChangedValue(dto::setEmail, bean.getEmail());
		setDtoChangedValue(dto::setPhone, bean.getPhone());
		setDtoChangedValue(dto::setFirstName, bean.getFirstName());
		setDtoChangedValue(dto::setLastName, bean.getLastName());
		setDtoChangedValue(dto::setName, bean.getName());
		setDtoChangedValue(dto::setType, bean.getType());
		setDtoChangedValue(dto::setTitle, bean.getTitle());
		
		dto = super.save(dto);		
		return CoreMapper.map(dto);
	}
	
	private void changePartnerStatus(ClientContext ctx, PartnerBean bean) {
		var dto = dao.getReferenceById(bean.getPartnerCode().getValue());

		setDtoValue(dto::setStatus, bean.getStatus());
		setDtoValue(dto::setStatusDate, bean.getStatusDate());
		
		dto = super.save(dto);		
	}
	
	@RuleNamespace("core/partner/deactivate")
	public void deactivatePartner(ClientContext ctx, PartnerBean bean) {
		changePartnerStatus(ctx, bean);	
	}
	
	@RuleNamespace("core/partner/activate")
	public void activatePartner(ClientContext ctx, PartnerBean bean) {
		changePartnerStatus(ctx, bean);	
	}
	
	@RuleNamespace("core/partner/delete")
	public void deletePartner(ClientContext ctx, PartnerBean bean) {
		var dto = dao.getReferenceById(bean.getPartnerCode().getValue());
		dao.delete(dto);
	}
	
	public PartnerBean findPartnerByCode(ClientContext ctx, String partnerCode) {
		var dto = dao.getReferenceById(partnerCode);
		return CoreMapper.map(dto);		
	}
	
	@RuleNamespace("core/partner/search")
	public List<PartnerBean> findPartnersByCriteria(ClientContext ctx, PartnerSearchBean searchBean) {
		getLogger().debug("Find Partner by criteria ... ");
		var probe = new PartnerDto();
		var matcher = ExampleMatcher.matchingAll();

		if (searchBean.getPartnerCode() != null) {
			probe.setPartnerCode(searchBean.getPartnerCode().getValue());
			matcher = matcher.withMatcher("partnerCode", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		}

		if (searchBean.getPartnerName() != null) {
//			probe.setName(bean.getPartnerName().getValue());
			probe.setFirstName(searchBean.getPartnerName().getValue());
//			probe.setLastName(bean.getPartnerName().getValue());

//			matcher = matcher.withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
			matcher = matcher.withMatcher("firstName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
//			matcher = matcher.withMatcher("lastName", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		}

		if (searchBean.getEmail() != null) {
			probe.setEmail(searchBean.getEmail().getValue());
			matcher = matcher.withMatcher("email", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
		}

		if (searchBean.getPhone() != null) {
			probe.setPhone(searchBean.getPhone().getValue());
			matcher = matcher.withMatcher("phone", ExampleMatcher.GenericPropertyMatchers.endsWith());
		}

//		if (searchBean.getStatusIn() != null) {
//			probe.setStatus((PartnerStatusEnumVd) searchBean.getStatusIn().getValue());
//		}

		if (searchBean.getType() != null) {
			probe.setType(searchBean.getType().getValue());
		}
		var example = Example.of(probe, matcher);
		return dao.findAll(example).stream().map(CoreMapper::map).collect(Collectors.toList());	
	}

}
