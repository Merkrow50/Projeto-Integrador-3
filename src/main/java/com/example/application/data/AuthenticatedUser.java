package com.example.application.data;

import lombok.Data;

@Data
public class AuthenticatedUser {

  private String userId;

  private String token;

  private String email;

}
