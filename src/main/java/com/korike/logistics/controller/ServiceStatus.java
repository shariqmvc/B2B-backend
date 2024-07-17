package com.korike.logistics.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korike.logistics.controller.user.CustomerOrderController;
import com.korike.logistics.entity.CustomerOrderDetails;
import com.korike.logistics.entity.Items;
import com.korike.logistics.model.items.ItemImageWrapper;
import com.korike.logistics.model.items.ItemsDetails;
import com.korike.logistics.model.orders.OrderResponseDto;
import com.korike.logistics.repository.CustomerOrderRepository;
import com.korike.logistics.repository.ItemRepository;
import com.korike.logistics.service.CustomerOrderService;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.script.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class ServiceStatus {

    @Autowired
    CustomerOrderRepository customerOrderRepository;
    @Autowired
    CustomerOrderService customerOrdService;
    @Autowired
    ItemRepository itemRepository;

    //private static Logger logger = Logger.getLogger(ServiceStatus.class);

    @GetMapping("/service-status/{s_id}")
    public ResponseEntity<?> getItems(@PathVariable(required=true) String s_id) throws JsonProcessingException, JSONException, FileNotFoundException, ScriptException {
//        StringWriter writer = new StringWriter();
//        ScriptContext context = new SimpleScriptContext();
//        context.setWriter(writer);
//
//        ScriptEngineManager manager = new ScriptEngineManager();
//        ScriptEngine engine = manager.getEngineByName("python3");
//        Object response = engine.eval(new FileReader("/home/ubuntu/korike-app/get_service_info.py"), context);
//        //logger.info("Service Status Response", (Throwable) writer.toString().trim());
//        Map<String, Object> model = new HashMap<>();
//        model.put("Korike", writer.toString().trim());
//        return ok(model);

        //logger.info("Started getItemListByService method ");
        ObjectMapper mapper = new ObjectMapper();
        List<Items> fetchedItems = itemRepository.getItemsByServiceId(s_id);
        ItemsDetails newItemDto;
        List<ItemsDetails> itemDtoList = new ArrayList();
        if (!fetchedItems.isEmpty()) {
            for (Items item : fetchedItems) {
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
                itemDtoList.add(newItemDto);

            }
        }
        Map<String, Object> model = new HashMap<>();
        model.put("details", itemDtoList);
        model.put("success", "true");
        model.put("statusDetail", "Found " + itemDtoList.size() + " items");
        return ok(model);
    }
}
