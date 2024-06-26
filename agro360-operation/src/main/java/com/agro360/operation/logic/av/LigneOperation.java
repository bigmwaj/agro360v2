package com.agro360.operation.logic.av;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agro360.bo.bean.av.CommandeBean;
import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.mapper.AchatVenteMapper;
import com.agro360.dao.av.ILigneDao;
import com.agro360.dao.common.IDao;
import com.agro360.dao.stock.IArticleDao;
import com.agro360.dao.stock.IUniteDao;
import com.agro360.dao.stock.IVariantDao;
import com.agro360.dto.av.LigneDto;
import com.agro360.operation.context.ClientContext;
import com.agro360.operation.logic.common.AbstractOperation;
import com.agro360.operation.utils.RuleNamespace;
import com.agro360.vd.av.LigneTypeEnumVd;

@Service
public class LigneOperation extends AbstractOperation<LigneDto, Long> {

	@Autowired
	ILigneDao dao;
	
	@Autowired
	IArticleDao articleDao;
	
	@Autowired
	IUniteDao uniteDao;
	
	@Autowired
	IVariantDao variantDao;

	@Override
	protected IDao<LigneDto, Long> getDao() {
		return dao;
	}
	
	@RuleNamespace("av/commande/ligne/create")
	public void createLigne(ClientContext ctx, CommandeBean commande, LigneBean bean) {
		var dto = new LigneDto();
		dto.setCommandeCode(commande.getCommandeCode().getValue());		
		
		var type = bean.getType().getValue();
		
		if( LigneTypeEnumVd.ARTC.equals(type) || LigneTypeEnumVd.ARTC.equals(type)) {
			
			var uniteCode = bean.getUnite().getUniteCode().getValue();
			var unite = uniteDao.getReferenceById(uniteCode);
			dto.setUnite(unite);
			
			var articleCode = bean.getArticle().getArticleCode().getValue();
			var article = articleDao.getReferenceById(articleCode);
			dto.setArticle(article);
			
			setDtoValue(dto::setVariantCode, bean.getVariantCode());
		}
		
		setDtoValue(dto::setType, bean.getType());
		setDtoValue(dto::setDescription, bean.getDescription());
		setDtoValue(dto::setQuantite, bean.getQuantite());
		setDtoValue(dto::setPrixUnitaire, bean.getPrixUnitaire());
		setDtoValue(dto::setRemiseType, bean.getRemiseType());
		setDtoValue(dto::setRemiseTaux, bean.getRemiseTaux());
		setDtoValue(dto::setRemiseMontant, bean.getRemiseMontant());
		setDtoValue(dto::setRemiseRaison, bean.getRemiseRaison());

		setDtoValue(dto::setPrixTotal, bean.getPrixTotal());
		setDtoValue(dto::setPrixTotalHT, bean.getPrixTotalHT());
		setDtoValue(dto::setTaxe, bean.getTaxe());
		setDtoValue(dto::setRemise, bean.getRemise());
		
		super.save(ctx, dto);

		var msgTpl = "Ligne %s créée avec succès";
		ctx.success(String.format(msgTpl, toString(bean)));
		
		 dto.getLigneId();
		 bean.getLigneId().setValue(dto.getLigneId());
	}
	
	private String toString(LigneBean bean) {
		var article = bean.getArticle().getArticleCode().getValue();
		if( article == null || article.isEmpty() ) {
			article = bean.getDescription().getValue();
		}
		var variant = bean.getVariantCode().getValue();
		if( variant != null && !variant.isEmpty() ) {
			article += "/" + variant;
		}
		return article;
	}

	@RuleNamespace("av/commande/ligne/update")
	public void updateLigne(ClientContext ctx, CommandeBean commande, LigneBean bean) {

		var dto = findLigneDto(commande, bean);
		
		var type = bean.getType().getValue();
		
		if( LigneTypeEnumVd.ARTC.equals(type) || LigneTypeEnumVd.SSTD.equals(type)) {
			var uniteCode = bean.getUnite().getUniteCode().getValue();
			if( dto.getUnite() == null || !dto.getUnite().getUniteCode().equals(uniteCode)) {
				var unite = uniteDao.getReferenceById(uniteCode);
				dto.setUnite(unite);
			}
			
			var articleCode = bean.getArticle().getArticleCode().getValue();
			if( dto.getArticle() == null || !dto.getArticle().getArticleCode().equals(articleCode)) {
				var article = articleDao.getReferenceById(articleCode);
				dto.setArticle(article);				
			}
			
			setDtoChangedValue(dto::setVariantCode, bean.getVariantCode());
		}
		
		setDtoChangedValue(dto::setType, bean.getType());
		setDtoChangedValue(dto::setDescription, bean.getDescription());
		setDtoChangedValue(dto::setQuantite, bean.getQuantite());
		setDtoChangedValue(dto::setPrixUnitaire, bean.getPrixUnitaire());
		setDtoChangedValue(dto::setRemiseType, bean.getRemiseType());
		setDtoChangedValue(dto::setRemiseTaux, bean.getRemiseTaux());
		setDtoChangedValue(dto::setRemiseMontant, bean.getRemiseMontant());
		setDtoChangedValue(dto::setRemiseRaison, bean.getRemiseRaison());		

		setDtoChangedValue(dto::setPrixTotal, bean.getPrixTotal());
		setDtoChangedValue(dto::setPrixTotalHT, bean.getPrixTotalHT());
		setDtoChangedValue(dto::setTaxe, bean.getTaxe());
		
		super.save(ctx, dto);
		

		var msgTpl = "Ligne %s modifiée avec succès";
		ctx.success(String.format(msgTpl, toString(bean)));
	}

	public void deleteLigne(ClientContext ctx, CommandeBean commande, LigneBean bean) {
		var dto = findLigneDto(commande, bean);
		var msgTpl = "Ligne %s supprimée avec succès";
		ctx.success(String.format(msgTpl, toString(bean)));
		dao.delete(dto);
	}
	
	public void deleteLignesCommande(ClientContext ctx, CommandeBean bean) {
		var lignes = findLignesCommande(ctx, bean.getCommandeCode().getValue());
		for (LigneBean ligne : lignes) {
			deleteLigne(ctx, bean, ligne);
		}
	}

	public LigneBean findLigneByCode(ClientContext ctx, String commandeCode, Long ligneId) {
		return AchatVenteMapper.map(findLigneDto(commandeCode, ligneId));
	}

	public List<LigneBean> findLignesCommande(ClientContext ctx, String commandeCode) {
		return dao.findAllByCommandeCode(commandeCode)
				.stream().map(AchatVenteMapper::map)
				.collect(Collectors.toList());
	}
	
	private LigneDto findLigneDto(String commandeCode, Long ligneId) {
		return dao.findOneByCommandeCodeAndLigneId(commandeCode, ligneId)
				.orElseThrow();
	}
	
	private LigneDto findLigneDto(CommandeBean commande, LigneBean bean) {
		var commandeCode = commande.getCommandeCode().getValue();
		var ligneId = bean.getLigneId().getValue();
		return findLigneDto(commandeCode, ligneId);
	}
}
