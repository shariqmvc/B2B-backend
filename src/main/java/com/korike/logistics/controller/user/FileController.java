package com.korike.logistics.controller.user;

import static org.springframework.http.ResponseEntity.ok;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.route53domains.model.InvalidInputException;
import com.korike.logistics.service.impl.AWSS3ServiceImpl;



@RestController
@RequestMapping("/api")
public class FileController {
	private AWSS3ServiceImpl amazonClient;
	
	@Autowired
	FileController(AWSS3ServiceImpl amazonClient) {
        this.amazonClient = amazonClient;
    }
	
	@RequestMapping(value={"/upload"}, method  = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
		if(file.isEmpty()) {
			throw new InvalidInputException("Image/File is missing");
		}
		
		String uploadUrl = amazonClient.uploadFile(file);
		Map<String, Object> model = new HashMap<>();
        model.put("details", uploadUrl);
        model.put("success","true");
        
        return ok(model);
	}
}
