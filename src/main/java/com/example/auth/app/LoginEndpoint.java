package com.example.auth.app;

import com.example.auth.core.ResourceOwnerAuthentication;
import com.example.auth.core.session.Session;
import com.google.common.base.Optional;
import com.google.inject.Inject;
import com.google.sitebricks.Show;
import com.google.sitebricks.http.Post;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Ivan Stefanov <ivan.stefanov@clouway.com>
 */
@Show("login.html")
public class LoginEndpoint {
  private String username;
  private String password;
  private String redirectUrl;

  private ResourceOwnerAuthentication authentication;

  @Inject
  public LoginEndpoint(ResourceOwnerAuthentication authentication) {
    this.authentication = authentication;
  }

  @Post
  public String login(HttpServletRequest request, HttpServletResponse response) {
    Optional<Session> session = authentication.auth(username, password, request.getRemoteAddr());

    if (session.isPresent()) {
      // "SID" -this value should be configurable or in the
      // login should be responsible for that hardcoded string
      response.addCookie(new Cookie("SID", session.get().value));
    }

    return redirectUrl;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setRedirectUrl(String redirectUrl) {
    this.redirectUrl = redirectUrl;
  }
}