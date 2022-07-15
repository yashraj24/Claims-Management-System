package com.policy.microservice.model;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ProviderDTO {
	
	@JsonProperty
	private Set<Hospital> providers;
	public ProviderDTO(Set<Hospital> providers) {
		this.providers=providers;
	}
	public Set<Hospital> getProviders() {
		return providers;
	}
	public void setProviders(Set<Hospital> providers) {
		this.providers = providers;
	}
}
