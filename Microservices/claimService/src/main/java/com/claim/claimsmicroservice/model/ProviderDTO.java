package com.claim.claimsmicroservice.model;



import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProviderDTO {
	
	private List<Hospital> providers;
	
	public ProviderDTO(List<Hospital> providers) {
		this.providers=providers;
	}
	
	public List<Hospital> getProviders() {
		return providers;
	}
	
	public void setProviders(List<Hospital> providers) {
		this.providers = providers;
	}
		
		
		

}