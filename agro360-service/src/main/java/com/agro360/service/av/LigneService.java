package com.agro360.service.av;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.bean.av.LigneTaxeBean;
import com.agro360.bo.bean.stock.ArticleTaxeBean;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.stock.ArticleTaxeOperation;
import com.agro360.service.common.AbstractService;
import com.agro360.vd.av.CommandeTypeEnumVd;

@Transactional(rollbackFor = Exception.class)
@Service
public class LigneService extends AbstractService {
	
	@Autowired
	private ArticleTaxeOperation articleTaxeOperation;
	
	@Autowired
	private LigneServiceHelper ligneServiceHelper;
	
	public List<LigneTaxeBean> findTaxes(ClientContext ctx, String commandeCode, Optional<Long> ligneId,
			String articleCode) {
//		if( ligneId.filter(e -> !e.equals(0L)).isPresent()) {
//			return ligneTaxeOperation.findLigneTaxesLigne(ctx, commandeCode, ligneId.get());
//		}else {
//		}
		return articleTaxeOperation.findArticleTaxesByArticleCode(ctx, articleCode)
				.stream()
				.map(this::map)
				.collect(Collectors.toList());
	}


	public LigneBean initialiserPrixCalcules(ClientContext ctx, CommandeTypeEnumVd type, LigneBean ligne) {
		ligneServiceHelper.initChampsCalcules(ctx, type, ligne);
		return ligne;		
	}
	
	private LigneTaxeBean map(ArticleTaxeBean bean) {
		var lt = new LigneTaxeBean();
		lt.setTaxe(bean.getTaxe());
		lt.getTaux().setValue(bean.getTaxe().getTaux().getValue());
		return lt;
	}
	
}
