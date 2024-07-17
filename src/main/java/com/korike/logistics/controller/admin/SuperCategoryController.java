package com.korike.logistics.controller.admin;

import static org.springframework.http.ResponseEntity.ok;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.korike.logistics.entity.SuperCategory;
import com.korike.logistics.exception.ResourceNotFoundException;
import com.korike.logistics.model.SuperCategoryDto;
import com.korike.logistics.repository.SuperCategoryRepository;
import com.korike.logistics.service.SupCatService;

@RestController
@RequestMapping("/api/admin")
public class SuperCategoryController {

     private static Logger logger = Logger.getLogger(SuperCategoryController.class);
     
     @Autowired
     SupCatService supCatService;
     @Autowired
     SuperCategoryRepository supCatRepository;
	
	@RequestMapping(value={"/superCategory/{super_cat_id}","/superCategory"}, method  = RequestMethod.POST)
	public ResponseEntity<?> createUpdateSupCat(@PathVariable(required=false) Long super_cat_id, @RequestParam( required = false, name="action") String action,@RequestBody SuperCategoryDto supCatReq) throws JsonProcessingException{
		logger.info("Started createUpdateSupCat() method ");
		Map<String, Object> model = new HashMap<>();
		
		if("add".equals(action)) {
			if(supCatReq.getName() == null || supCatReq.getName().isEmpty()) {
				throw new InvalidParameterException("SuperCategory name is missing");
			}
			supCatReq = supCatService.createSupCat(supCatReq);
			
			model.put("super_cat_id", supCatReq.getSuperCatId());
			model.put("job_id","default");
			model.put("statusDetail", "Super Category created successfully");
		}else if("edit".equals(action)) {
			if(supCatReq.getName() == null || supCatReq.getName().isEmpty()) {
				throw new InvalidParameterException("SuperCategory name is missing");
			}
			SuperCategory fetchedSupCat = supCatRepository.findBySuperCatId(super_cat_id);
			 if(fetchedSupCat == null) {
		        	logger.error("Exception occured in SuperCategoryController() method.");
					throw new ResourceNotFoundException("Invalid supCatId "+super_cat_id);
		        }
			supCatReq = supCatService.updateSupCat(supCatReq, fetchedSupCat);
			
			model.put("super_cat_id", supCatReq.getSuperCatId());
			model.put("job_id","default");
			model.put("statusDetail", "Super Category updated successfully");
		}else if("query".equals(action)) {
			List<SuperCategoryDto> supCatList = supCatService.getSupCatByFilter(supCatReq);
			
			model.put("super_cat_list", supCatList);
			model.put("job_id","default");
			model.put("statusDetail", "Found "+supCatList.size()+" superCategories");
		}
		
		return ok(model);
	}
}
