package com.agro360.dao.core;

import org.springframework.stereotype.Repository;

import com.agro360.dao.common.IDao;
import com.agro360.dto.core.CodeGenetorDto;

@Repository
public interface ICodeGeneratorDao extends IDao<CodeGenetorDto, String>{
	
	default String generateCommandeCode() {
		return generateCode(getReferenceById("COMMANDE"));
	}
	
	default String generateTransactionCode() {
		return generateCode(getReferenceById("TRANSACTION"));
	}
	
	default String generateFactureCode() {
		return generateCode(getReferenceById("FACTURE"));
	}
	
	default String generateCode(CodeGenetorDto dto) {
		var code = dto.getPrefix() + padLeftZeros(dto.getSequence().toString(), 4);
		dto.setSequence(dto.getSequence() + 1);
		save(dto);
		return code;
	}
	
	default String padLeftZeros(String inputString, long length) {
	    if (inputString.length() >= length) {
	        return inputString;
	    }
	    StringBuilder sb = new StringBuilder();
	    while (sb.length() < length - inputString.length()) {
	        sb.append('0');
	    }
	    sb.append(inputString);

	    return sb.toString();
	}
}
