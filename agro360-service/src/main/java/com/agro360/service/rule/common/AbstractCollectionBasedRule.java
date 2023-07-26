package com.agro360.service.rule.common;

import java.util.Map;

abstract public class AbstractCollectionBasedRule extends AbstractValidationRule
{
    public static final String PARAM_KEY = "RULES_KEY";
	
	private static final String MSG1 = String.format("Un paramètre de clé %s doit être défini", PARAM_KEY);
	
	private static final String MSG2 = String.format("Le paramètre de clé %s doit être de type Map<String, Map<String, Object>>", PARAM_KEY);

	private static final String MSG3 = String.format("Au moins une valeur pour le paramètre de clé %s doit être définit", PARAM_KEY);;
    
    @SuppressWarnings("unchecked")
	protected Map<String, Map<String, Object>> getParamValue(Map<String, Object> params) {
    	
    	if( !params.containsKey(PARAM_KEY) ){
    		throw new RuleException(MSG1);
    	}
    	
    	var _paramValue = params.get(PARAM_KEY);
    	
        if( !(_paramValue instanceof Map) )
        {
            throw new RuleException(MSG2);
        }
        
		var paramValue = (Map<String, Map<String, Object>>) _paramValue;
        
        if( paramValue.keySet().isEmpty() ) {
        	throw new RuleException(MSG3);
        }
    	return paramValue;
    }
}