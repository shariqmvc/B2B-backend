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
import com.korike.logistics.entity.ItemPackageCategory;
import com.korike.logistics.entity.SubCategory;
import com.korike.logistics.exception.ResourceNotFoundException;
import com.korike.logistics.model.ItemPackageCategoryDto;
import com.korike.logistics.model.SubCategoryDto;
import com.korike.logistics.model.SuperCategoryDto;
import com.korike.logistics.repository.ItemPackageCategoryRepository;
import com.korike.logistics.service.ItemPackCatService;

@RestController
@RequestMapping("/api/admin")
public class ItemPackCatController {

	private static Logger logger = Logger.getLogger(SubCategoryController.class);
	
	@Autowired
	ItemPackCatService itemPackCatService;
	@Autowired
	ItemPackageCategoryRepository itemPackCatRepository;
	
	@RequestMapping(value={"/itemPackCategory/{item_pack_cat_id}","/itemPackCategory"}, method  = RequestMethod.POST)
	public ResponseEntity<?> createUpdateItemPackCat(@PathVariable(required=false) Long item_pack_cat_id, @RequestParam( required = false, name="action") String action,@RequestBody ItemPackageCategoryDto itemPackCatReq) throws JsonProcessingException{
		logger.info("Started createUpdateItemPackCat() method ");
		Map<String, Object> model = new HashMap<>();
		
		if("add".equals(action)) {
			if(itemPackCatReq.getName() == null || itemPackCatReq.getName().isEmpty()) {
				throw new InvalidParameterException("itemPackageName name is missing");
			}
			
			itemPackCatReq = itemPackCatService.createItemPackCat(itemPackCatReq);
			
			model.put("item_pack_cat_id", itemPackCatReq.getItemPackageCategoryId());
			model.put("job_id","default");
			model.put("statusDetail", "itemPackage Category created successfully");
		}else if("edit".equals(action)) {
			if(itemPackCatReq.getName() == null || itemPackCatReq.getName().isEmpty()) {
				throw new InvalidParameterException("itemPackageName name is missing");
			}
			
			Optional<ItemPackageCategory> fetchedItemPackCat = itemPackCatRepository.findById(item_pack_cat_id);
 			if(!fetchedItemPackCat.isPresent()) {
	        	logger.error("Exception occured in ItemPackCatController() method.");
				throw new ResourceNotFoundException("Invalid itemPackageId "+item_pack_cat_id);
 			}
			
			itemPackCatReq = itemPackCatService.updateItemPackCat(itemPackCatReq, fetchedItemPackCat.get());
			
			model.put("item_pack_cat_id", itemPackCatReq.getItemPackageCategoryId());
			model.put("job_id","default");
			model.put("statusDetail", "itemPackage Category updated successfully");
		}else if("query".equals(action)) {
			List<ItemPackageCategoryDto> itemPackCatList = itemPackCatService.getItemPackCatByFilter(itemPackCatReq);
			
			model.put("item_pack_cat_list", itemPackCatList);
			model.put("job_id","default");
			model.put("statusDetail", "Found "+itemPackCatList.size()+" itemPackCats");
 		}
 		return ok(model);
	}
}
