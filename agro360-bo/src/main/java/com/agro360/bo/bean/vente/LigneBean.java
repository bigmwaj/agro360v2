package com.agro360.bo.bean.vente;

import java.util.Objects;

import com.agro360.bo.bean.common.AbstractLigneBean;
import com.agro360.bo.bean.stock.MagasinBean;
import com.agro360.bo.metadata.FieldMetadata;
import com.agro360.bo.utils.TypeScriptInfos;
import com.agro360.dto.vente.LigneDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
@TypeScriptInfos(igroreSuperClassParam = true)
public class LigneBean extends AbstractLigneBean<LigneDto> {

	private static final long serialVersionUID = 1647090333349627006L;

	private MagasinBean magasin = new MagasinBean();

	private FieldMetadata<String> casierCode = new FieldMetadata<>("Casier");

	private FieldMetadata<Boolean> nonFacturable = new FieldMetadata<>("Non Facturable?");

	private FieldMetadata<Boolean> nonEmballee = new FieldMetadata<>("Non Emballée?");

	private FieldMetadata<Boolean> nonCartonnee = new FieldMetadata<>("Non Cartonnée?");
	
	public void setMagasin(MagasinBean magasin) {
		Objects.requireNonNull(magasin);
		this.magasin = magasin;
	}
}
