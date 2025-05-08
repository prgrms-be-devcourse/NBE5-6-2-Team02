package com.grepp.smartwatcha.infra.handler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
      Authentication authentication) throws IOException {
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    for(GrantedAuthority authority : authorities) {
      if(authority.getAuthority().equals("ROLE_ADMIN")) {
        response.sendRedirect("/admin");
        return;
      }
    }

    response.sendRedirect("/");
  }
}
