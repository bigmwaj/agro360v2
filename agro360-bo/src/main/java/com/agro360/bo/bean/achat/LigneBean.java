package com.agro360.bo.bean.achat;

import com.agro360.bo.bean.common.AbstractLigneBean;
import com.agro360.bo.utils.TypeScriptInfos;
import com.agro360.dto.achat.LigneDto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = false)
@TypeScriptInfos(igroreSuperClassParam = true)
public class LigneBean extends AbstractLigneBean<LigneDto> {

	private static final long serialVersionUID = 1647090333349627006L;
}
