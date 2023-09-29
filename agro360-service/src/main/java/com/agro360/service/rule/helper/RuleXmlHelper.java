package com.agro360.service.rule.helper;

import java.io.IOException;

import javax.xml.stream.XMLResolver;
import javax.xml.stream.XMLStreamException;

import com.ctc.wstx.api.WstxInputProperties;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class RuleXmlHelper {

	public BeanMetadataHelper loadMetadataFromXml(String xmlName)  throws IOException{
		var xml = getClass().getClassLoader().getResourceAsStream( "rules/" + xmlName + ".xml");
		var xmlMapper = new XmlMapper();
		
		xmlMapper.getFactory().getXMLInputFactory().setProperty(
		    WstxInputProperties.P_UNDECLARED_ENTITY_RESOLVER,
		    new XMLResolver() {
		        @Override
		        public Object resolveEntity(String publicId, String systemId, String baseUri, String ns) throws XMLStreamException {
		            // replace the entity with a string of your choice, e.g.
		            switch (ns) {
		                case "nbsp": 
		                    return " ";
		                default: 
		                    return "";
		            }
		            // some useful tool is org.apache.commons.text.StringEscapeUtils
		            // e.g.
		            // return StringEscapeUtils.escapeXml10(StringEscapeUtils.unescapeHtml4('&' + ns + ';'));
		        }
		    }
		);
		
		return xmlMapper.readValue(xml, BeanMetadataHelper.class);
	}
}
