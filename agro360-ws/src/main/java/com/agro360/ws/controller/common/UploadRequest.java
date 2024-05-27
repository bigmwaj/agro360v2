package com.agro360.ws.controller.common;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UploadRequest implements Serializable {

	private static final long serialVersionUID = -6112491437108222035L;
	
	@NotNull
	private MultipartFile file;
	
}
