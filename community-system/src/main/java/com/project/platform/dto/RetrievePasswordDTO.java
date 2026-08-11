package com.project.platform.dto;

import lombok.Data;

@Data
public class RetrievePasswordDTO {
    private String type;
    private String username;
    private String code;
    private String password;
}
