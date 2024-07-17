package com.korike.logistics.controller.user;

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
import com.korike.logistics.entity.Commission;
import com.korike.logistics.entity.Partner;
import com.korike.logistics.exception.ResourceNotFoundException;
import com.korike.logistics.model.CommissionDto;
import com.korike.logistics.repository.CommissionRepository;
import com.korike.logistics.repository.PartnerRepository;
import com.korike.logistics.service.CommissionService;

@RestController
@RequestMapping("/api/admin")
public class CommissionController {
	 private static Logger logger = Logger.getLogger(CommissionController.class);
	 
	 @Autowired
	 CommissionService commService;
	 @Autowired
	 CommissionRepository commRepository;
	 @Autowired
	 PartnerRepository partnerRepository;
	 
	 @RequestMapping(value={"/commission/{comm_id}","/commission"}, method  = RequestMethod.POST)
		public ResponseEntity<?> createUpdateCommission(@PathVariable(required=false) Long comm_id, @RequestParam( required = false, name="action") String action,@RequestBody CommissionDto commReq) throws JsonProcessingException{
			logger.info("Started createUpdateCommission() method ");
			Map<String, Object> model = new HashMap<>();
			
			if("add".equals(action)) {
				if(commReq.getCommissionPercent() == null) {
					throw new InvalidParameterException("commission percent is missing");
				}
				
				if(commReq.getPartnerId() == null || commReq.getPartnerId().isEmpty()) {
					throw new InvalidParameterException("partner id is missing");
				}
				
				Optional<Partner> fetchedPartner = partnerRepository.findById(commReq.getPartnerId());
				 if(!fetchedPartner.isPresent()) {
			        	logger.error("Exception occured in CommissionController() method.");
						throw new ResourceNotFoundException("Invalid partnerId "+commReq.getPartnerId());
			     }
				List<Commission> commList = commRepository.findAll();
				 int commSize = commList.size();
				 int commHit = 0;
				for(Commission commission: commList){
					if(commission.getCommissionPercent().equals(commReq.getCommissionPercent()) && commission.getPartner().getPartnerId().equalsIgnoreCase(commReq.getPartnerId())){
						logger.info("Commission for partner already present.");
						model.put("comm_id", commission.getCommissionId());
						model.put("job_id","default");
						model.put("statusDetail", "Commission for partner already present.");
						commHit++;
					}
					commSize--;
					if(commSize==0 && commHit==0){
						CommissionDto createCommission = commService.createCommission(commReq,fetchedPartner.get());
						model.put("comm_id", createCommission.getCommId());
						model.put("job_id","default");
						model.put("statusDetail", "Commission created successfully");
					}
				}

			}else if("edit".equals(action)) {
				if(commReq.getCommissionPercent() == null) {
					throw new InvalidParameterException("commission percent is missing");
				}
				
				if(commReq.getPartnerId() == null || commReq.getPartnerId().isEmpty()) {
					throw new InvalidParameterException("partner id is missing");
				}
				
				Optional<Commission> fetchedComm = commRepository.findById(comm_id);
				if(!fetchedComm.isPresent()) {
		        	logger.error("Exception occured in CommissionController() method.");
					throw new ResourceNotFoundException("Invalid commissionId "+comm_id);
		         }
				
				Optional<Partner> fetchedPartner = partnerRepository.findById(commReq.getPartnerId());
				 if(!fetchedPartner.isPresent()) {
			        	logger.error("Exception occured in CommissionController() method.");
						throw new ResourceNotFoundException("Invalid partnerId "+commReq.getPartnerId());
			     }
				 
				 CommissionDto updatedCommission = commService.updateCommission(commReq,fetchedComm.get(),fetchedPartner.get());
				 
				 model.put("comm_id", updatedCommission.getCommId());
				 model.put("job_id","default");
				 model.put("statusDetail", "Commission updated successfully");
			}else if("query".equals(action)) {
				List<CommissionDto> commLst = commService.commissionList(commReq);
				
				 model.put("comm_list", commLst);
				 model.put("job_id","default");
				 model.put("statusDetail", "Found "+commLst.size()+" commissions");
			}
			
			return ok(model);
	 }
}
