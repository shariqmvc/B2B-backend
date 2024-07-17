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
import com.korike.logistics.entity.ServiceDefinition;
import com.korike.logistics.exception.ResourceNotFoundException;
import com.korike.logistics.model.services.ServiceDetails;
import com.korike.logistics.repository.ServiceDefinitionRepository;
import com.korike.logistics.service.ServiceDefintitionService;

@RestController
@RequestMapping("/api/admin")
public class ServiceDefintionController {
	
	@Autowired
	ServiceDefintitionService serviceDefService;
	@Autowired
	ServiceDefinitionRepository serviceDefRepo;
	
	private static Logger logger = Logger.getLogger(ServiceDefintionController.class);
	
	@RequestMapping(value={"/service/{s_id}","/service"}, method  = RequestMethod.POST)
	public ResponseEntity<?> createUpdateService(@PathVariable(required=false) String s_id, @RequestParam( required = false, name="action") String action,@RequestBody ServiceDetails serviceReq) throws JsonProcessingException{
		logger.info("Started createUpdateService() method ");
		Map<String, Object> model = new HashMap<>();
		 
		 
		if("add".equals(action)) {
			if(serviceReq.getName() == null || serviceReq.getName().isEmpty()) {
				throw new InvalidParameterException("Service name is missing");
			}
	        if(serviceReq.getDescription() == null || serviceReq.getDescription().isEmpty()) {
	        	throw new InvalidParameterException("Service description is missing");
			}
	        
	        serviceReq = serviceDefService.createService(serviceReq);
	        
	       
			model.put("s_id", serviceReq.getServiceId());
			model.put("job_id","default");
			model.put("statusDetail", "Service created successfully");
		}else if("edit".equals(action)) {
			if(serviceReq.getName() == null || serviceReq.getName().isEmpty()) {
				throw new InvalidParameterException("Service name is missing");
			}
	        if(serviceReq.getDescription() == null || serviceReq.getDescription().isEmpty()) {
	        	throw new InvalidParameterException("Service description is missing");
			}
	        
	        ServiceDefinition fetchedServiceDefinition = serviceDefRepo.getByServiceId(s_id);
	        if(fetchedServiceDefinition == null) {
	        	logger.error("Exception occured in ServiceDefintionController() method.");
				throw new ResourceNotFoundException("Invalid Service Id "+s_id);
	        }
	        
	        //Call service method
	        serviceDefService.updateService(serviceReq, fetchedServiceDefinition);
	        
	        model.put("s_id", serviceReq.getServiceId());
			model.put("job_id","default");
			model.put("statusDetail", "Service updated successfully");
		}else if("query".equals(action)){
			List<ServiceDetails> serviceListByFilter = serviceDefService.getServiceListByFilter(serviceReq.getFilter().getsId() == null?"":serviceReq.getFilter().getsId(), serviceReq.getFilter().getsName() == null?"":serviceReq.getFilter().getsName(), serviceReq.getFilter().getsActive() == null?null:serviceReq.getFilter().getsActive());
			  model.put("serviceList", serviceListByFilter);
			  model.put("statusDetail", "Found "+serviceListByFilter.size()+" services");
		}
		return ok(model);
        

	}
}
