package com.example.TaskManager.Model.DTO;

import lombok.Data;

/**
 * Represents a DTO that is meant to be sent from
 * login post requests to provide authorization to users
 */
@Data
public class AuthResponseDTO {
    private String accessToken;
    private String tokenType = "Bearer ";

    /**
     * object contains the generated token
     * @param accessToken the generated token is passed in
     */
    public AuthResponseDTO(String accessToken){
        this.accessToken = accessToken;
    }
}
