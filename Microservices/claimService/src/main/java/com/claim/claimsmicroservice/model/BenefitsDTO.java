package com.claim.claimsmicroservice.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BenefitsDTO {
	
	 private List<Benefits> benefits;

	public List<Benefits> getBenefits() {
		return benefits;
	}

	public void setBenefits(List<Benefits> benefits) {
		this.benefits = benefits;
	}

	public BenefitsDTO(List<Benefits> benefits) {
		super();
		this.benefits = benefits;
	}
	
	
	
	
	

}