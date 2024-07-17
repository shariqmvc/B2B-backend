package com.korike.logistics.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korike.logistics.controller.user.UserController;
import com.korike.logistics.entity.BillingTemplate;
import com.korike.logistics.entity.Consumer;
import com.korike.logistics.entity.Invoice;
import com.korike.logistics.entity.Items;
import com.korike.logistics.model.InvoiceResDto;
import com.korike.logistics.model.UserInfoDto;
import com.korike.logistics.model.WholeSaleLimits;
import com.korike.logistics.model.items.ItemCostBreakUp;
import com.korike.logistics.model.items.ItemsDetails;
import com.korike.logistics.model.orders.OrderDetails;
import com.korike.logistics.model.orders.OrderItemsListDto;
import com.korike.logistics.model.orders.OrderReqDto;
import com.korike.logistics.model.orders.OrderResponseDto;
import com.korike.logistics.repository.BillingTemplateRepository;
import com.korike.logistics.repository.ConsumerRepository;
import com.korike.logistics.repository.ItemRepository;
import com.korike.logistics.repository.UserRepository;
import com.korike.logistics.util.PdfGen;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.korike.logistics.service.InvoiceService;

import java.io.OutputStream;
import java.util.*;

@Service
public class InvoiceServiceImpl implements InvoiceService{

    private static Logger logger = Logger.getLogger(InvoiceServiceImpl.class);

    @Autowired
    ItemRepository itemRepository;
    @Autowired
    ConsumerRepository consumerRepo;

    @Autowired
    BillingTemplateRepository billingTemplateRepository;

