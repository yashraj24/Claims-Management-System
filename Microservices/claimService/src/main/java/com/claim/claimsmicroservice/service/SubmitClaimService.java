package com.claim.claimsmicroservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.claim.claimsmicroservice.exceptions.PolicyException;
import com.claim.claimsmicroservice.model.Benefits;
import com.claim.claimsmicroservice.model.BenefitsDTO;
import com.claim.claimsmicroservice.model.Claim;
import com.claim.claimsmicroservice.model.ClaimAmountDTO;
import com.claim.claimsmicroservice.model.ClaimDTO;
import com.claim.claimsmicroservice.model.ClaimStatusDTO;
import com.claim.claimsmicroservice.model.Hospital;
import com.claim.claimsmicroservice.model.ProviderDTO;
import com.claim.claimsmicroservice.proxy.PolicyClient;
import com.claim.claimsmicroservice.repository.ClaimRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SubmitClaimService {
	@Autowired
	private ClaimRepo claimRepo;
	
	@Autowired
	private PolicyClient policyClient;

	private boolean checkProvider(ClaimDTO claimDTO, String token) {
		ProviderDTO providerDTO = policyClient.getChainOfProviders(claimDTO.getPolicyId(), token).getBody();
		log.info("came here");
		
		if (providerDTO !=null && providerDTO.getProviders() != null) {
			for (Hospital hospitalDetails : providerDTO.getProviders()) {
				if (claimDTO.getHospitalId().equalsIgnoreCase(hospitalDetails.getHospitalId())) {
					return true;
				}
			}
		}
		
		return false;
	}

	private boolean checkBenefit(ClaimDTO claimDTO, String token) {
		BenefitsDTO benefitsDTO = policyClient.getEligibleBenefits(claimDTO.getPolicyId(), claimDTO.getMemberId(), token).getBody();
		if (benefitsDTO != null &&  benefitsDTO.getBenefits() !=null) {
			for (Benefits benefits : benefitsDTO.getBenefits()) {
				if (claimDTO.getBenefitId().equalsIgnoreCase(benefits.getBenefitId())) {
					return true;
				}
			}
		}
		return false;
	}

	private boolean checkAmount(ClaimDTO claimDTO, String token) {
		ClaimAmountDTO claimAmountDTO = policyClient.getEligibleAmount(claimDTO.getPolicyId(), claimDTO.getMemberId(), token).getBody();
		if (claimAmountDTO != null) {
			if(claimDTO.getClaimAmount() <= claimAmountDTO.getEligibleAmount()) {
				return true;
			}
		}
		return false;
	}

	public ResponseEntity<ClaimStatusDTO> submitClaim(ClaimDTO claimDTO, String token) throws NullPointerException {
		boolean hospitalFlag = false;
		boolean benefitFlag = false;
		boolean amountFlag = false;
		try {
			log.info("inside the submit claim service method : BEGIN");
			log.info("beginning checking the providers");
			hospitalFlag = checkProvider(claimDTO, token);
			log.info("ending checking of providers");
	
			log.info("beginning checking the Benefit");
			benefitFlag = checkBenefit(claimDTO, token);
			log.info("ending checking of Benefit");
	
			log.info("begining checking the Amount");
			amountFlag = checkAmount(claimDTO, token);
			log.info("ending checking the amount");
		}catch(PolicyException pe) {
			throw new PolicyException("Policy Serivce is Not working Properly");
		}
		log.info("creating claim and setting the status : BEGIN");
		Claim claim = new Claim();
		claim.setClaimId(claimDTO.getClaimId());
		claim.setBenefitId(claimDTO.getBenefitId());
		claim.setClaimAmount(claim.getClaimAmount());
		claim.setHospitalId(claimDTO.getHospitalId());
		claim.setMemberId(claimDTO.getMemberId());
		claim.setPolicyId(claimDTO.getPolicyId());
		claim.setRemarks(claim.getRemarks());
		if (hospitalFlag && benefitFlag && amountFlag ) {
			claim.setStatus("Pending Action");
			claim.setDescription("All the fields are successfully verified! Please wait for furthur action");
		} else {
			claim.setStatus("Claim Rejected");
			if (!hospitalFlag) {
				claim.setDescription("Providers Details are not valid");
			} else if (!benefitFlag) {
				claim.setDescription("Benefits Details are not valid");
			} 
			else
			{
				claim.setDescription("Claim amount is not valid");
			}
		}
		log.info("setting the status : ENDED");
		if (hospitalFlag && benefitFlag && amountFlag) {
			claimRepo.save(claim);
		}

		ClaimStatusDTO claimStatusDTO = new ClaimStatusDTO();
		claimStatusDTO.setClaimStatus(claim.getStatus());
		claimStatusDTO.setClaimDescription(claim.getDescription());
		claimStatusDTO.setClaimId(claim.getClaimId());
		return new ResponseEntity<>(claimStatusDTO, HttpStatus.OK);
	}

}
