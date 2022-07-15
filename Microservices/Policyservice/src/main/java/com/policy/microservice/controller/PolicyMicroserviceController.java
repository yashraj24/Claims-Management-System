package com.policy.microservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.policy.microservice.exception.InvalidTokenException;
import com.policy.microservice.service.BenefitsService;
import com.policy.microservice.service.ClaimAmountService;
import com.policy.microservice.service.ProviderService;
import com.policy.microservice.model.BenefitsDTO;
import com.policy.microservice.model.ClaimAmountDTO;
import com.policy.microservice.model.ProviderDTO;
import com.policy.microservice.proxy.AuthClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class PolicyMicroserviceController {
	
	@Autowired
	private ProviderService providerService;
	
	@Autowired
	private BenefitsService benefitsService;
	
	@Autowired
	private ClaimAmountService claimAmountService;
	
	@Autowired
	private AuthClient authClient;
	 
	private String msg = "Token is either expired or invalid...";
	
	@GetMapping("/getChainOfProviders/{policyId}")
	public ResponseEntity<ProviderDTO> getChainOfProviders(@PathVariable String policyId,@RequestHeader(name = "Authorization", required = true)String token) throws InvalidTokenException{
		log.info("Inside get chain of providers...");
		
		if (!authClient.getsValidity(token).isValidStatus()) {
			log.info("PolicyMicroserviceController.getChainOfProviders : token is not valid");
			throw new InvalidTokenException(msg);
		}
		log.info("Exiting get chain of providers...");
		
		return new ResponseEntity<>(providerService.getProviders(policyId), HttpStatus.OK);		
	}
	
		
	@GetMapping("/getEligibleBenefits/{policyId}/{memberId}")
	public ResponseEntity<BenefitsDTO> getEligibleBenefits(@PathVariable String policyId, @PathVariable String memberId, @RequestHeader(name = "Authorization", required = true)String token) throws InvalidTokenException{
		log.info("Inside get eligible benefits");
		
		if (!authClient.getsValidity(token).isValidStatus()) {
			log.info("getEligibleBenifits: token is not valid");
			throw new InvalidTokenException(msg);
		}
		log.info("Exiting get eligible benefits");
    	return new ResponseEntity<>(benefitsService.getBenefits(policyId,memberId), HttpStatus.OK);
	}
	
	
	
	@GetMapping("/getEligibleClaimAmount/{policyId}/{memberId}")
	public ResponseEntity<ClaimAmountDTO> getEligibleAmount(@PathVariable String policyId, @PathVariable String memberId, @RequestHeader(name = "Authorization", required = true)String token) throws InvalidTokenException{
		log.info("Inside get eligible benefits");
		
		if (!authClient.getsValidity(token).isValidStatus()) {
			log.info("getEligibleAmoung: token is not valid");
			throw new InvalidTokenException(msg);
		}	   
		log.info("Exiting get elibile amount");
		
		return new ResponseEntity<>(claimAmountService.getClaimAmount(policyId,memberId), HttpStatus.OK);
	}
}
