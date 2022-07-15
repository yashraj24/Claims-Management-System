package com.policy.microservice.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class BenefitsDTO {
	private Set<Benefits> benefits;

	public Set<Benefits> getBenefits() {
		return benefits;
	}

	public void setBenefits(Set<Benefits> benefits) {
		this.benefits = benefits;
	}

	public BenefitsDTO(Set<Benefits> benefits) {
		super();
		this.benefits = benefits;
	}

}