    @Override
    public InvoiceResDto generateInvoice(OrderResponseDto orderResponseDto) throws JsonProcessingException {

        ObjectMapper objMapper = new ObjectMapper();

        //Get Billing template for partner
        List<BillingTemplate> billingTemplate = billingTemplateRepository.getBillingTemplatesByPartnerId(orderResponseDto.getPartnerId());

        //Get Order details and break the fields
        OrderDetails orderDetails = orderResponseDto.getOrderDetails();

        //Get Item List
        List<OrderItemsListDto> itemsDetails = orderDetails.getItems();
        List<String> itemStringIds = new ArrayList<>();
        Map<String, Integer> itemOrderQuantity = new HashMap<>();
        for (int i = 0; i < itemsDetails.size(); i++) {
            itemStringIds.add(itemsDetails.get(i).getItemId());
            itemOrderQuantity.put(itemsDetails.get(i).getItemId(), itemsDetails.get(i).getItemQuantity());
        }

        //Get MRP or base cost + HSN tax
        List<Items> itemDetails = itemRepository.getItemsByItemIds(itemStringIds);
        Map<String, Long> itemCostMap = new HashMap<>();
        List<ItemCostBreakUp> itemCostBreakUpList = new ArrayList<>();


        //Get Item Mrp or base cost (after comparing with wholesale limits)
        for (int i = 0; i < itemDetails.size(); i++) {
            if (null != itemDetails.get(i).getMrp() && itemDetails.get(i).getMrp() >0) {
                ItemCostBreakUp itemCostBreakUp = new ItemCostBreakUp();
                itemCostBreakUp.setItemId(itemDetails.get(i).getItem_id());
                itemCostBreakUp.setItemCost(itemDetails.get(i).getMrp());
                itemCostBreakUp.setItemCount(itemsDetails.get(i).getItemQuantity());
                itemCostBreakUp.setItemTitle(itemsDetails.get(i).getItemName());
                itemCostBreakUpList.add(itemCostBreakUp);
                itemsDetails.get(i).setDesc(itemDetails.get(i).getItem_description());
                itemsDetails.get(i).setUnitPrice(itemDetails.get(i).getItemBaseCost());
            } else {
                ObjectMapper mapper = new ObjectMapper();
                WholeSaleLimits wholeSaleLimits = mapper.readValue(billingTemplate.get(0).getWholeSaleLimits(), WholeSaleLimits.class);
                if (null != wholeSaleLimits.getCount()) {
                    if (itemOrderQuantity.get(itemDetails.get(i).getItem_id()) >= wholeSaleLimits.getCount()) {
                        ItemCostBreakUp itemCostBreakUp = new ItemCostBreakUp();
                        itemCostBreakUp.setItemId(itemDetails.get(i).getItem_id());
                        itemCostBreakUp.setItemCost(itemDetails.get(i).getItemWholeSaleCost().longValue());
                        itemCostBreakUp.setItemCount(itemsDetails.get(i).getItemQuantity());
                        itemCostBreakUp.setItemTitle(itemsDetails.get(i).getItemName());
                        itemCostBreakUpList.add(itemCostBreakUp);
                    } else {
                        ItemCostBreakUp itemCostBreakUp = new ItemCostBreakUp();
                        itemCostBreakUp.setItemId(itemDetails.get(i).getItem_id());
                        itemCostBreakUp.setItemCost(itemDetails.get(i).getItemBaseCost().longValue());
                        itemCostBreakUp.setItemCount(itemsDetails.get(i).getItemQuantity());
                        itemCostBreakUp.setItemTitle(itemsDetails.get(i).getItemName());
                        itemCostBreakUpList.add(itemCostBreakUp);
                    }
                }
                if (null != wholeSaleLimits.getDistance()) {
                    if (itemOrderQuantity.get(itemDetails.get(i).getItem_id()) >= wholeSaleLimits.getDistance()) {
                        ItemCostBreakUp itemCostBreakUp = new ItemCostBreakUp();
                        itemCostBreakUp.setItemId(itemDetails.get(i).getItem_id());
                        itemCostBreakUp.setItemCost(itemDetails.get(i).getItemWholeSaleCost().longValue());
                        itemCostBreakUp.setItemCount(itemsDetails.get(i).getItemQuantity());
                        itemCostBreakUp.setItemTitle(itemsDetails.get(i).getItemName());
                        itemCostBreakUpList.add(itemCostBreakUp);
                    } else {
                        ItemCostBreakUp itemCostBreakUp = new ItemCostBreakUp();
                        itemCostBreakUp.setItemId(itemDetails.get(i).getItem_id());
                        itemCostBreakUp.setItemCost(itemDetails.get(i).getItemBaseCost().longValue());
                        itemCostBreakUp.setItemCount(itemsDetails.get(i).getItemQuantity());
                        itemCostBreakUp.setItemTitle(itemsDetails.get(i).getItemName());
                        itemCostBreakUpList.add(itemCostBreakUp);
                    }
                }
                if (null != wholeSaleLimits.getSize()) {
                    if (itemDetails.get(i).getItemSize().equals(wholeSaleLimits.getSize())) {
                        ItemCostBreakUp itemCostBreakUp = new ItemCostBreakUp();
                        itemCostBreakUp.setItemId(itemDetails.get(i).getItem_id());
                        itemCostBreakUp.setItemCost(itemDetails.get(i).getItemWholeSaleCost().longValue());
                        itemCostBreakUp.setItemCount(itemsDetails.get(i).getItemQuantity());
                        itemCostBreakUp.setItemTitle(itemsDetails.get(i).getItemName());
                        itemCostBreakUpList.add(itemCostBreakUp);
                    } else {
                        ItemCostBreakUp itemCostBreakUp = new ItemCostBreakUp();
                        itemCostBreakUp.setItemId(itemDetails.get(i).getItem_id());
                        itemCostBreakUp.setItemCost(itemDetails.get(i).getItemBaseCost().longValue());
                        itemCostBreakUp.setItemCount(itemsDetails.get(i).getItemQuantity());
                        itemCostBreakUp.setItemTitle(itemsDetails.get(i).getItemName());
                        itemCostBreakUpList.add(itemCostBreakUp);
                    }
                }
                if (null != wholeSaleLimits.getWeight()) {
                    if (itemOrderQuantity.get(itemDetails.get(i).getItem_id()) >= wholeSaleLimits.getWeight()) {
                        ItemCostBreakUp itemCostBreakUp = new ItemCostBreakUp();
                        itemCostBreakUp.setItemId(itemDetails.get(i).getItem_id());
                        itemCostBreakUp.setItemCost(itemDetails.get(i).getItemWholeSaleCost().longValue());
                        itemCostBreakUp.setItemCount(itemsDetails.get(i).getItemQuantity());
                        itemCostBreakUp.setItemTitle(itemsDetails.get(i).getItemName());
                        itemCostBreakUpList.add(itemCostBreakUp);
                    } else {
                        ItemCostBreakUp itemCostBreakUp = new ItemCostBreakUp();
                        itemCostBreakUp.setItemId(itemDetails.get(i).getItem_id());
                        itemCostBreakUp.setItemCost(itemDetails.get(i).getItemBaseCost().longValue());
                        itemCostBreakUp.setItemCount(itemsDetails.get(i).getItemQuantity());
                        itemCostBreakUp.setItemTitle(itemsDetails.get(i).getItemName());
                        itemCostBreakUpList.add(itemCostBreakUp);
                    }
                }
//                if(null != wholeSaleLimits.getTime()){
//                    if(itemOrderQuantity.get(itemDetails.get(i).getItem_id()) >= wholeSaleLimits.getTime()){
//                        itemCostMap.put(itemDetails.get(i).getItem_id(),itemDetails.get(i).getItemWholeSaleCost().longValue());
//                    }
//                }
            }
        }
        orderDetails.setItems(itemsDetails);
        Optional<Consumer> fetchedConsumer = consumerRepo.findById(orderResponseDto.getConsumerId());
        UserInfoDto userInfo = objMapper.readValue(fetchedConsumer.get().getUser().getUserDetails(), UserInfoDto.class);
        orderDetails.setUserInfo(userInfo);
        double totalItemCost = 0.0;
        for (ItemCostBreakUp itemCostBreakUp : itemCostBreakUpList){
            totalItemCost = totalItemCost + itemCostBreakUp.getItemCost();
        }

        InvoiceResDto invoiceResDto = new InvoiceResDto();
        invoiceResDto.setTotalItemCost(totalItemCost);
        invoiceResDto.setItemCostBreakup(itemCostBreakUpList);
        invoiceResDto.setTotalTaxes(getTaxesForItems(orderResponseDto));
        invoiceResDto.setDeliveryCharges(getDeliveryCharges(orderResponseDto));
        invoiceResDto.setDiscount(getDiscountForItems(orderResponseDto));
        //invoiceResDto.setInvoicePath(generateInvoicePath(invoiceResDto));
        invoiceResDto.setTotalPayable((invoiceResDto.getTotalItemCost()+invoiceResDto.getTotalTaxes()+invoiceResDto.getDeliveryCharges())-invoiceResDto.getDiscount());
        invoiceResDto.setItemDetails(itemDetails);
        orderDetails.setInvoiceResDto(invoiceResDto);
        invoiceResDto.setInvoicePath(generateInvoicePath(orderDetails));
        return invoiceResDto;
    }

      @Override
      public double getTaxesForItems(OrderResponseDto orderResponseDto) throws JsonProcessingException {
        return 0.0;
      }
    @Override
    public double getDiscountForItems(OrderResponseDto orderResponseDto) throws JsonProcessingException {
        return 0.0;
    }
    @Override
    public double getDeliveryCharges(OrderResponseDto orderResponseDto) throws JsonProcessingException {
        return 0.0;
    }

    @Override
    public String generateInvoicePath(OrderDetails orderDetails) throws JsonProcessingException {
        //OutputStream pdf = PdfGen.createPDF(orderDetails); // send Invoice Dto

        //upload to s3
        return "https://korike.s3.ap-south-1.amazonaws.com/1673862493278-invoicetest.pdf";

        //return null;


    }
}
