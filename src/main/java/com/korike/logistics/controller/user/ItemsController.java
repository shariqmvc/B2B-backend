package com.korike.logistics.controller.user;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korike.logistics.entity.*;
import com.korike.logistics.exception.ResourceNotFoundException;
import com.korike.logistics.model.ItemTypes;
import com.korike.logistics.model.items.ItemImageWrapper;
import com.korike.logistics.model.items.ItemsDetails;
import com.korike.logistics.model.items.ItemsResponseDto;
import com.korike.logistics.model.services.Details;
import com.korike.logistics.repository.*;
import com.korike.logistics.service.ItemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/partner")
public class ItemsController {
    private static Logger logger = Logger.getLogger(ItemsController.class);

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ItemService itemService;
    @Autowired
    ServicePartnerRepository servicePartnerRepository;

    @Autowired
    ServiceDefinitionRepository serviceDefinitionRepository;
    @Autowired
    SuperCategoryRepository superCategoryRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    SubCategoryRepository subCategoryRepository;
    @Autowired
    ItemPackageCategoryRepository itemPackCatRepository;

    @RequestMapping(value={"/items/{item_id}","/items"}, method  = RequestMethod.POST)
    public ResponseEntity<?> createUpdateItems(@PathVariable(required=false) String item_id, @RequestParam( required = false, name="action") String action, @RequestBody ItemsDetails itemsDto) throws JsonProcessingException {



        ItemsDetails itemsDtoResponse = new ItemsDetails();
        Map<String, Object> model = new HashMap<>();
        List<ItemsDetails> itemList = new ArrayList();
        if("add".equals(action)) {
            if(itemsDto.getItem_title() == null && itemsDto.getItem_title().isEmpty()) {
                logger.error("Item title missing");
                throw new InvalidParameterException("Item title missing");
            }
            if(itemsDto.getCategory() == null && itemsDto.getCategory().isEmpty()) {
                logger.error("Item category missing");
                throw new InvalidParameterException("Item category missing");
            }
//            if(itemsDto.getPartnerId() == null && itemsDto.getPartnerId().isEmpty()) {
//                logger.error("Partner Id missing");
//                throw new InvalidParameterException("Partner Id missing");
//            }
            if(itemsDto.getServiceId() == null || itemsDto.getServiceId().isEmpty()) {
                logger.error("Service Id missing");
                throw new InvalidParameterException("Service Id missing");
            }

            String superCatName = itemsDto.getSuperCategory();
            String catName = itemsDto.getCategory();
            String subCatName = itemsDto.getSubCategory();
            String itemPackCatName = itemsDto.getItemPackCat();
            String serviceDefintionId = itemsDto.getServiceId();
            String partnerId = itemsDto.getPartnerId();
            Optional<Partner> fetchedServicePartner = null;
            if(!itemsDto.getType().equalsIgnoreCase("PostOrder")) {
                if (null != partnerId) {
                    fetchedServicePartner = servicePartnerRepository.findById(partnerId);
                    if (!fetchedServicePartner.isPresent()) {
                        logger.error("Invalid partnerId " + partnerId);
                        throw new ResourceNotFoundException("Invalid partnerId " + partnerId);
                    }
                }
            }
            Optional<ServiceDefinition> fetchedServiceDefinition = serviceDefinitionRepository.findById(serviceDefintionId);
            if(!fetchedServiceDefinition.isPresent()) {
                logger.error("Invalid sId "+serviceDefintionId);
                throw new ResourceNotFoundException("Invalid sId "+serviceDefintionId);
            }
            SuperCategory fetchedSuperCat = null;
            if(null!=superCatName) {
                fetchedSuperCat = superCategoryRepository.findByName(superCatName);
                if (fetchedSuperCat == null) {
                    logger.error("Invalid superCatId " + superCatName);
                    throw new ResourceNotFoundException("Invalid superCatId " + superCatName);
                }
            }

            Category fetchedCat = categoryRepository.findByName(catName);
            if(fetchedCat==null) {
                logger.error("Invalid catId "+catName);
                throw new ResourceNotFoundException("Invalid catId "+catName);
            }
            SubCategory fetchedSubCat = null;
            if(null!=subCatName) {
                fetchedSubCat = subCategoryRepository.findByName(subCatName);
                if (fetchedSubCat == null) {
                    logger.error("Invalid subCatId " + subCatName);
                    throw new ResourceNotFoundException("Invalid catId " + subCatName);
                }
            }

            ItemPackageCategory fetchedItemPackCat = null;
            if(null!=itemPackCatName) {
                fetchedItemPackCat = itemPackCatRepository.findByName(itemPackCatName);
                if (fetchedItemPackCat == null) {
                    logger.error("Invalid subCatId " + itemPackCatName);
                    throw new ResourceNotFoundException("Invalid itemPackCatId " + itemPackCatName);
                }
            }
            if(null!=partnerId && !partnerId.isEmpty()) {
                itemsDtoResponse = itemService.createItem(itemsDto, fetchedServicePartner.get(), fetchedServiceDefinition.get(), fetchedSuperCat, fetchedCat, fetchedSubCat, fetchedItemPackCat);
                model.put("details", itemsDtoResponse);
                model.put("success", "true");
            }
            else{
                itemsDtoResponse = itemService.createItem(itemsDto, null, fetchedServiceDefinition.get(), fetchedSuperCat, fetchedCat, fetchedSubCat, fetchedItemPackCat);
                model.put("details", itemsDtoResponse);
                model.put("success", "true");
            }
        }



        else if("edit".equals(action)){
            if(itemsDto.getItem_title() == null && itemsDto.getItem_title().isEmpty()) {
                logger.error("Item title missing");
                throw new InvalidParameterException("Item title missing");
            }
            if(itemsDto.getCategory() == null && itemsDto.getCategory().isEmpty()) {
                logger.error("Item category missing");
                throw new InvalidParameterException("Item category missing");
            }
//            if(itemsDto.getPartnerId() == null && itemsDto.getPartnerId().isEmpty()) {
//                logger.error("Partner Id missing");
//                throw new InvalidParameterException("Partner Id missing");
//            }
            if(itemsDto.getServiceId() == null || itemsDto.getServiceId().isEmpty()) {
                logger.error("Service Id missing");
                throw new InvalidParameterException("Service Id missing");
            }
            String item = itemsDto.getItem_id();
            String partnerId = itemsDto.getPartnerId();
            String serviceDefintionId = itemsDto.getServiceId();
            String superCatName = itemsDto.getSuperCategory();
            String catName = itemsDto.getCategory();
            String subCatName = itemsDto.getSubCategory();
            String itemPackCatName = itemsDto.getItemPackCat();
            Optional<Partner> fetchedServicePartner = null;
            if(null!=partnerId && !partnerId.isEmpty()) {
                servicePartnerRepository.findById(partnerId);
                if (!fetchedServicePartner.isPresent()) {
                    logger.error("Invalid partnerId " + partnerId);
                    throw new ResourceNotFoundException("Invalid partnerId " + partnerId);
                }
            }

            Optional<ServiceDefinition> fetchedServiceDefinition = serviceDefinitionRepository.findById(serviceDefintionId);
            if(!fetchedServiceDefinition.isPresent()) {
                logger.error("Invalid sId "+serviceDefintionId);
                throw new ResourceNotFoundException("Invalid sId "+serviceDefintionId);
            }
            SuperCategory fetchedSuperCat = null;
            if(null!=superCatName) {
                fetchedSuperCat = superCategoryRepository.findByName(superCatName);
                if (fetchedSuperCat == null) {
                    logger.error("Invalid superCatId " + superCatName);
                    throw new ResourceNotFoundException("Invalid superCatId " + superCatName);
                }
            }

            Category fetchedCat = categoryRepository.findByName(catName);
            if(fetchedCat==null) {
                logger.error("Invalid catId "+catName);
                throw new ResourceNotFoundException("Invalid catId "+catName);
            }

            SubCategory fetchedSubCat = null;
            if(null!=subCatName) {
                fetchedSubCat = subCategoryRepository.findByName(subCatName);
                if (fetchedSubCat == null) {
                    logger.error("Invalid subCatId " + subCatName);
                    throw new ResourceNotFoundException("Invalid catId " + subCatName);
                }
            }


            ItemPackageCategory fetchedItemPackCat = null;
            if(null!=itemPackCatName) {
                fetchedItemPackCat = itemPackCatRepository.findByName(itemPackCatName);
                if (fetchedItemPackCat == null) {
                    logger.error("Invalid subCatId " + itemPackCatName);
                    throw new ResourceNotFoundException("Invalid itemPackCatId " + itemPackCatName);
                }
            }

            Optional<Items> fetchedItems = itemRepository.findById(item_id);
            if(!fetchedItems.isPresent()){
                logger.error("Invalid item_id " +item_id);
                throw new ResourceNotFoundException("Invalid item_id " +item_id);
            }
            if(null!=partnerId && !partnerId.isEmpty()) {
                itemsDtoResponse = itemService.updateItem(itemsDto, fetchedItems.get(), fetchedServicePartner.get(), fetchedServiceDefinition.get(), fetchedSuperCat, fetchedCat, fetchedSubCat, fetchedItemPackCat);
                model.put("details", itemsDtoResponse);
                model.put("success", "true");
            }
            else{
                itemsDtoResponse = itemService.createItem(itemsDto, null, fetchedServiceDefinition.get(), fetchedSuperCat, fetchedCat, fetchedSubCat, fetchedItemPackCat);
                model.put("details", itemsDtoResponse);
                model.put("success", "true");
            }

        }else if("query".equals(action)) {

             /*    String partnerId = itemsDto.getPartnerId();
                 String serviceDefintionId = itemsDto.getServiceId();
                 Optional<Partner> fetchedServicePartner = servicePartnerRepository.findById(partnerId);
                 if(!fetchedServicePartner.isPresent()) {
                 	logger.error("Invalid partnerId "+partnerId);
         			throw new ResourceNotFoundException("Invalid partnerId "+partnerId);
                 }

                 Optional<ServiceDefinition> fetchedServiceDefinition = serviceDefinitionRepository.findById(Long.valueOf(serviceDefintionId));
                 if(!fetchedServiceDefinition.isPresent()) {
                 	logger.error("Invalid sId "+serviceDefintionId);
         			throw new ResourceNotFoundException("Invalid sId "+serviceDefintionId);
                 }  */
            //itemsDto.getFilter().setItem_id(item_id);
            itemList = itemService.getItemsByFilter(itemsDto);
            model.put("details", itemList);
            model.put("success","true");
        }



        if("add".equals(action)) {
            model.put("statusDetail", "Created Item "+itemsDtoResponse.getItem_id());
        }else if("edit".equals(action)){
            model.put("statusDetail", "Updated Item "+itemsDtoResponse.getItem_id());
        }else {
            model.put("statusDetail", "Found  "+itemList.size()+" items");
        }

        return ok(model);

    }
    @GetMapping("/items/service/{serviceId}")
    public ResponseEntity<?> getItems(@PathVariable(required=false) String serviceId) throws JsonProcessingException {

        logger.info("Started getItemListByService method ");
        ObjectMapper mapper = new ObjectMapper();
        List<Items> fetchedItems = itemRepository.getItemsByServiceId(serviceId);
        ItemsDetails newItemDto;
        List<ItemsDetails> itemDtoList = new ArrayList();
        if(!fetchedItems.isEmpty()) {
            for(Items item : fetchedItems) {
                newItemDto = new ItemsDetails();
                newItemDto.setItem_id(item.getItem_id());
                newItemDto.setItem_title(item.getItem_title());
                newItemDto.setItemBaseCost(item.getItemBaseCost());
                newItemDto.setItemRetailCost(item.getItemRetailCost());
                newItemDto.setItemWholeSaleCost(item.getItemWholeSaleCost());
                newItemDto.setItemMaxCost(item.getItemMaxCost());
                newItemDto.setItemMinOrder(item.getItemMinOrder());
                newItemDto.setItemUnit(item.getItemUnit());
                ItemImageWrapper imageWrapper = mapper.readValue(item.getItemImagePath(), ItemImageWrapper.class);
                newItemDto.setItemImagePath(imageWrapper.getImagePaths());
                newItemDto.setOrigin(item.getOrigin());
                if(null!=item.getServicePartner()) {
                    newItemDto.setPartnerId(item.getServicePartner().getPartnerId());
                }
                newItemDto.setPreOrder(item.getIsPreOrder());
                newItemDto.setQuotation(item.getIsQuotation());
                newItemDto.setServiceId(item.getServiceDefinition().getServiceId());
                newItemDto.setItemPackCat(String.valueOf(item.getItemPackCat().getItemPackageCategoryId()));
                newItemDto.setTax(item.getTax());
                newItemDto.setHsnNumber(item.getHsnNumber());
                //      newItemDto.setSubCategory(item.getSubCategory());
                newItemDto.setType(item.getType());
                newItemDto.setActive(item.getIsActive());
                newItemDto.setSuperCategory(String.valueOf(item.getItemSuperCategory().getSuperCatId()));
                newItemDto.setCategory(String.valueOf(item.getItemCategory().getCatId()));
                newItemDto.setSubCategory(String.valueOf(item.getItemSubCategory().getSubCatId()));
                newItemDto.setIsActive(item.getIsActive());
                newItemDto.setItem_base_quantity(item.getItem_base_quantity());
                newItemDto.setItem_description(item.getItem_description());
                itemDtoList.add(newItemDto);

            }
        }

        Map<String, Object> model = new HashMap<>();
        model.put("details", itemDtoList);
        model.put("success","true");
        model.put("statusDetail", "Found "+itemDtoList.size()+" items");

        return ok(model);
    }

