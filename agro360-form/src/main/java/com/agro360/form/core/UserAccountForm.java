package com.agro360.form.core;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.core.UserAccountBean;
import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.bean.stock.MagasinSearchBean;
import com.agro360.form.common.MetadataBeanName;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.core.PartnerOperation;
import com.agro360.operation.logic.core.UserAccountOperation;
import com.agro360.operation.logic.stock.MagasinOperation;
import com.agro360.vd.common.ClientOperationEnumVd;
import com.agro360.vd.core.UserAccountStatusEnumVd;

@Component
public class UserAccountForm {

	@Autowired
	private UserAccountOperation operation;	
	
	@Autowired
	private PartnerOperation partnerOperation;	
	
	@Autowired
	private MagasinOperation magasinOperation;	
	
	public UserAccountBean initLoginFormBean(ClientContext ctx) {
		var bean = new UserAccountBean();
		return bean;
	}
	
	@MetadataBeanName("core/user-account")
	public UserAccountBean initCreateFormBean(ClientContext ctx, String partnerCode) {
		var bean = new UserAccountBean();
		var partner = partnerOperation.findPartnerByCode(ctx, partnerCode);
		bean.setPartner(partner);
		initMagasinOption(ctx, bean.getMagasin()::setValueOptions);
		bean.getStatus().setValue(UserAccountStatusEnumVd.ENABLED);
		bean.setAction(ClientOperationEnumVd.CREATE);
		return bean;
	}
	
	@MetadataBeanName("core/user-account")
	public UserAccountBean initEditFormBean(ClientContext ctx, String partnerCode) {
		var bean = operation.findUserAccountByCode(ctx, partnerCode);
		var partner = partnerOperation.findPartnerByCode(ctx, partnerCode);
		initMagasinOption(ctx, bean.getMagasin()::setValueOptions);
		bean.setPartner(partner);
		bean.getPassword().setValue(null);
		bean.setAction(ClientOperationEnumVd.UPDATE);
		
		return bean;
	}

	@MetadataBeanName("core/user-account")
	public UserAccountBean initChangeStatusFormBean(ClientContext ctx, String partnerCode, Optional<UserAccountStatusEnumVd> status) {
		var bean = operation.findUserAccountByCode(ctx, partnerCode);
		var partner = partnerOperation.findPartnerByCode(ctx, partnerCode);
		bean.getPassword().setValue(null);
		bean.getStatusDate().setValue(LocalDateTime.now());
		bean.getStatus().setValue(status.orElse(null));
		bean.setPartner(partner);
		bean.setAction(ClientOperationEnumVd.CHANGE_STATUS);
		return bean;
	}
	
	private void initMagasinOption(ClientContext ctx, Consumer<Map<Object, String>> valueOptionsSetter) {
		Function<MagasinBean, Object> codeFn = e -> e.getMagasinCode().getValue();
		Function<MagasinBean, String> libelleFn = e -> e.getDescription().getValue();
		
		var options = magasinOperation.findMagasinsByCriteria(ctx, new MagasinSearchBean())
				.stream().collect(Collectors.toMap(codeFn, libelleFn));
		
		valueOptionsSetter.accept(options);
	}

}
