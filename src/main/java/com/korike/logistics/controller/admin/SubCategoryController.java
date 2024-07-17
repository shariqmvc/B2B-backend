package com.korike.logistics.controller.admin;

import static org.springframework.http.ResponseEntity.ok;

import java.security.InvalidParameterException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.korike.logistics.entity.Category;
import com.korike.logistics.entity.SubCategory;
import com.korike.logistics.entity.SuperCategory;
import com.korike.logistics.exception.ResourceNotFoundException;
import com.korike.logistics.model.CategoryDto;
import com.korike.logistics.model.SubCategoryDto;
import com.korike.logistics.repository.CategoryRepository;
import com.korike.logistics.repository.SubCategoryRepository;
import com.korike.logistics.service.CatService;
import com.korike.logistics.service.SubCatService;

@RestController
@RequestMapping("/api/admin")
public class SubCategoryController {

	private static Logger logger = Logger.getLogger(SubCategoryController.class);
	
	 @Autowired
     CatService catService;
	 @Autowired
	 SubCatService subCatService;
	 @Autowired
	 CategoryRepository catRepository;
	 @Autowired
	 SubCategoryRepository subCatRepository;
	 
	 @RequestMapping(value={"/subCategory/{sub_cat_id}","/subCategory"}, method  = RequestMethod.POST)
	 	public ResponseEntity<?> createUpdateSubCat(@PathVariable(required=false) Long sub_cat_id, @RequestParam( required = false, name="action") String action,@RequestBody SubCategoryDto subCatReq) throws JsonProcessingException{
	 		logger.info("Started createUpdateSubCat() method ");
	 		Map<String, Object> model = new HashMap<>();
	 		
	 		if("add".equals(action)) {
	 			if(subCatReq.getName() == null || subCatReq.getName().isEmpty()) {
					throw new InvalidParameterException("SubCategory name is missing");
				}
	 			if(subCatReq.getCatId() == null || subCatReq.getCatId().isEmpty()) {
					throw new InvalidParameterException("CategoryId superCatId is missing");
				}
	 			
	 			Category fetchedCat = catRepository.findByCatId(Long.valueOf(subCatReq.getCatId()));
	 			if(fetchedCat == null) {
		        	logger.error("Exception occured in CategoryController() method.");
					throw new ResourceNotFoundException("Invalid catId "+subCatReq.getCatId());
	 			}
	 			
	 			subCatReq = subCatService.createSubCategory(subCatReq, fetchedCat);
	 			
	 			model.put("sub_cat_id", subCatReq.getSubCatId());
				model.put("job_id","default");
				model.put("statusDetail", "SubCategory created successfully");
		        
	 		}else if("edit".equals(action)) {
	 			if(subCatReq.getName() == null || subCatReq.getName().isEmpty()) {
					throw new InvalidParameterException("SubCategory name is missing");
				}
	 			if(subCatReq.getCatId() == null || subCatReq.getCatId().isEmpty()) {
					throw new InvalidParameterException("CategoryId superCatId is missing");
				}
	 			
	 			Optional<SubCategory> fetchedSubCat = subCatRepository.findById(sub_cat_id);
	 			if(!fetchedSubCat.isPresent()) {
		        	logger.error("Exception occured in CategoryController() method.");
					throw new ResourceNotFoundException("Invalid subCatId "+sub_cat_id);
	 			}
	 			
	 			Category fetchedCat = catRepository.findByCatId(Long.valueOf(subCatReq.getCatId()));
	 			if(fetchedCat == null) {
		        	logger.error("Exception occured in CategoryController() method.");
					throw new ResourceNotFoundException("Invalid catId "+subCatReq.getCatId());
	 			}
	 			
	 			subCatReq = subCatService.updateSubCategory(subCatReq, fetchedCat, fetchedSubCat.get());
	 			
	 			model.put("sub_cat_id", subCatReq.getSubCatId());
				model.put("job_id","default");
				model.put("statusDetail", "SubCategory updated successfully");
	 		}else if("query".equals(action)) {
	 			List<SubCategoryDto> subCatList = subCatService.getSubCategoryByFilter(subCatReq);
				
				model.put("sub_cat_list", subCatList);
				model.put("job_id","default");
				model.put("statusDetail", "Found "+subCatList.size()+" subCategories");
	 		}
	 		return ok(model);
	 }
}
