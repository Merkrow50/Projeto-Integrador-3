package com.example.application.data;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class UserDetails {

  private Long id;

  private String name;

  private String email;

  private String password;

  private boolean allowsTerms;

}
