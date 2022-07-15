package com.authorizationservice.authorization.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authorizationservice.authorization.exceptions.LoginException;
import com.authorizationservice.authorization.model.AuthenticationRequest;
import com.authorizationservice.authorization.model.AuthenticationRequestDTO;
import com.authorizationservice.authorization.model.AuthenticationResponse;
import com.authorizationservice.authorization.model.VaildatingDTO;
import com.authorizationservice.authorization.service.AppUserDetailsService;
import com.authorizationservice.authorization.util.JwtUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@CrossOrigin
@RequestMapping("/authorization")
public class AuthorizationController {

	@Autowired
	private AppUserDetailsService userDetailsService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	

	private VaildatingDTO vaildatingDTO = new VaildatingDTO();

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> createAuthorizationToken(@RequestBody AuthenticationRequestDTO authenticationRequestDTO)
			throws LoginException {
		
		log.info("BEGIN - [login(customerLoginCredentials)]");
		UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequestDTO.getUsername());
		log.debug("{}", userDetails);
		
		if (userDetails.getPassword().equals(authenticationRequestDTO.getPassword())) {
			log.info("END - [login(customerLoginCredentials)]");
			return new ResponseEntity<AuthenticationResponse>(new AuthenticationResponse(authenticationRequestDTO.getUsername(),
					jwtTokenUtil.generateToken(userDetails), jwtTokenUtil.getCurrentTime(), jwtTokenUtil.getExpirationTime()), HttpStatus.OK);
		} 
		
		log.info("password not mathcing");
		log.info("END - [login(customerLoginCredentials)]");
		throw new LoginException("Invalid Username or Password");
	}
	    

	@GetMapping("/validate")
	public ResponseEntity<VaildatingDTO> validatingAuthorizationToken(@RequestHeader(name = "Authorization") String tokenDup) {
		
		log.info("BEGIN - [validatingAuthorizationToken(JWT-token)]");
		String token = tokenDup.substring(7);
		try {
			UserDetails user = userDetailsService.loadUserByUsername(jwtTokenUtil.extractUsername(token));
			if (Boolean.TRUE.equals(jwtTokenUtil.validateToken(token, user))) {
				log.debug("Token matched is Valid");
				log.info("Token validation successful");
				log.info("END - validate()");
				vaildatingDTO.setValidStatus(true);
				return new ResponseEntity<>(vaildatingDTO, HttpStatus.OK);
			} else {
				throw new LoginException("Invalid Token");
			}
		} catch (Exception e) {
			log.debug("Invalid token - Bad Credentials Exception");
			log.info("END Exception - validatingAuthorizationToken()");
			vaildatingDTO.setValidStatus(false);
			return new ResponseEntity<>(vaildatingDTO, HttpStatus.BAD_REQUEST);
		}
		
	}

	@GetMapping(path = "/health-check")
	public ResponseEntity<String> healthCheck() {
		
		log.info("Authorization Microservice is Up and Running....");
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	
}
