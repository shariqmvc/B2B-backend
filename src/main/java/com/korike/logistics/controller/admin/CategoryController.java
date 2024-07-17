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
import com.korike.logistics.entity.Category;
import com.korike.logistics.entity.SuperCategory;
import com.korike.logistics.exception.ResourceNotFoundException;
import com.korike.logistics.model.CategoryDto;
import com.korike.logistics.repository.CategoryRepository;
import com.korike.logistics.repository.SuperCategoryRepository;
import com.korike.logistics.service.CatService;
import com.korike.logistics.service.SupCatService;

@RestController
@RequestMapping("/api/admin")
public class CategoryController {

	 private static Logger logger = Logger.getLogger(CategoryController.class);
	 
	 @Autowired
     SupCatService supCatService;
	 @Autowired
     CatService catService;
     @Autowired
     SuperCategoryRepository supCatRepository;
     @Autowired
     CategoryRepository catRepository;
     
    @RequestMapping(value={"/category/{cat_id}","/category"}, method  = RequestMethod.POST)
 	public ResponseEntity<?> createUpdateCat(@PathVariable(required=false) Long cat_id, @RequestParam( required = false, name="action") String action,@RequestBody CategoryDto catReq) throws JsonProcessingException{
 		logger.info("Started createUpdateCat() method ");
 		Map<String, Object> model = new HashMap<>();
 		
 		if("add".equals(action)) {
 			if(catReq.getName() == null || catReq.getName().isEmpty()) {
				throw new InvalidParameterException("Category name is missing");
			}
 			if(catReq.getSuperCatId() == null || catReq.getSuperCatId().isEmpty()) {
				throw new InvalidParameterException("SuperCategoryId superCatId is missing");
			}
 			SuperCategory fetchedSupCat = supCatRepository.findBySuperCatId(Long.valueOf(catReq.getSuperCatId()));
 			if(fetchedSupCat == null) {
	        	logger.error("Exception occured in CategoryController() method.");
				throw new ResourceNotFoundException("Invalid supCatId "+catReq.getSuperCatId());
	        }
 			catReq = catService.createCat(catReq, fetchedSupCat);
 			
 			model.put("cat_id", catReq.getCatId());
			model.put("job_id","default");
			model.put("statusDetail", "Category created successfully");
 		}else if("edit".equals(action)) {
 			if(catReq.getName() == null || catReq.getName().isEmpty()) {
				throw new InvalidParameterException("Category name is missing");
			}
 			if(catReq.getSuperCatId() == null || catReq.getSuperCatId().isEmpty()) {
				throw new InvalidParameterException("SuperCategoryId superCatId is missing");
			}
 			SuperCategory fetchedSupCat = supCatRepository.findBySuperCatId(Long.valueOf(catReq.getSuperCatId()));
 			if(fetchedSupCat == null) {
	        	logger.error("Exception occured in CategoryController() method.");
				throw new ResourceNotFoundException("Invalid supCatId "+catReq.getSuperCatId());
	        }
 			
 			Category fetchedCat = catRepository.findByCatId(cat_id);
 			if(fetchedCat == null) {
	        	logger.error("Exception occured in CategoryController() method.");
				throw new ResourceNotFoundException("Invalid catId "+cat_id);
	        }
 			catReq = catService.updateCat(catReq, fetchedSupCat, fetchedCat);
 			
 			model.put("cat_id", catReq.getCatId());
			model.put("job_id","default");
			model.put("statusDetail", "Category updated successfully");
 		}else if("query".equals(action)) {
            List<CategoryDto> catList = catService.getCategoryByFilter(catReq);
			
			model.put("cat_list", catList);
			model.put("job_id","default");
			model.put("statusDetail", "Found "+catList.size()+" categories");
 		}
 		return ok(model);
     }
}
