package com.agro360.service.logic.production.avicole;

import static com.agro360.service.mapper.production.avicole.JourneeMapper.OPTION_MAP_PRODUCTION_KEY;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.dao.common.IDao;
import com.agro360.dao.production.avicole.ICycleDao;
import com.agro360.dto.production.avicole.CycleDto;
import com.agro360.service.bean.production.avicole.JourneeBean;
import com.agro360.service.bean.production.avicole.JourneeSearchBean;
import com.agro360.service.logic.common.AbstractService;
import com.agro360.service.mapper.production.avicole.JourneeMapper;
import com.agro360.service.message.Message;
import com.agro360.vd.common.EditActionEnumVd;

@Service
public class JourneeService extends AbstractService<CycleDto, String>{

	@Autowired
	private JourneeMapper mapper;
	
	@Autowired
	private ICycleDao cycleDao;
	
	@Override
	protected IDao<CycleDto, String> getDao() {
		// TODO Auto-generated method stub
		return null;
	}

	protected String getRulePath() {
		return "production/avicole/journee";
	}
	
	public JourneeSearchBean initSearchFormBean() {
		var bean = mapper.mapToSearchBean();
		return bean;
	}
	
	public List<JourneeBean> search(JourneeSearchBean searchBean) {
		var cycle = this.cycleDao.getReferenceById(searchBean.getCycleCode().getValue());
		var journee = mapper.mapToBean(cycle, searchBean.getJournee().getValue(), Map.of(OPTION_MAP_PRODUCTION_KEY, true));
		return Collections.singletonList(applyInitCreateRules(journee));
	}
	
	public List<Message> save(JourneeBean bean) {
		if( bean.getAction() == null ) {
			return Collections.singletonList(Message.error("Aucune action sélectionnée"));
		}
		
		if(  EditActionEnumVd.SYNC.equals(bean.getAction())) {
			return Collections.emptyList();
		}
		
		List<Message> messages = new ArrayList<>();

		return messages;
	}
}
