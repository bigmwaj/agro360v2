package com.agro360.form.av;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.agro360.bo.bean.av.RetourLigneBean;
import com.agro360.bo.mapper.StockMapper;
import com.agro360.form.common.MetadataBeanName;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.av.LigneOperation;
import com.agro360.operation.logic.stock.ConversionOperation;
import com.agro360.vd.av.RetourStatusEnumVd;
import com.agro360.vd.common.ClientOperationEnumVd;

@Component
public class RetourLigneForm extends AbstractPostCommandeEntityForm{

	@Autowired
	private LigneOperation ligneOperation;
	
	@Autowired
	private ConversionOperation conversionOperation;
	
	@Override
	protected ConversionOperation getConversionOperation() {
		return conversionOperation;
	}
	
	@Override
	protected LigneOperation getLigneOperation() {
		return ligneOperation;
	}
	
	@MetadataBeanName("av/retour")
	public RetourLigneBean initCreateFormBean(ClientContext ctx, String commandeCode) {

		var ligneOption = getLigneOptions(ctx, commandeCode);
		var bean = new RetourLigneBean();
		bean.getPrixUnitaire().setValue(BigDecimal.ZERO);
		bean.getLigne().getLigneId().setValueOptions(ligneOption);
		
		bean.setAction(ClientOperationEnumVd.CREATE);
		
		bean.getQuantite().setValue(Double.valueOf(1));
		bean.getStatus().setValue(RetourStatusEnumVd.BRLN);
		bean.getDate().setValue(LocalDateTime.now());
		
		return bean;
	}
	
	@MetadataBeanName("av/retour")
	public List<RetourLigneBean> initUpdateFormBean(ClientContext ctx, String commandeCode, List<RetourLigneBean> beans) {
		var ligneOptions = getLigneOptions(ctx, commandeCode);
		
		for (var bean : beans) {
			var unite = bean.getLigne().getUnite();
			var option = Map.ofEntries(StockMapper.asOption(unite));
			bean.getUnite().getUniteCode().setValueOptions(option);
			bean.getLigne().getUnite().getUniteCode().setValueOptions(option);
			bean.setAction(ClientOperationEnumVd.UPDATE);
			bean.getLigne().getLigneId().setValueOptions(ligneOptions);
		}
		
		return beans;
	}

}
