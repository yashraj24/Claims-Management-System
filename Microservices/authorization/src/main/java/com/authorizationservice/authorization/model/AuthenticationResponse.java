package com.authorizationservice.authorization.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
//@EqualsAndHashCode
public class AuthenticationResponse {

    private String username;
    private String jwtAuthToken;
    private long serverCurrentTime;
    private long tokenExpirationTime;
}
