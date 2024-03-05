package com.tutorial.crud.security.dto;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Getter;

@Getter
@Setter
@NoArgsConstructor
public class JwtDto {
    
    private String token;
    
    public JwtDto(String token){
        this.token = token;
    }
}
