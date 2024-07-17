package com.korike.logistics.service.impl;

import java.security.InvalidParameterException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.korike.logistics.common.ItemSizes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korike.logistics.entity.Category;
import com.korike.logistics.entity.ItemPackageCategory;
import com.korike.logistics.entity.Items;
import com.korike.logistics.entity.Partner;
import com.korike.logistics.entity.ServiceDefinition;
import com.korike.logistics.entity.SubCategory;
import com.korike.logistics.entity.SuperCategory;
import com.korike.logistics.exception.ApiErrorCode;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.model.items.ItemImageWrapper;
import com.korike.logistics.model.items.ItemsDetails;
import com.korike.logistics.model.services.Details;
import com.korike.logistics.repository.ItemRepository;
import com.korike.logistics.service.ItemService;
import com.korike.logistics.util.CommonUtils;


@Service
public class ItemServiceImpl implements ItemService {
    private static Logger logger = Logger.getLogger(ItemServiceImpl.class);

    @Autowired
    ItemRepository itemsRepo;

    @Override
    public ItemsDetails createItem(ItemsDetails itemsDto, Partner servicePartner, ServiceDefinition serviceDefinition, SuperCategory superCat, Category category, SubCategory subCategory, ItemPackageCategory itemPackCat) {

    	ObjectMapper mapper = new ObjectMapper();
    	
        Items items = new Items();
        items.setItem_id(CommonUtils.generateOrdNum());
        items.setItem_title(itemsDto.getItem_title());
        items.setItem_description(itemsDto.getItem_description());
        items.setItemSuperCategory(superCat);
        items.setItemCategory(category);
        items.setItemSubCategory(subCategory);
        items.setTax(itemsDto.getTax());
        items.setHsnNumber(itemsDto.getHsnNumber());
     //   items.setSubCategory(itemsDto.getSubCategory());
        items.setIsActive(itemsDto.getActive());
        items.setIsPreOrder(itemsDto.getPreOrder());
        items.setIsQuotation(itemsDto.getQuotation());
        items.setItemBaseCost(itemsDto.getItemBaseCost());
        items.setItem_base_quantity(itemsDto.getItem_base_quantity());
        items.setItemMinOrder(itemsDto.getItemMinOrder());
        items.setItemUnit(itemsDto.getItemUnit());
        items.setItemMaxCost(itemsDto.getItemMaxCost());
        items.setItemRetailCost(itemsDto.getItemRetailCost());
        items.setServiceDefinition(serviceDefinition);
        items.setItemPackCat(itemPackCat);
        items.setInvoicePath(itemsDto.getInvoicePath());
        if(null!=itemsDto.getItemSize()) {
            for (ItemSizes size : ItemSizes.values()) {
                if (itemsDto.getItemSize().equalsIgnoreCase(size.toString())) {
                    items.setItemSize(itemsDto.getItemSize());
                }
            }
        }
        
        if(servicePartner != null) {
        	items.setServicePartner(servicePartner);
        }
        
        items.setOrigin(itemsDto.getOrigin());
        items.setType(itemsDto.getType());
        try {
        	ItemImageWrapper ImageWrapper = new ItemImageWrapper();
        	ImageWrapper.setImagePaths(itemsDto.getItemImagePath());
			items.setItemImagePath(mapper.writeValueAsString(ImageWrapper));
		} catch (JsonProcessingException exc) {
		
			logger.error("Exception occured in createItem() method." + CommonUtils.printException(exc));
	        throw new KorikeException("Exception occured in createItem() method while converting pojo to json "+ ApiErrorCode.INTERNAL_SERVER_ERROR);
		}
        items.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        items.setCreatedBy(CommonUtils.getCurrentUserName());

        try {
            items = itemsRepo.save(items);
            ItemsDetails itemResponseDto = new ItemsDetails();
            itemResponseDto.setItem_id(items.getItem_id());
            itemResponseDto.setServiceId(items.getServiceDefinition().getServiceId());
            if(items.getServicePartner() != null){
            	itemResponseDto.setPartnerId(items.getServicePartner().getPartnerId());
            }
            
            return itemResponseDto;
        }catch(Exception exc) {
            logger.error("Exception occured in createItem() method." + CommonUtils.printException(exc));
            throw new KorikeException("Exception occured in createItem() method while creating "+ ApiErrorCode.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    public ItemsDetails updateItem(ItemsDetails itemsDto, Items fetchedItemDetails, Partner servicePartner, ServiceDefinition serviceDefinition, SuperCategory superCat, Category category, SubCategory subCategory, ItemPackageCategory itemPackCat) {
    	ObjectMapper mapper = new ObjectMapper();
    	
        fetchedItemDetails.setItem_title(itemsDto.getItem_title());
        fetchedItemDetails.setItem_description(itemsDto.getItem_description());
        fetchedItemDetails.setItemSuperCategory(superCat);
        fetchedItemDetails.setItemCategory(category);
        fetchedItemDetails.setItemSubCategory(subCategory);
        fetchedItemDetails.setItemPackCat(itemPackCat);
        fetchedItemDetails.setTax(itemsDto.getTax());
        fetchedItemDetails.setHsnNumber(itemsDto.getHsnNumber());
    //    fetchedItemDetails.setSubCategory(itemsDto.getSubCategory());
        fetchedItemDetails.setIsActive(itemsDto.getActive());
        fetchedItemDetails.setIsPreOrder(itemsDto.getPreOrder());
        fetchedItemDetails.setIsQuotation(itemsDto.getQuotation());
        fetchedItemDetails.setItemBaseCost(itemsDto.getItemBaseCost());
        fetchedItemDetails.setItemRetailCost(itemsDto.getItemRetailCost());
        fetchedItemDetails.setItemWholeSaleCost(itemsDto.getItemWholeSaleCost());
        fetchedItemDetails.setItem_base_quantity(itemsDto.getItem_base_quantity());
        fetchedItemDetails.setItemMinOrder(itemsDto.getItemMinOrder());
        fetchedItemDetails.setItemUnit(itemsDto.getItemUnit());
        fetchedItemDetails.setInvoicePath(itemsDto.getInvoicePath());
        //fetchedItemDetails.setItemPackCat(itemPackCat);
        if(servicePartner != null) {
            fetchedItemDetails.setServicePartner(servicePartner);
        }
        for (ItemSizes size : ItemSizes.values()) {
            if(itemsDto.getItemSize().equalsIgnoreCase(size.toString())){
                fetchedItemDetails.setItemSize(itemsDto.getItemSize());
            }
        }
        try {
        	ItemImageWrapper imageWrapper = new ItemImageWrapper();
        	imageWrapper.setImagePaths(itemsDto.getItemImagePath());
			fetchedItemDetails.setItemImagePath(mapper.writeValueAsString(imageWrapper));
		} catch (JsonProcessingException exc) {
			logger.error("Exception occured in updateItem() method." + CommonUtils.printException(exc));
	        throw new KorikeException("Exception occured in updateItem() method while converting pojo to json "+ ApiErrorCode.INTERNAL_SERVER_ERROR);
		}
        fetchedItemDetails.setItemMinOrder(itemsDto.getItemMinOrder());
        fetchedItemDetails.setServiceDefinition(serviceDefinition);
        fetchedItemDetails.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
        fetchedItemDetails.setLastUpdatedBy(CommonUtils.getCurrentUserName());

        try {
            fetchedItemDetails = itemsRepo.save(fetchedItemDetails);
            ItemsDetails itemResponseDto = new ItemsDetails();
            itemResponseDto.setItem_id(fetchedItemDetails.getItem_id());
            itemResponseDto.setServiceId(fetchedItemDetails.getServiceDefinition().getServiceId());
            if(servicePartner != null) {
            	itemResponseDto.setPartnerId(fetchedItemDetails.getServicePartner().getPartnerId());
            }
            
            return itemResponseDto;
        }catch(Exception exc) {
            logger.error("Exception occured in updateItem() method." + CommonUtils.printException(exc));
            throw new KorikeException("Exception occured in updateItem() method while creating "+ ApiErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ItemsDetails deleteItem(ItemsDetails itemsDto, Partner servicePartner, ServiceDefinition serviceDefinition) {
        return null;
    }


    @Override
    public List<ItemsDetails> getItemsByFilter(ItemsDetails itemsDto) throws JsonMappingException, JsonProcessingException {
    	ObjectMapper mapper = new ObjectMapper();
    	
        List<Items> itemList = new ArrayList();
        try {
            itemList = itemsRepo.getItemsByFilter(itemsDto.getFilter().getServiceId());
        }catch(Exception exc) {
            logger.error(exc.getLocalizedMessage());
            throw new InvalidParameterException("Internal server error");
        }

        ItemsDetails newItemDto;
        List<ItemsDetails> itemDtoList = new ArrayList();
        if(!itemList.isEmpty()) {
            for(Items item : itemList) {
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
                newItemDto.setPartnerId(item.getServicePartner().getPartnerId());
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
                newItemDto.setInvoicePath(item.getInvoicePath());
                newItemDto.setItemSize(item.getItemSize());
                itemDtoList.add(newItemDto);
            }


        }


        return itemDtoList;
    }


}
