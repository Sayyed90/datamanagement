package com.example.datamanagementsystem.usermodule.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResponsePayload {
    private String token;
    private String status;
}
