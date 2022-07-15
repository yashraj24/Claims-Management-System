package com.mfpe.memberService.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mfpe.memberService.exceptions.InvalidTokenException;
import com.mfpe.memberService.model.Bills;
import com.mfpe.memberService.model.ClaimDetails;
import com.mfpe.memberService.model.ClaimStatusDTO;
import com.mfpe.memberService.proxy.AuthClient;
import com.mfpe.memberService.service.ClaimStatusAndDetails;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;



@RestController
@Slf4j
@RequestMapping("/memberModule")
@CrossOrigin
public class MemberController { 
    
    @Autowired
    private ClaimStatusAndDetails ClaimStatusAndDetails;
    
	 @Autowired
	 private AuthClient authClient;
     
     private String msg = "Token is either expired or invalid...";
    
    @GetMapping("/viewBills/{memberId}")
    public ResponseEntity<Bills> getBills( @PathVariable("memberId") String memberId,@RequestHeader(name = "Authorization", required = true)String token) throws InvalidTokenException{
        log.info("Inside the getBills EndPoint : Begin");
 
    	if (!authClient.getsValidity(token).isValidStatus()) {
			throw new InvalidTokenException(msg);
		}
	
    	log.info("Inside the getBills EndPoint : Ended");
        return new ResponseEntity<>( ClaimStatusAndDetails.fetchBills(memberId), HttpStatus.OK);
    }
    
    
    @GetMapping("/getClaimStatus/{claimId}")
    public ResponseEntity<ClaimStatusDTO> returnClaimStatus(@PathVariable("claimId") String claimId, @RequestHeader(name = "Authorization", required = true)String token) throws InvalidTokenException
    {
        log.info("Inside the getClaimStatus EndPoint : Begin");
        
        if (!authClient.getsValidity(token).isValidStatus())  {
            throw new InvalidTokenException(msg);
        }
        
        log.info("Inside the getClaimStatus EndPoint : Ended");
        return new ResponseEntity<>( ClaimStatusAndDetails.fetchClaimStatus(claimId, token), HttpStatus.OK);          
    }
    
    @PostMapping("/submitClaim")
    public ResponseEntity<ClaimStatusDTO> returnClaimStatusOnUpdate(@RequestBody ClaimDetails claimDetails,  @RequestHeader(name = "Authorization", required = true)String token) throws InvalidTokenException
    {
        log.info("Inside the submitClaim EndPoint : Begin");
        
        if (!authClient.getsValidity(token).isValidStatus())  {
            throw new InvalidTokenException(msg);
        }
        
        log.info("Inside the submitClaim EndPoint : Ended");
        return new ResponseEntity<>( ClaimStatusAndDetails.fetchClaimDetails(claimDetails,token), HttpStatus.OK);

    }
    
     
}
