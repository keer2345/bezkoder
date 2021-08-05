package com.keer.springjwt.payload.response;

import java.util.List;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class JwtResponse {

  private String type = "Bearer";
  @NonNull
  private String token;
  @NonNull
  private Long id;
  @NonNull
  private String username;
  @NonNull
  private String email;
  @NonNull
  private List<String> roles;

}
