package com.korike.logistics.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.korike.logistics.entity.*;
import com.korike.logistics.model.items.ItemsDetails;

public interface ItemService {

    public ItemsDetails createItem(ItemsDetails itemsDto, Partner servicePartner, ServiceDefinition serviceDefinition, SuperCategory superCat, Category category, SubCategory subCategory,ItemPackageCategory itemPackCat);
    public ItemsDetails updateItem(ItemsDetails itemsDto, Items fetchedItemsDetails, Partner servicePartner, ServiceDefinition serviceDefinition, SuperCategory superCat, Category category, SubCategory subCategory, ItemPackageCategory itemPackCat);
    public ItemsDetails deleteItem(ItemsDetails itemsDto, Partner servicePartner, ServiceDefinition serviceDefinition);
    public List<ItemsDetails> getItemsByFilter(ItemsDetails itemsDto) throws JsonMappingException, JsonProcessingException;
    //public List<Items> getAllItemsByService(String serviceId) throws JsonMappingException, JsonProcessingException;

}
