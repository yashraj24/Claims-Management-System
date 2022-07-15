package com.policy.microservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.policy.microservice.model.MemberPolicy;

@Repository
public interface MemberPolicyRepo extends JpaRepository<MemberPolicy,String>{
	

}
