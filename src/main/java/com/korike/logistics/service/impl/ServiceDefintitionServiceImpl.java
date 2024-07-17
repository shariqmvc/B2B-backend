package com.korike.logistics.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.google.maps.GeoApiContext;
import com.korike.logistics.repository.ServicePartnerRepository;
import com.korike.logistics.util.CommonUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.korike.logistics.entity.ServiceDefinition;
import com.korike.logistics.exception.KorikeException;
import com.korike.logistics.exception.ResourceNotFoundException;
import com.korike.logistics.model.services.Details;
import com.korike.logistics.model.services.SNearest;
import com.korike.logistics.model.services.ServiceDetails;
import com.korike.logistics.repository.ServiceDefinitionRepository;
import com.korike.logistics.service.ServiceDefintitionService;

import static com.korike.logistics.common.Constants.GOOGLE_MAPS_API_KEY;

@Service
public class ServiceDefintitionServiceImpl implements ServiceDefintitionService{

	private static Logger logger = Logger.getLogger(ServiceDefintitionServiceImpl.class);
	
	@Autowired
	ServiceDefinitionRepository serviceDefRepo;
	
	
	@Override
	public ServiceDefinition getServiceBySid(String sid) {
		ObjectMapper objectMapper = new ObjectMapper();
		
		ServiceDefinition fetchedServiceDef = serviceDefRepo.getByServiceId(sid);
		if(fetchedServiceDef == null) {
			logger.error("Exception occured in getServiceBySid() method. Invalid sid "+sid);
			throw new ResourceNotFoundException("Invalid sid "+sid);
		}
		return fetchedServiceDef;
	}


	@Override
	public List<Details> getServiceList() {
		List<ServiceDefinition> serviceDefLst = serviceDefRepo.findAll();
		return	serviceDefLst.stream().map(sdf -> new Details(
				sdf.getServiceId(),
				sdf.getServiceName(),
				sdf.getServiceIconPath(),
				sdf.getMultipleAllowed(),
				sdf.getIsActive(),
				null,
				null
				)).collect(Collectors.toList());
		 
	}


	@Override
	public ServiceDetails createService(ServiceDetails serviceReq) {
		logger.info("Started createService() method ");
		
		ServiceDefinition newService = new ServiceDefinition();
		newService.setServiceId(UUID.randomUUID().toString());
		newService.setServiceName(serviceReq.getName());
		newService.setServiceDescription(serviceReq.getDescription());
		newService.setServiceIconPath(serviceReq.getDetails().getsIcon());
		newService.setMultipleAllowed(serviceReq.getMultipleAllowed());
		newService.setCreatedBy(CommonUtils.getCurrentUserName());
		newService.setLastUpdatedBy(CommonUtils.getCurrentUserName());
		newService.setCreatedAt(new Timestamp(System.currentTimeMillis()));
		newService.setIsActive(serviceReq.getActive());
		
		try {
			newService = serviceDefRepo.save(newService);
			serviceReq.setServiceId(newService.getServiceId());
		}catch(Exception exc) {
			logger.error("Exception occured in createService() method.");
			throw new KorikeException("Internal Server Error");
		}
		
		
		return serviceReq;
	}


	@Override
	public Boolean updateService(ServiceDetails serviceReq, ServiceDefinition fetchedService) {
		
		Boolean status = false;
	
		fetchedService.setServiceName(serviceReq.getName());
		fetchedService.setServiceDescription(serviceReq.getDescription());
		fetchedService.setServiceIconPath(serviceReq.getDetails().getsIcon());
		fetchedService.setLastUpdatedBy(CommonUtils.getCurrentUserName());
		fetchedService.setMultipleAllowed(serviceReq.getMultipleAllowed());
		fetchedService.setLastUpdatedAt(new Timestamp(System.currentTimeMillis()));
		//newService.setIsActive(true);
		fetchedService.setIsActive(serviceReq.getActive());
		try {
			fetchedService = serviceDefRepo.save(fetchedService);
			serviceReq.setServiceId(fetchedService.getServiceId());
			status = true;
		}catch(Exception exc) {
			logger.error("Exception occured in updateService() method.");
			throw new KorikeException("Internal Server Error");
		}
		
		
		return status;
	}


	@Override
	public List<ServiceDetails> getServiceListByFilter(String sId, String sName, Boolean isActive) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		List<ServiceDefinition> serviceDefList;
	/*	try { */
			serviceDefList = serviceDefRepo.getServiceByFilter(sId, sName, isActive);
			ServiceDetails serviceDet;
			List<ServiceDetails> serviceDets = new ArrayList(); 
			for(ServiceDefinition serDef : serviceDefList) {
				serviceDet = new ServiceDetails();
				serviceDet.setServiceId(serDef.getServiceId());
				serviceDet.setName(serDef.getServiceName());
				serviceDet.setMultipleAllowed(serDef.getMultipleAllowed());
				serviceDet.setDescription(serDef.getServiceDescription());
				serviceDet.setDetails(serDef.getServiceDetails() == null || serDef.getServiceDetails().isEmpty()?null:objectMapper.readValue(serDef.getServiceDetails(), Details.class));
				serviceDets.add(serviceDet);
			}
		/*	return serviceDefList.stream().map(sf -> 
			/*	try {
					return 
							{
								try {
									return new ServiceDetails(
									sf.getServiceId(),
									sf.getServiceName(),
									sf.getServiceDescription(),
									sf.getServiceDetails() == null?null:objectMapper.readValue(sf.getServiceDetails(), Details.class)
									);
								} catch (JsonProcessingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								return null;
							}
			/*	} catch (JsonMappingException e) {
					logger.error("Exception occured in getServiceListByFilter() method.");
					throw new KorikeException("Internal Server Error");
				} catch (JsonProcessingException e) {
					logger.error("Exception occured in getServiceListByFilter() method.");
					throw new KorikeException("Internal Server Error");
				} 
			).collect(Collectors.toList()); */
			
			
	/*	}catch(Exception exc) {
			logger.error("Exception occured in getServiceListByFilter() method.");
			throw new KorikeException("Internal Server Error");
		} */
		return serviceDets;
	}

	public List<ServiceDefinition> getRegisteredServicesByUser(String userId) {

		List<ServiceDefinition> serviceList = serviceDefRepo.getRegisteredServicesByUser(userId);
		if(serviceList!=null && !serviceList.isEmpty()) {
			return serviceList;
		}
		return null;
	}


}
