package com.authorizationservice.authorization.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor   
@AllArgsConstructor
@ToString
@Entity
@Table(name="persons")
public class AuthenticationRequest {

    @Id
    @Column(name="username")
    private String username;
    
    @Column(name="password")
    private String password;

}
