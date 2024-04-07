package com.agro360.operation.logic.production.avicole.helper;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.agro360.bo.bean.production.avicole.MetadataBean;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import lombok.Data;

@Service
public class MetadataHelper {
	
	public List<MetadataBean> findMetadatasFromXmlModel() throws IOException {
		var xml = getClass().getClassLoader().getResourceAsStream("cycle-metadata-model.xml");
		var xmlMapper = new XmlMapper();
		return xmlMapper.readValue(xml, Models.class).metadata
				.stream()
				.map(this::mapToBean)
				.collect(Collectors.toList());
	}
	
	private MetadataBean mapToBean(Metadata metadata) {
		var bean = new MetadataBean();
		bean.getMetadataCode().setValue(metadata.code);
		bean.getValue().setValue(metadata.value);
		bean.getDescription().setValue(metadata.description);
		bean.getLibelle().setValue(metadata.libelle);
		bean.getType().setValue(metadata.inputType);
		bean.getOrdre().setValue(metadata.ordre);
		return bean;
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	@Data
	static class Models {
		@JacksonXmlElementWrapper(useWrapping = false)
		List<Metadata> metadata;
	}

	@Data
	static class Metadata {
		String code;
		short ordre;
		String inputType;
		String libelle;
		String description;
		String value;
		
		@JacksonXmlElementWrapper(localName = "estimationSalaires")
		List<EstimationSalaire> estimationSalaire;
		
		@JacksonXmlElementWrapper(localName = "estimationConsommationAliments")
		List<EstimationConsommationAliment> estimationConsommationAliment;
		
		@JacksonXmlElementWrapper(localName = "estimationVarieteAliments")
		List<EstimationVarieteAliment> estimationVarieteAliment;
		
		@JacksonXmlElementWrapper(localName = "estimationProphylactiques")
		List<EstimationProphylactique> estimationProphylactique;
	}
	
	@Data
	static class EstimationSalaire {
		double taux;
		double salaire;
		String role;
	}

	@Data
	static class EstimationConsommationAliment {
		String debut;
		String fin;
		double quantite;
	}
	
	@Data
	static class EstimationVarieteAliment {
		String debut;
		String fin;
		String variete;
		double pu;
	}
	
	@Data
	static class EstimationProphylactique {
		String type;
		String semaine;
		String libelle;
		double quantite;
		double pu;
	}
}
