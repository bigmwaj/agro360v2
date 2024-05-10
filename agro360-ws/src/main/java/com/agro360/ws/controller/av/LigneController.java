package com.agro360.ws.controller.av;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.agro360.bo.bean.av.LigneBean;
import com.agro360.bo.bean.av.LigneTaxeBean;
import com.agro360.bo.bean.stock.ArticleSearchBean;
import com.agro360.form.av.LigneForm;
import com.agro360.service.av.LigneService;
import com.agro360.service.stock.ArticleService;
import com.agro360.service.stock.InventaireService;
import com.agro360.vd.av.CommandeTypeEnumVd;
import com.agro360.vd.stock.ArticleTypeEnumVd;
import com.agro360.ws.controller.common.AbstractController;

@RestController()
@RequestMapping("/achat-vente/commande/ligne")
public class LigneController extends AbstractController {

	@Autowired
	private LigneService service;
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private InventaireService inventaireService;
	
	@Autowired
	private LigneForm form;

	@GetMapping("/taxe")
	public ResponseEntity<List<LigneTaxeBean>> getTaxesAction(
			@RequestParam String commandeCode, 
			@RequestParam Optional<Long> ligneId,
			@RequestParam String articleCode) {		
		var ctx = getClientContext();
		var taxes = service.findTaxes(ctx, commandeCode, ligneId, articleCode);
		return ResponseEntity.ok(taxes);
	}
	
	@PostMapping("/refresh-prix")
	public ResponseEntity<LigneBean> refreshLignePrixAction(
			@RequestParam CommandeTypeEnumVd type,
			@RequestBody LigneBean ligne) {		
		var ctx = getClientContext();
		ligne = service.initialiserPrixCalcules(ctx, type, ligne);
		return ResponseEntity.ok(ligne);
	}	
	
	@GetMapping(CREATE_FORM_RN)
	public ResponseEntity<ModelMap> getLigneCreateFormAction(
			@RequestParam CommandeTypeEnumVd type,
			@RequestParam String commandeCode, 
			@RequestParam Optional<String> magasinCode,
			@RequestParam Optional<String> alias) {		
		var ctx = getClientContext();
		var form = this.form.initCreateFormBean(ctx, type, commandeCode, magasinCode, alias);
		var model = new ModelMap(MESSAGES_MODEL_KEY, ctx.getMessages());
		model.addAttribute(RECORD_MODEL_KEY, form);
		return ResponseEntity.ok(model);
	}
	
	@GetMapping("/article-option")
	public ResponseEntity<Map<Object, String>> getLigneArticleOptionsAction(
			@RequestParam ArticleTypeEnumVd type) {		
		var ctx = getClientContext();
		var searchBean = new ArticleSearchBean();
		searchBean.getType().setValue(type);
		searchBean.setPageSize(null);
		var options = articleService.searchAsOptions(ctx, searchBean);
		return ResponseEntity.ok(options);
	}
	
	@GetMapping("/variant-option")
	public ResponseEntity<Map<Object, String>> getLigneVariantOptionsAction(
			@RequestParam String articleCode) {		
		var ctx = getClientContext();
		var options = articleService.getVariantArticleAsOption(ctx, articleCode);
		return ResponseEntity.ok(options);
	}
	
	@GetMapping("/unite-option")
	public ResponseEntity<Map<Object, String>> getLigneUniteOptionsAction(
			@RequestParam String articleCode) {		
		var ctx = getClientContext();
		var options = articleService.getUniteArticleAsOption(ctx, articleCode);
		return ResponseEntity.ok(options);
	}
	
	@GetMapping("/prix-unitaire")
	public ResponseEntity<BigDecimal> getLignePrixUnitaireAction(
			@RequestParam CommandeTypeEnumVd type,
			@RequestParam String magasinCode,
			@RequestParam String articleCode,
			@RequestParam String variantCode,
			@RequestParam String uniteCode) {		
		var ctx = getClientContext();
		BigDecimal pu;
		if( CommandeTypeEnumVd.ACHAT.equals(type)) {
			pu = inventaireService.getPrixUnitaireAchat(ctx, magasinCode, articleCode, variantCode, uniteCode);
		}else if( CommandeTypeEnumVd.VENTE.equals(type)) {
			pu = inventaireService.getPrixUnitaireVente(ctx, magasinCode, articleCode, variantCode, uniteCode);
		}else {
			throw new RuntimeException(String.format("Le type de commande %s n'est pas pris en charge", type));
		}
		return ResponseEntity.ok(pu);
	}
	
}
