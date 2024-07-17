package com.korike.logistics.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.korike.logistics.entity.ServiceDefinition;
import com.korike.logistics.model.services.Details;
import com.korike.logistics.model.services.ServiceDetails;

@Service
public interface ServiceDefintitionService {

	public ServiceDefinition getServiceBySid(String sid);
	public List<Details> getServiceList();
	public ServiceDetails createService(ServiceDetails serviceReq);
	public Boolean updateService(ServiceDetails serviceReq, ServiceDefinition fetchedService);
	public  List<ServiceDetails> getServiceListByFilter(String sId, String sName, Boolean isActive) throws JsonMappingException, JsonProcessingException;
	List<ServiceDefinition> getRegisteredServicesByUser(String userId);
}