    @GetMapping("/items")
    public ResponseEntity<?> getItemsByIds(@RequestBody List<String> itemIds) throws JsonProcessingException {

        logger.info("Started getItemListByService method ");
        ObjectMapper mapper = new ObjectMapper();
        List<Items> fetchedItems = itemRepository.getItemsByItemIds(itemIds);
        ItemsDetails newItemDto;
        List<ItemsDetails> itemDtoList = new ArrayList();
        if(!fetchedItems.isEmpty()) {
            for(Items item : fetchedItems) {
                newItemDto = new ItemsDetails();
                newItemDto.setItem_id(item.getItem_id());
                newItemDto.setItem_title(item.getItem_title());
                newItemDto.setItemBaseCost(item.getItemBaseCost());
                newItemDto.setItemRetailCost(item.getItemRetailCost());
                newItemDto.setItemWholeSaleCost(item.getItemWholeSaleCost());
                newItemDto.setItemMaxCost(item.getItemMaxCost());
                newItemDto.setItemMinOrder(item.getItemMinOrder());
                newItemDto.setItemUnit(item.getItemUnit());
                ItemImageWrapper imageWrapper = mapper.readValue(item.getItemImagePath(), ItemImageWrapper.class);
                newItemDto.setItemImagePath(imageWrapper.getImagePaths());
                newItemDto.setOrigin(item.getOrigin());
                if (null!=item.getServicePartner()) {
                    newItemDto.setPartnerId(item.getServicePartner().getPartnerId());
                }
                newItemDto.setPreOrder(item.getIsPreOrder());
                newItemDto.setQuotation(item.getIsQuotation());
                newItemDto.setServiceId(item.getServiceDefinition().getServiceId());
                if(null!=item.getItemPackCat()) {
                    newItemDto.setItemPackCat(String.valueOf(item.getItemPackCat().getItemPackageCategoryId()));
                }
                newItemDto.setTax(item.getTax());
                newItemDto.setHsnNumber(item.getHsnNumber());
                //      newItemDto.setSubCategory(item.getSubCategory());
                newItemDto.setType(item.getType());
                newItemDto.setActive(item.getIsActive());
                if(null!=item.getItemSuperCategory()) {
                    newItemDto.setSuperCategory(String.valueOf(item.getItemSuperCategory().getSuperCatId()));
                }
                if(null!=item.getItemCategory()) {
                    newItemDto.setCategory(String.valueOf(item.getItemCategory().getCatId()));
                }
                if(null!=item.getItemSubCategory()) {
                    newItemDto.setSubCategory(String.valueOf(item.getItemSubCategory().getSubCatId()));
                }
                newItemDto.setIsActive(item.getIsActive());
                newItemDto.setItem_base_quantity(item.getItem_base_quantity());
                newItemDto.setItem_description(item.getItem_description());
                itemDtoList.add(newItemDto);

            }
        }

        Map<String, Object> model = new HashMap<>();
        model.put("details", itemDtoList);
        model.put("success","true");
        model.put("statusDetail", "Found "+itemDtoList.size()+" services");

        return ok(model);
    }


}
